package com.liberty.services;

import com.liberty.model.UserSessionDetails;
import com.liberty.security.AuthData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dmytro_Kovalskyi.
 * @since 20.01.2017.
 */
public interface AuthService {
    void authenticateSession(UserSessionDetails userSessionDetails);

    void addCookies(HttpServletResponse response, AuthData authData);

    void removeCookies(HttpServletResponse response);

    void logout(HttpServletRequest request, HttpServletResponse response);
}
