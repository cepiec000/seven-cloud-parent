package com.seven.admin.service;

import com.seven.admin.bean.dto.AddRoleDTO;
import com.seven.admin.bean.dto.EditRoleDTO;
import com.seven.admin.bean.dto.RoleUserDTO;
import com.seven.admin.bean.dto.UserRoleOnlyDTO;
import com.seven.admin.bean.entity.SysRoleEntity;
import com.seven.admin.bean.entity.SysUserEntity;
import com.seven.admin.bean.query.RoleQuery;
import com.seven.admin.bean.vo.RoleVO;
import com.seven.admin.bean.vo.UserVO;
import com.seven.comm.core.page.PageInfo;

import java.util.List;

/**
 * 系统角色表
 *
 * @author chendongdong
 * @date 2020-12-21 15:15:48
 * @version 1.0
 */
public interface SysRoleService {
    /**
     * id 获取角色
     * @param id
     * @return
     */
     SysRoleEntity get(Integer id);

    /**
     * 根据用户ID 获取用户关联角色列表
     * @param userId
     * @return
     */
    List<SysRoleEntity> queryRolesByUserId(Integer userId);

    /**
     * 添加角色
     * @param roleDTO
     * @return
     */
    Boolean addRule(AddRoleDTO roleDTO);

    /**
     * 修改角色
     * @param roleDTO
     * @return
     */
    Boolean updateRole(EditRoleDTO roleDTO);

    /**
     * 批量删除角色
     * @param roleIds
     * @return
     */
    Boolean delete(Integer[] roleIds);

    /**
     * 查询角色列表
     * @param role
     * @return
     */
    PageInfo<RoleVO> queryRoleList(RoleQuery role);

    /**
     * 角色名称获取角色
     * @param roleName
     * @return
     */
    SysRoleEntity checkRoleNameUnique(String roleName);

    /**
     * 角色编号获取角色
     * @param roleCode
     * @return
     */
    SysRoleEntity checkCodeUnique(String roleCode);


    /**
     * 批量用户授权 橘色
     * @param roleUserDTO
     * @return
     */
    Boolean authUserAll(RoleUserDTO roleUserDTO);

    /**
     * 取消用户授权角色
     * @param userRoleOnly
     * @return
     */
    Boolean cancelAuthUser(UserRoleOnlyDTO userRoleOnly);
}

