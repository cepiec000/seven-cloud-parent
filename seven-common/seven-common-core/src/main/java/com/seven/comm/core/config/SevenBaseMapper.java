package com.seven.comm.core.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.comm.core.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

public interface SevenBaseMapper<T> extends BaseMapper<T> {

    default Page<T> selectByPage(@Param("ew") QueryWrapper<T> queryWrapper, int index, int size, boolean count, String orderBy) {
        Page<T> page = new Page<T>();
        Integer total = 0;
        if (count) {
            total = selectCount(queryWrapper);
            if (total == null || total == 0) {
                return page;
            }
        }
        if (!StringUtils.isEmpty(orderBy)) {
            queryWrapper.last(orderBy);
        }
        if (index == 0) {
            index = 1;
        }
        int start = (index - 1) * size;
        queryWrapper.last(" limit " + start + "," + size);
        List<T> list = selectList(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return page;
        }
        page.setRows(list);
        return page;
    }

    default Page<T> selectByPage(@Param("ew") QueryWrapper<T> queryWrapper, int page, boolean count) {
        return selectByPage(queryWrapper, page, 20, count, null);
    }

    default Page<T> selectByPage(@Param("ew") QueryWrapper<T> queryWrapper, int page, int size) {
        return selectByPage(queryWrapper, page, size, true, null);
    }

    default Page<T> selectByPage(@Param("ew") QueryWrapper<T> queryWrapper, int page) {
        return selectByPage(queryWrapper, page, 20, true, null);
    }
}
