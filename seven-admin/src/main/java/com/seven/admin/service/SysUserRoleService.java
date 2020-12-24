package com.seven.admin.service;

import com.seven.admin.bean.entity.SysUserRoleEntity;

import java.util.List;

/**
 * 用户角色表
 *
 * @author chendongdong
 * @date 2020-12-21 15:15:48
 * @version 1.0
 */
public interface SysUserRoleService {

    /**
     * 根据用户ID 获取所有 角色ID
     * @param userId
     * @return
     */
    List<Integer> queryRoleIdByUserId(Integer userId);

    /**
     * 根据用户ID 获取用户所有角色
     * @param userId
     * @return
     */
    List<SysUserRoleEntity> queryRoleByUserId(Integer userId);

    /**
     * 批量新增用户角色
     * @param userId
     * @param roleIds
     * @return
     */
    int addUserRole(Integer userId,List<Integer> roleIds);

    /**
     * 删除用户角色
     * @param id
     * @return
     */
    int deleteByUserId(Integer id);

    /**
     * 查看角色都跟那些用戶管理
     * @param roleId
     * @return
     */
    List<SysUserRoleEntity> queryUserByRoleId(Integer roleId);

    /**
     * 批量新增用户角色
     * @param list
     * @return
     */
    int batchSaveUserRole(List<SysUserRoleEntity> list);

    /**
     * 删除用户角色
     * @param userRoleEntity
     * @return
     */
    Boolean delete(SysUserRoleEntity userRoleEntity);
}

