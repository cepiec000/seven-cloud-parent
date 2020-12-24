package com.seven.admin.service;

import com.seven.admin.bean.entity.SysMenuEntity;

import java.util.List;

/**
 * 菜单权限表
 *
 * @author chendongdong
 * @date 2020-12-21 15:15:48
 * @version 1.0
 */
public interface SysMenuService {

    /**
     * 根据用户ID获取 用户角色 下所有菜单
     * @param userId
     * @return
     */
    List<SysMenuEntity> queryMenusByUserId(Integer userId);
}

