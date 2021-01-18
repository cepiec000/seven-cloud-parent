package com.seven.mybatis.pagehelper.bean;

import java.util.List;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/28 15:18
 */
public interface PageResultCallback<T> {
    /**
     * 用于回调 函数
     *
     * @param oList
     * @return
     */
    List<?> callback(List<T> oList);
}
