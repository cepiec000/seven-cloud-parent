package com.seven.mybatis.pagehelper.cache;

import com.seven.mybatis.pagehelper.utils.StringUtil;
import org.apache.ibatis.cache.decorators.FifoCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.mapping.CacheBuilder;

import java.util.Properties;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/25 14:15
 */
public class SimpleCache<K, V> implements Cache<K, V> {

    private final org.apache.ibatis.cache.Cache CACHE;

    public SimpleCache(Properties properties, String prefix) {
        CacheBuilder cacheBuilder = new CacheBuilder("SQL_CACHE");
        String typeClass = properties.getProperty(prefix + ".typeClass");
        if (StringUtil.isNotEmpty(typeClass)) {
            try {
                cacheBuilder.implementation((Class<? extends org.apache.ibatis.cache.Cache>) Class.forName(typeClass));
            } catch (ClassNotFoundException e) {
                cacheBuilder.implementation(PerpetualCache.class);
            }
        } else {
            cacheBuilder.implementation(PerpetualCache.class);
        }
        String evictionClass = properties.getProperty(prefix + ".evictionClass");
        if (StringUtil.isNotEmpty(evictionClass)) {
            try {
                cacheBuilder.addDecorator((Class<? extends org.apache.ibatis.cache.Cache>) Class.forName(evictionClass));
            } catch (ClassNotFoundException e) {
                cacheBuilder.addDecorator(FifoCache.class);
            }
        } else {
            cacheBuilder.addDecorator(FifoCache.class);
        }
        String flushInterval = properties.getProperty(prefix + ".flushInterval");
        if (StringUtil.isNotEmpty(flushInterval)) {
            cacheBuilder.clearInterval(Long.parseLong(flushInterval));
        }
        String size = properties.getProperty(prefix + ".size");
        if (StringUtil.isNotEmpty(size)) {
            cacheBuilder.size(Integer.parseInt(size));
        }
        cacheBuilder.properties(properties);
        CACHE = cacheBuilder.build();
    }

    @Override
    public V get(K key) {
        Object value = CACHE.getObject(key);
        if (value != null) {
            return (V) value;
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        CACHE.putObject(key, value);
    }
}