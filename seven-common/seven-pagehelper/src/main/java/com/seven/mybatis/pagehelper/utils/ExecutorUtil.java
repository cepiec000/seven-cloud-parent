package com.seven.mybatis.pagehelper.utils;

import com.seven.mybatis.pagehelper.PageException;
import com.seven.mybatis.pagehelper.dialect.Dialect;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/25 16:12
 */
public class ExecutorUtil {
    private static Field additionalParametersField;
    private static Field providerMethodArgumentNamesField;

    public ExecutorUtil() {
    }

    public static Map<String, Object> getAdditionalParameter(BoundSql boundSql) {
        try {
            return (Map)additionalParametersField.get(boundSql);
        } catch (IllegalAccessException var2) {
            throw new PageException("获取 BoundSql 属性值 additionalParameters 失败: " + var2, var2);
        }
    }

    public static MappedStatement getExistedMappedStatement(Configuration configuration, String msId) {
        MappedStatement mappedStatement = null;

        try {
            mappedStatement = configuration.getMappedStatement(msId, false);
        } catch (Throwable var4) {
        }

        return mappedStatement;
    }

    public static Long executeManualCount(Executor executor, MappedStatement countMs, Object parameter, BoundSql boundSql, ResultHandler resultHandler) throws SQLException {
        CacheKey countKey = executor.createCacheKey(countMs, parameter, RowBounds.DEFAULT, boundSql);
        BoundSql countBoundSql = countMs.getBoundSql(parameter);
        Object countResultList = executor.query(countMs, parameter, RowBounds.DEFAULT, resultHandler, countKey, countBoundSql);
        Long count = ((Number)((List)countResultList).get(0)).longValue();
        return count;
    }

    public static Long executeAutoCount(Dialect dialect, Executor executor, MappedStatement countMs, Object parameter, BoundSql boundSql, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
        Map<String, Object> additionalParameters = getAdditionalParameter(boundSql);
        CacheKey countKey = executor.createCacheKey(countMs, parameter, RowBounds.DEFAULT, boundSql);
        String countSql = dialect.getCountSql(boundSql);
        BoundSql countBoundSql = new BoundSql(countMs.getConfiguration(), countSql, boundSql.getParameterMappings(), parameter);
        Iterator var11 = additionalParameters.keySet().iterator();

        while(var11.hasNext()) {
            String key = (String)var11.next();
            countBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
        }


        Object countResultList = executor.query(countMs, parameter, RowBounds.DEFAULT, resultHandler, countKey, countBoundSql);
        Long count = (Long)((List)countResultList).get(0);
        return count;
    }



    public static <E> List<E> pageQuery(Dialect dialect, Executor executor, MappedStatement ms, Object parameter,ResultHandler resultHandler, BoundSql boundSql, CacheKey cacheKey) throws SQLException {
        if (!dialect.beforePage()) {
            return executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, boundSql);
        } else {
            parameter = dialect.processParameterObject(ms, parameter, boundSql, cacheKey);
            String pageSql = dialect.getPageSql(boundSql);
            BoundSql pageBoundSql = new BoundSql(ms.getConfiguration(), pageSql, boundSql.getParameterMappings(), parameter);
            Map<String, Object> additionalParameters = getAdditionalParameter(boundSql);
            Iterator var12 = additionalParameters.keySet().iterator();

            while(var12.hasNext()) {
                String key = (String)var12.next();
                pageBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
            }


            return executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, pageBoundSql);
        }
    }

    static {
        try {
            additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
            additionalParametersField.setAccessible(true);
        } catch (NoSuchFieldException var2) {
            throw new PageException("获取 BoundSql 属性 additionalParameters 失败: " + var2, var2);
        }

        try {
            providerMethodArgumentNamesField = ProviderSqlSource.class.getDeclaredField("providerMethodArgumentNames");
            providerMethodArgumentNamesField.setAccessible(true);
        } catch (NoSuchFieldException var1) {
        }

    }
}
