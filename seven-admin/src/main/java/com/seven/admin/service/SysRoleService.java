package com.seven.admin.service;

import com.seven.admin.bean.entity.SysRoleEntity;
import com.seven.admin.bean.query.SysRoleQuery;

import java.util.List;
import java.util.Set;

/**
 * 角色信息表
 *
 * @author chendongdong
 * @date 2021-01-04 10:43:24
 * @version 1.0
 */
public interface SysRoleService {

    /**
     * 通過ID获取
     * @param roleId
     * @return
     */
    SysRoleEntity selectRoleById(Long roleId);

    /**
     * 获取用户所有角色标识
     * @param userId
     * @return
     */
    Set<String> selectRolePermissionByUserId(Long userId);

    /**
     * 根据用户名称 查询角色
     * @param userName
     * @return
     */
    List<SysRoleEntity> selectByUserName(String userName);



    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    public List<SysRoleEntity> selectRoleList(SysRoleQuery role);


    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    public List<SysRoleEntity> selectRoleAll();

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    public List<Integer> selectRoleListByUserId(Long userId);



    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    public String checkRoleNameUnique(SysRoleEntity role);

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    public String checkRoleKeyUnique(SysRoleEntity role);

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    public void checkRoleAllowed(SysRoleEntity role);

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    public int countUserRoleByRoleId(Long roleId);

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    public int insertRole(SysRoleEntity role);

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    public int updateRole(SysRoleEntity role);

    /**
     * 修改角色状态
     *
     * @param role 角色信息
     * @return 结果
     */
    public int updateRoleStatus(SysRoleEntity role);

    /**
     * 修改数据权限信息
     *
     * @param role 角色信息
     * @return 结果
     */
    public int authDataScope(SysRoleEntity role);

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleById(Long roleId);

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    public int deleteRoleByIds(Long[] roleIds);
}

