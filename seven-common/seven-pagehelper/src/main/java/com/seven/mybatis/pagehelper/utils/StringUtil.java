package com.seven.mybatis.pagehelper.utils;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/25 14:13
 */
public class StringUtil {
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
