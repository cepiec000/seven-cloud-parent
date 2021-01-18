package com.seven.mybatis.pagehelper;

import com.seven.mybatis.pagehelper.bean.Page;
import com.seven.mybatis.pagehelper.dialect.Chian;
import lombok.extern.slf4j.Slf4j;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/25 15:25
 */
@Slf4j
public class PageTools extends Chian {

    public PageTools() {
    }

    public static <E> Page<E> startPage(int pageNum, int pageSize) {
        return startPage(pageNum, pageSize, DEFAULT_COUNT);
    }

    public static <E> Page<E> startPage(int pageNum, int pageSize, String orderBy) {
        Page<E> page = startPage(pageNum, pageSize);
        page.setOrderBy(orderBy);
        return page;
    }

    public static <E> Page<E> startPage(int pageNum, int pageSize, boolean count) {
        Page<E> page = new Page(pageNum, pageSize, count);
        Page<E> oldPage = getLocalPage();
        if (oldPage != null && oldPage.getOrderBy() != null) {
            page.setOrderBy(oldPage.getOrderBy());
        }

        setLocalPage(page);
        return page;
    }

    public static <E> Page<E> offsetPage(int pageSize, int limit) {
        return offsetPage(pageSize, limit, DEFAULT_COUNT);
    }

    public static <E> Page<E> offsetPage(int pageSize, int limit, boolean count) {
        Page<E> page = new Page(new int[]{pageSize, limit}, count);
        Page<E> oldPage = getLocalPage();
        if (oldPage != null && oldPage.getOrderBy() != null) {
            page.setOrderBy(oldPage.getOrderBy());
        }
        setLocalPage(page);
        return page;
    }

    public static void orderBy(String orderBy) {
        if (orderBy == null || orderBy.trim().length() == 0) {
            return;
        }
        Page<?> page = getLocalPage();
        if (page != null) {
            page.setOrderBy(orderBy);
        } else {
            page = new Page();
            page.setOrderBy(orderBy);
            setLocalPage(page);
        }
    }
}
