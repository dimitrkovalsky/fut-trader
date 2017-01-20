package com.liberty.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.liberty.common.JsonHelper;
import com.liberty.common.StringConstants;
import com.liberty.model.UserSessionDetails;
import com.liberty.repositories.UserSessionDetailsRepository;
import com.liberty.security.AuthData;
import com.liberty.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserSessionDetailsRepository userSessionRepository;

    public String getCurrentUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null &&
                authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return "Anonymous";
    }

    @Override
    public void authenticateSession(UserSessionDetails userSessionDetails) {
        SecurityContext context = SecurityContextHolder.getContext();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userSessionDetails.getName(),
                userSessionDetails.getPassword(), Lists.newArrayList(new SimpleGrantedAuthority("USER")));
        context.setAuthentication(authRequest);
        userSessionRepository.save(userSessionDetails);
    }

    @Override
    public void addCookies(HttpServletResponse response, AuthData authData) {
        Cookie cookie = generateCookie(StringConstants.TOKEN_COOKIE_NAME, authData.getToken(),
                StringConstants.ROOT_WEB_PATH);
        response.addCookie(cookie);
        try {
            String userDetailsString =
                    JsonHelper.getObjectMapper().writeValueAsString(authData.getUserAttributes());
            Cookie userDetailsCookie =
                    generateCookie(StringConstants.USER_DETAILS_COOKIE_NAME, userDetailsString);
            response.addCookie(userDetailsCookie);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCookies(HttpServletResponse response) {
        Cookie cookie = generateCookie(StringConstants.TOKEN_COOKIE_NAME, "",
                StringConstants.ROOT_WEB_PATH, 0);
        response.addCookie(cookie);

        Cookie userDetailsCookie = generateCookie(StringConstants.USER_DETAILS_COOKIE_NAME, "", 0);
        response.addCookie(userDetailsCookie);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.logout();
        } catch (ServletException e) {
            log.error("Error during spring session logout", e);
        }
    }

    private int getTimeToLive() {
        return 600_000;
    }

    private Cookie generateCookie(String cookieName, String value) {
        return generateCookie(cookieName, value, "/",
                getTimeToLive());
    }

    private Cookie generateCookie(String cookieName, String value, int age) {
        return generateCookie(cookieName, value, "/", age);
    }

    private Cookie generateCookie(String cookieName, String value, String path) {
        return generateCookie(cookieName, value, path, getTimeToLive());
    }

    private Cookie generateCookie(String cookieName, String value, String path, int age) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setPath(path);
        cookie.setMaxAge(age);
        return cookie;
    }
}
