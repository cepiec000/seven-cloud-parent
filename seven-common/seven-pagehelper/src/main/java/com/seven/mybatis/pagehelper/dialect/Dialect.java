package com.seven.mybatis.pagehelper.dialect;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface Dialect {
    /**
     * 跳过 count 和 分页查询
     *
     * @param rowBounds       分页参数
     * @return true 跳过，返回默认查询结果，false 执行分页查询
     */
    boolean skip(RowBounds rowBounds);

    /**
     * 执行分页前，返回 true 会进行 count 查询，false 会继续下面的 beforePage 判断
     *
     * @return
     */
    boolean beforeCount();

    /**
     * 设置分页count
     * @param count
     * @return
     */
     boolean afterCount(long count);

    /**
     * 設置數據
     * @param pageList
     * @return
     */
    Object afterPage(List pageList);

    /**
     * 获取count sql
     * @param boundSql
     * @return
     */
    String getCountSql(BoundSql boundSql);

    /**
     * 判断是否为分页 pageSize是否大于0
     * @return
     */
    boolean beforePage();

    /**
     * 最后销毁当前page
     */
    void afterAll();

    /**
     * 获取当前分页 page sql
     * @param boundSql
     * @return
     */
    String getPageSql(BoundSql boundSql);

    /**
     * 设置当前分页参数
     * @param ms
     * @param parameterObject
     * @param boundSql
     * @param pageKey
     * @return
     */
     Object processParameterObject(MappedStatement ms, Object parameterObject, BoundSql boundSql, CacheKey pageKey);
}
