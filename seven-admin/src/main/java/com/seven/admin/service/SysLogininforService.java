package com.seven.admin.service;

import com.seven.admin.bean.entity.SysLogininforEntity;
import com.seven.admin.bean.query.SysLogininforQuery;

import java.util.List;

/**
 * 系统访问记录
 *
 * @author chendongdong
 * @date 2021-01-04 10:43:24
 * @version 1.0
 */
public interface SysLogininforService {

    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    void insertLogininfor(SysLogininforEntity logininfor);

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    List<SysLogininforEntity> selectLogininforList(SysLogininforQuery logininfor);

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return
     */
    int deleteLogininforByIds(Long[] infoIds);

    /**
     * 清空系统登录日志
     */
    void cleanLogininfor();
}

