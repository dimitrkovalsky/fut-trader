package com.liberty.common;

/**
 * @author Dmytro_Kovalskyi.
 * @since 20.01.2017.
 */
public class StringConstants {
    public static final String TOKEN_COOKIE_NAME = "token";
    public static String ROOT_WEB_PATH = "/";
    public static String USER_DETAILS_COOKIE_NAME = "details";

    public static final String ROLE_USER = "USER";
    public static final String ROLE_SWAGGER_USER = "SWAGGER_USER";

    public static final String SECURED_ROLES_EXPRESSION = "hasRole(\"" + ROLE_USER + "\")";
    public static final String ANONYMOUS_USER = "Anonymous User";
}
