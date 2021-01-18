package com.seven.mybatis.pagehelper;

import com.seven.mybatis.pagehelper.cache.Cache;
import com.seven.mybatis.pagehelper.cache.CacheFactory;
import com.seven.mybatis.pagehelper.dialect.Dialect;
import com.seven.mybatis.pagehelper.utils.ExecutorUtil;
import com.seven.mybatis.pagehelper.utils.MSUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/25 14:30
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
), @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
)})
@Slf4j
public class PageIntercept implements Interceptor {
    private volatile Dialect dialect;
    private String countSuffix = "_COUNT";
    protected Cache<String, MappedStatement> msCountMap = null;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            RowBounds rowBounds = (RowBounds) args[2];
            ResultHandler resultHandler = (ResultHandler) args[3];
            /**获取参数**/
            Object parameter = args[1];
            Executor executor = (Executor) invocation.getTarget();
            CacheKey cacheKey;
            BoundSql boundSql;
            if (args.length == 4) {
                boundSql = ms.getBoundSql(parameter);
                cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
            } else {
                cacheKey = (CacheKey) args[4];
                boundSql = (BoundSql) args[5];
            }

            /**判断是否继承Page来进行分页**/
            List resultList;
            if (this.dialect.skip(rowBounds)) {
                if (this.dialect.beforeCount()) {
                    Long count = this.count(executor, ms, parameter, rowBounds, (ResultHandler) null, boundSql);
                    if (!this.dialect.afterCount(count)) {
                        Object result = this.dialect.afterPage(new ArrayList());
                        return result;
                    }
                }
                resultList = ExecutorUtil.pageQuery(this.dialect, executor, ms, parameter, resultHandler, boundSql, cacheKey);
            } else {
                resultList = executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
            }
            Object result = this.dialect.afterPage(resultList);
            return result;
        }finally {
            if (this.dialect != null) {
                this.dialect.afterAll();
            }
        }
    }

    private Long count(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        String countMsId = ms.getId() + this.countSuffix;
        MappedStatement countMs = ExecutorUtil.getExistedMappedStatement(ms.getConfiguration(), countMsId);
        Long count;
        if (countMs != null) {
            count = ExecutorUtil.executeManualCount(executor, countMs, parameter, boundSql, resultHandler);
        } else {
            if (this.msCountMap != null) {
                countMs = (MappedStatement)this.msCountMap.get(countMsId);
            }
            if (countMs == null) {
                countMs = MSUtils.newCountMappedStatement(ms, countMsId);
                if (this.msCountMap != null) {
                    this.msCountMap.put(countMsId, countMs);
                }
            }
            count = ExecutorUtil.executeAutoCount(this.dialect, executor, countMs, parameter, boundSql, rowBounds, resultHandler);
        }
        return count;
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.msCountMap = CacheFactory.createCache(properties.getProperty("msCountCache"), "ms", properties);
        log.warn(properties.toString());
        try {
            Class<?> aClass = Class.forName("com.seven.mybatis.pagehelper.PageTools");
            this.dialect = (Dialect)aClass.newInstance();
        } catch (Exception var4) {
            throw new PageException(var4);
        }
    }

}
