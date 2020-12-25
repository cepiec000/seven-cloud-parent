package com.seven.admin.service;

import com.seven.admin.bean.dto.AddUserDTO;
import com.seven.admin.bean.dto.EditUserDTO;
import com.seven.admin.bean.dto.UserRoleDTO;
import com.seven.admin.bean.query.RoleQuery;
import com.seven.admin.bean.query.UserQuery;
import com.seven.admin.bean.vo.UserVO;
import com.seven.admin.bean.entity.SysUserEntity;
import com.seven.comm.core.page.PageInfo;

/**
 * 用户表
 *
 * @author chendongdong
 * @date 2020-12-21 15:15:48
 * @version 1.0
 */
public interface SysUserService {

    /**
     * 通过用户ID 获取用户
     * @param userId 用户ID
     * @return 用户对象
     */
    SysUserEntity get(Integer userId);

    /**
     * 用户名称 获取用户
     * @param username
     * @return
     */
    SysUserEntity getByUserName(String username);

    /**
     * 账号密码获取用户
     * @param username
     * @param password
     * @return
     */
    SysUserEntity login(String username, String password);

    /**
     * 获取用户列表
     * @param user
     * @return
     */
    PageInfo<UserVO> queryUserVoList(UserQuery user);

    /**
     * 添加用户
     * @param user
     * @return
     */
    Boolean addUser(AddUserDTO user);

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    SysUserEntity checkLoginNameUnique(String username);

    /**
     * 判断手机号是否重复
     * @param phone
     * @return
     */
    SysUserEntity checkPhoneUnique(String phone);

    /**
     * 判断手机号是否重复
     * @param email
     * @return
     */
    SysUserEntity checkEmailUnique(String email);

    /**
     * 修改用户
     * @param user
     * @return
     */
    Boolean updateUser(EditUserDTO user);

    /**
     * 重置用户密码
     * @param userId
     * @return
     */
    Boolean resetPwd(Integer userId);

    /**
     * 删除 一个 或 多个用户
     * @param ids
     * @return
     */
    Boolean delete(Integer [] ids);

    /**
     * 设置用户 角色
     * @param roleDTO
     * @return
     */
    Boolean setAuthRole(UserRoleDTO roleDTO);

    /**
     * 修改用户状态
     * @param userId
     * @param lockFlag
     * @return
     */
    Boolean changeLockFlag(Integer userId, Integer lockFlag);

    /**
     * 为分配 角色的用户列表
     * @param role
     * @return
     */
    PageInfo<UserVO> unassignedRoleList(UserQuery role);

    /**
     * 查询该部门下有多少用户
     * @return
     */
    int checkUserByDeptId(Integer deptId);
}

