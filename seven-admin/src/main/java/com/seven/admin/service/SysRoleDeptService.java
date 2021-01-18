package com.seven.admin.service;

import com.seven.admin.bean.entity.SysRoleEntity;

/**
 * 角色和部门关联表
 *
 * @author chendongdong
 * @date 2021-01-04 10:43:24
 * @version 1.0
 */
public interface SysRoleDeptService {

    /**
     * 根据角色ID 删除
     * @param roleId
     * @return
     */
    int deleteByRoleId(Long roleId);

    /**
     * 批量新增 角色 与部门关系
     * @param role
     * @return
     */
    int insertRoleDept(SysRoleEntity role);
}

