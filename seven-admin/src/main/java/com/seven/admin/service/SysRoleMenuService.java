package com.seven.admin.service;

/**
 * 角色菜单表
 *
 * @author chendongdong
 * @date 2020-12-21 15:15:48
 * @version 1.0
 */
public interface SysRoleMenuService {

    /**
     * 删除菜单 根据 角色Id
     * @param roleId
     */
    void deleteByRoleId(Integer roleId);

    /**
     * 添加角色 菜单
     * @param roleId
     * @param menuIds
     * @return
     */
    int addRoleMenu(Integer roleId, Integer[] menuIds);
}

