package com.seven.comm.core.utils.sign;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/29 16:48
 */
public class Base64Utils {
    private Base64Utils() {
    }

    /**
     * 加密JDK1.8
     *
     * @param str
     * @return java.lang.String
     * @author dolyw.com
     * @date 2018/8/21 15:28
     */
    public static String encode(String str) throws UnsupportedEncodingException {
        byte[] encodeBytes = Base64.getEncoder().encode(str.getBytes("utf-8"));
        return new String(encodeBytes);
    }

    public static String encode(byte[] bytes) {
        byte[] encodeBytes = Base64.getEncoder().encode(bytes);
        return new String(encodeBytes);
    }

    /**
     * 解密JDK1.8
     *
     * @param str
     * @return java.lang.String
     * @author dolyw.com
     * @date 2018/8/21 15:28
     */
    public static String decode(String str) throws UnsupportedEncodingException {
        byte[] decodeBytes = Base64.getDecoder().decode(str.getBytes("utf-8"));
        return new String(decodeBytes);
    }
}
