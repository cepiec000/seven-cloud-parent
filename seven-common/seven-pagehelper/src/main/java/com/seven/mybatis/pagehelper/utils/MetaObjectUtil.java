package com.seven.mybatis.pagehelper.utils;

import com.seven.mybatis.pagehelper.PageException;
import org.apache.ibatis.reflection.MetaObject;

import java.lang.reflect.Method;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/25 18:36
 */
public class MetaObjectUtil {
    public static Method method;

    public MetaObjectUtil() {
    }

    public static MetaObject forObject(Object object) {
        try {
            return (MetaObject)method.invoke((Object)null, object);
        } catch (Exception var2) {
            throw new PageException(var2);
        }
    }

    static {
        try {
            Class.forName("org.apache.ibatis.reflection.ReflectorFactory");
            Class<?> metaClass = Class.forName("com.github.pagehelper.util.MetaObjectWithReflectCache");
            method = metaClass.getDeclaredMethod("forObject", Object.class);
        } catch (Throwable var5) {
            try {
                Class<?> metaClass = Class.forName("org.apache.ibatis.reflection.SystemMetaObject");
                method = metaClass.getDeclaredMethod("forObject", Object.class);
            } catch (Exception var4) {
                try {
                    Class<?> metaClass = Class.forName("org.apache.ibatis.reflection.MetaObject");
                    method = metaClass.getDeclaredMethod("forObject", Object.class);
                } catch (Exception var3) {
                    throw new PageException(var3);
                }
            }
        }

    }
}
