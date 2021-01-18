package com.seven.admin.service;

import com.seven.admin.bean.entity.SysRoleEntity;

/**
 * 角色和菜单关联表
 *
 * @author chendongdong
 * @date 2021-01-04 10:43:24
 * @version 1.0
 */
public interface SysRoleMenuService {

    /**
     * 根据menuId 统计
     * @param menuId
     * @return
     */
    int countByMenuId(Long menuId);

    /**
     * 批量新增 角色菜单
     * @param role
     * @return
     */
    int insertRoleMenu(SysRoleEntity role);

    /**
     * 根据 角色ID 删除关联
     * @param roleId
     * @return
     */
    int deleteByRoleId(Long roleId);
}

