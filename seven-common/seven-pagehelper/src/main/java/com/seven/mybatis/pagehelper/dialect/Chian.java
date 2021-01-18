package com.seven.mybatis.pagehelper.dialect;

import com.seven.mybatis.pagehelper.bean.Page;
import com.seven.mybatis.pagehelper.utils.MetaObjectUtil;
import com.seven.mybatis.pagehelper.utils.SQLTools;
import com.seven.mybatis.pagehelper.utils.StringUtil;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/28 11:24
 */
public class Chian implements Dialect {
    protected static final ThreadLocal<Page> LOCAL_PAGE_MAP = new ThreadLocal();
    protected static boolean DEFAULT_COUNT = true;

    public static <T> Page<T> getLocalPage() {
        return (Page) LOCAL_PAGE_MAP.get();
    }

    protected static void setLocalPage(Page page) {
        LOCAL_PAGE_MAP.set(page);
    }

    public static void clearPage() {
        LOCAL_PAGE_MAP.remove();
    }

    @Override
    public boolean skip(RowBounds rowBounds) {
        Page page = getLocalPage();
        if (page == null) {
            return false;
        } else {
            if (rowBounds != RowBounds.DEFAULT) {
                page = new Page(rowBounds.getOffset(), rowBounds.getLimit());
            }
            setLocalPage(page);
            return true;
        }
    }

    @Override
    public boolean beforeCount() {
        Page page = getLocalPage();
        return page.isCount();
    }

    @Override
    public boolean afterCount(long count) {
        Page page = getLocalPage();
        page.setTotal(count);
        return page.getPageNum() > 0 && count > page.getStartRow();
    }

    @Override
    public Object afterPage(List pageList) {
        Page page = getLocalPage();
        if (page == null) {
            return pageList;
        } else {
            page.addAll(pageList);
            if (!page.isCount()) {
                page.setTotal(-1L);
            } else if (page.getPageSize() == 0) {
                page.setTotal((long) pageList.size());
            }
            return page;
        }
    }

    @Override
    public String getCountSql(BoundSql boundSql) {
        return SQLTools.getSimpleCountSql(boundSql.getSql(), "0");
    }

    @Override
    public boolean beforePage() {
        Page page = getLocalPage();
        return page.getPageSize() > 0;
    }

    @Override
    public void afterAll() {
        clearPage();
    }

    @Override
    public String getPageSql(BoundSql boundSql) {
        String sql = boundSql.getSql();
        Page page = getLocalPage();
        String orderBy = page.getOrderBy();
        if (StringUtil.isNotEmpty(orderBy)) {
            sql = SQLTools.converToOrderBySql(sql, orderBy);
        }

        return page.isCount() ? SQLTools.getMysqlPageSql(sql, page) : sql;
    }

    @Override
    public Object processParameterObject(MappedStatement ms, Object parameterObject, BoundSql boundSql, CacheKey pageKey) {
        Page page = getLocalPage();
        if (!page.isCount()){
            return parameterObject;
        }
        Map<String, Object> paramMap = new HashMap<>();
        if (parameterObject != null && parameterObject instanceof Map) {
            paramMap.putAll((Map) parameterObject);
        }
        return this.processPageParameter(ms, paramMap, page, boundSql, pageKey);
    }

    public Object processPageParameter(MappedStatement ms, Map<String, Object> paramMap, Page page, BoundSql boundSql, CacheKey pageKey) {
        paramMap.put("seven_page_start", page.getStartRow());
        paramMap.put("seven_page_end", page.getPageSize());
        pageKey.update(page.getStartRow());
        pageKey.update(page.getPageSize());
        if (boundSql.getParameterMappings() != null) {
            List<ParameterMapping> newParameterMappings = new ArrayList(boundSql.getParameterMappings());
            if (page.getStartRow() == 0L) {
                newParameterMappings.add((new ParameterMapping.Builder(ms.getConfiguration(), "seven_page_end", Integer.TYPE)).build());
            } else {
                newParameterMappings.add((new ParameterMapping.Builder(ms.getConfiguration(), "seven_page_start", Long.TYPE)).build());
                newParameterMappings.add((new ParameterMapping.Builder(ms.getConfiguration(), "seven_page_end", Integer.TYPE)).build());
            }

            MetaObject metaObject = MetaObjectUtil.forObject(boundSql);
            metaObject.setValue("parameterMappings", newParameterMappings);
        }

        return paramMap;
    }
}
