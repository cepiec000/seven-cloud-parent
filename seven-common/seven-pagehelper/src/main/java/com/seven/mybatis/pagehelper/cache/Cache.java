package com.seven.mybatis.pagehelper.cache;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/25 14:12
 */
public interface Cache<K, V> {

    V get(K key);

    void put(K key, V value);
}