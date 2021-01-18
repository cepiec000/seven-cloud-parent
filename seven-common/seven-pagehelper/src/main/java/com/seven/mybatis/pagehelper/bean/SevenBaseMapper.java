package com.seven.mybatis.pagehelper.bean;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.mybatis.pagehelper.PageTools;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SevenBaseMapper<T> extends BaseMapper<T> {

    default PageResult<T> selectByPage(@Param("ew") QueryWrapper<T> queryWrapper, int pageNum, int pageSize, boolean count, String orderBy) {
        PageTools.startPage(pageNum, pageSize, count);
        PageTools.orderBy(orderBy);
        List<T> list = selectList(queryWrapper);
        PageResult<T> pageResult = new PageResult<>(list);
        return pageResult;
    }

    default PageResult<T> selectByPage(@Param("ew") QueryWrapper<T> queryWrapper, int page, boolean count) {
        return selectByPage(queryWrapper, page, 20, count, null);
    }

    default PageResult<T> selectByPage(@Param("ew") QueryWrapper<T> queryWrapper, int page, int size) {
        return selectByPage(queryWrapper, page, size, true, null);
    }

    default PageResult<T> selectByPage(@Param("ew") QueryWrapper<T> queryWrapper, int page) {
        return selectByPage(queryWrapper, page, 20, true, null);
    }
}
