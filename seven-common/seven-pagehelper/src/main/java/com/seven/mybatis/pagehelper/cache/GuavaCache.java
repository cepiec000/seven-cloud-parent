package com.seven.mybatis.pagehelper.cache;


import com.google.common.cache.CacheBuilder;
import com.seven.mybatis.pagehelper.utils.StringUtil;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/25 14:14
 */
public class  GuavaCache<K, V> implements Cache<K, V> {

    private final com.google.common.cache.Cache<K, V> CACHE;

    public GuavaCache(Properties properties, String prefix) {
        CacheBuilder cacheBuilder = CacheBuilder.newBuilder();
        String maximumSize = properties.getProperty(prefix + ".maximumSize");
        if (StringUtil.isNotEmpty(maximumSize)) {
            cacheBuilder.maximumSize(Long.parseLong(maximumSize));
        } else {
            cacheBuilder.maximumSize(1000);
        }
        String expireAfterAccess = properties.getProperty(prefix + ".expireAfterAccess");
        if (StringUtil.isNotEmpty(expireAfterAccess)) {
            cacheBuilder.expireAfterAccess(Long.parseLong(expireAfterAccess), TimeUnit.MILLISECONDS);
        }
        String expireAfterWrite = properties.getProperty(prefix + ".expireAfterWrite");
        if (StringUtil.isNotEmpty(expireAfterWrite)) {
            cacheBuilder.expireAfterWrite(Long.parseLong(expireAfterWrite), TimeUnit.MILLISECONDS);
        }
        String initialCapacity = properties.getProperty(prefix + ".initialCapacity");
        if (StringUtil.isNotEmpty(initialCapacity)) {
            cacheBuilder.initialCapacity(Integer.parseInt(initialCapacity));
        }
        CACHE = cacheBuilder.build();
    }

    @Override
    public V get(K key) {
        return CACHE.getIfPresent(key);
    }

    @Override
    public void put(K key, V value) {
        CACHE.put(key, value);
    }
}