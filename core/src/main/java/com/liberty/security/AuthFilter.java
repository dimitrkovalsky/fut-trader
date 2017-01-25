package com.liberty.security;

import com.google.common.collect.Lists;
import com.liberty.GlobalConfig;
import com.liberty.common.StringConstants;
import com.liberty.model.UserSessionDetails;
import com.liberty.repositories.UserSessionDetailsRepository;
import com.liberty.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Slf4j
@Component
public class AuthFilter extends DelegatingFilterProxy {

    @Autowired
    private UserSessionDetailsRepository sessionDetailsRepository;

    @Autowired
    private AuthService authService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        if (!GlobalConfig.securityEnabled) {
            chain.doFilter(req, res);
            return;
        }
        HttpServletRequest request = (HttpServletRequest) req;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = false;
        if (authentication != null) {
            log.debug("Authorised user: " + authentication.getName());
            isAuthenticated = checkRoles(authentication.getAuthorities());
        }
        if (!isAuthenticated) {
            String springSession = ((HttpServletRequest) req).getSession().getId();
            log.debug("Not authenticated. SessionId: " + springSession);

            String token = null;
            if (request.getCookies() != null) {
                List<Cookie> cookieList = Lists.newArrayList(request.getCookies());
                Optional<Cookie> cookie = cookieList.stream()
                        .filter(c -> c.getName().equals(StringConstants.TOKEN_COOKIE_NAME))
                        .findFirst();
                if (cookie.isPresent()) {
                    token = cookie.get().getValue();
                }
            }
            if (token != null) {
                checkToken((HttpServletResponse) res, springSession, token);
            }
        }
        chain.doFilter(req, res);
    }

    private void checkToken(HttpServletResponse res, String springSession, String token) {
        //Verify it in DB
        Optional<UserSessionDetails> details = sessionDetailsRepository.findByToken(token);
        //if exists update authentication
        if (details.isPresent() && details.get().isValid()) {
            UserSessionDetails sessionDetails = details.get();
            log.debug("Session is still valid. Renew Spring session id");
            sessionDetails.setSpringSession(springSession);

            authService.authenticateSession(sessionDetails);
        } else {
            authService.removeCookies(res);
            //delete from db
            if (details.isPresent()) {
                log.debug("Session is expired. Delete it from DB");
                sessionDetailsRepository.deleteByToken(token);
            }
        }
    }

    private boolean checkRoles(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(StringConstants.ROLE_USER)) {
                return true;
            }
        }
        return false;
    }

}
