package com.seven.comm.core.constant;

public class CommonConst {
    public static final String SEVEN_SECURITY_KEY = "sevenpw123";
    /**
     * redis过期时间，以秒为单位，一天
     */
    public static final int EXRP_DAY = 60 * 60 * 24;
    /**
     * redis过期时间，以秒为单位，一分钟
     */
    public static final int EXRP_MINUTE = 60;
    /**
     * JWT-account:
     */
    public static final String ACCOUNT = "account";
    public static final String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";
    public static final String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";
    public static final String PREFIX_SHIRO_CACHE = "shiro:cache:";
    public static final String CURRENT_TIME_MILLIS = "currentTimeMillis";
    public static final String TOKEN_HEADER_NAME = "auth_user_token";
}
