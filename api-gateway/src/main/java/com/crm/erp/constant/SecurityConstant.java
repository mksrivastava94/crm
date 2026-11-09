package com.crm.erp.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecurityConstant {

    public static final String EMAIL_ID = "EmailId";

    private SecurityConstant(){}

    public final static long EXPIRATION_TIME = 86_400_000;// 1days expressed in milliseconds
    public final static String TOKEN_PREFIX ="Bearer ";
    public final static String JWT_TOKEN_HEADER ="JWT-TOKEN";
    public final static String TOKEN_CANNOT_BE_VERIFIED ="Token can't be verified";
    public final static String CRM ="CRM";
    public final static String CRM_ADMINISTRATION =" USER Management Portal";
    public final static String AUTHORITIES ="Authorities";
    public final static String FORBIDDEN_MESSAGE ="You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE ="You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD="OPTIONS";
    //public static final String[] PUBLIC_URLS ={"/user/login","/user/register","/user/resetpassword/**","/user/image/**"};
    public static final String[] PUBLIC_URLS ={"**"};
    public static final int PASSWORD_SALT = 12;
    public static final List<String> ORIGINS = new ArrayList<>(Arrays.asList("*","http://localhost:4200","http://localhost:3000"));
    public static final List<String> HEADERS = new ArrayList<>(Arrays.asList("Origin", "X-Requested-With", "Content-Type", "Accept", "Key", "Authorization"));
    public static final List<String> EXPOSED_HEADERS = new ArrayList<>(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Jwt-Token", "Content-Type", "Accept", "Key", "Authorization"));
    public static final List<String> METHODS = new ArrayList<>(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    public static final Long MAX_AGE = (long) 3600;

    public static final String ACCOUNT_LOCKED = "Your account has been locked. Please contact administration";
    public static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a proper request";
    public static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";
    public static final String INCORRECT_CREDENTIALS = "Username / password incorrect. Please try again";
    public static final String ACCOUNT_DISABLED = "Your account has been disabled. If this is an error, please contact administration";
    public static final String ERROR_PROCESSING_FILE = "Error occurred while processing file";
    public static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";
    public static final String ERROR_PATH = "/error";
}
