package com.seven.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seven.admin.bean.entity.*;
import com.seven.admin.bean.query.SysUserQuery;
import com.seven.admin.constant.UserConstants;
import com.seven.admin.mapper.SysUserMapper;
import com.seven.admin.service.*;
import com.seven.comm.core.execption.SevenException;
import com.seven.comm.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 用户信息表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2021-01-04 10:43:24
 */

@Slf4j
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysPostService sysPostService;

    @Autowired
    private SysUserPostService userPostService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Override
    public int selectCountByDeptId(Long deptId) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_id", deptId);
        queryWrapper.eq("del_flag", "0");
        return sysUserMapper.selectCount(queryWrapper);
    }

    @Override
    public List<SysUserEntity> selectUserList(SysUserQuery user) {
        return sysUserMapper.selectUserList(user);
    }

    @Override
    public SysUserEntity selectUserByUserName(String userName) {
        QueryWrapper queryWrapper = new QueryWrapper<>().eq("user_name", userName);
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public SysUserEntity selectUserById(Long userId) {
        return sysUserMapper.selectById(userId);
    }

    @Override
    public String selectUserRoleGroup(String userName) {
        List<SysRoleEntity> list = sysRoleService.selectByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysRoleEntity role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    @Override
    public String selectUserPostGroup(String userName) {
        List<SysPostEntity> list = sysPostService.selectPostsByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysPostEntity post : list) {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    @Override
    public String checkUserNameUnique(String userName) {
        QueryWrapper queryWrapper = new QueryWrapper<>().eq("user_name", userName);
        int count = sysUserMapper.selectCount(queryWrapper);
        return count > 0 ? UserConstants.NOT_UNIQUE : UserConstants.UNIQUE;
    }

    @Override
    public String checkPhoneUnique(SysUserEntity user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        QueryWrapper queryWrapper = new QueryWrapper<>().eq("phonenumber", user.getPhonenumber());
        SysUserEntity userEntity = sysUserMapper.selectOne(queryWrapper);
        if (userEntity != null && !userEntity.getUserId().equals(userId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkEmailUnique(SysUserEntity user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        QueryWrapper queryWrapper = new QueryWrapper<>().eq("email", user.getEmail());
        SysUserEntity userEntity = sysUserMapper.selectOne(queryWrapper);
        if (userEntity != null && !userEntity.getUserId().equals(userId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public void checkUserAllowed(SysUserEntity user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new SevenException("不允许操作超级管理员用户");
        }
    }

    @Override
    public int insertUser(SysUserEntity user) {
        int rows = sysUserMapper.insert(user);
        userRoleService.insertUserRole(user);
        userPostService.insertUserPost(user);
        return rows;
    }


    @Override
    public int updateUser(SysUserEntity user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleService.deleteByUserId(userId);
        // 新增用户与角色管理
        userRoleService.insertUserRole(user);
        // 删除用户与岗位关联
        userPostService.deleteByUserId(userId);
        // 新增用户与岗位管理
        userPostService.insertUserPost(user);
        return sysUserMapper.updateById(user);
    }

    @Override
    public int updateUserStatus(SysUserEntity user) {
        SysUserEntity userEntity = selectUserById(user.getUserId());
        userEntity.setStatus(user.getStatus());
        userEntity.setUpdateTime(new Date());
        return sysUserMapper.updateById(userEntity);
    }

    @Override
    public int updateUserProfile(SysUserEntity user) {
        return sysUserMapper.updateById(user);
    }

    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        SysUserEntity userEntity = selectUserByUserName(userName);
        userEntity.setAvatar(avatar);
        userEntity.setUpdateTime(new Date());
        if (sysUserMapper.updateById(userEntity) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int resetPwd(SysUserEntity user) {
        SysUserEntity userEntity = selectUserById(user.getUserId());
        userEntity.setPassword(user.getPassword());
        userEntity.setUpdateTime(new Date());
        return sysUserMapper.updateById(userEntity);
    }

    @Override
    public int resetUserPwd(String userName, String password) {
        SysUserEntity userEntity = selectUserByUserName(userName);
        userEntity.setPassword(password);
        userEntity.setUpdateTime(new Date());
        return sysUserMapper.updateById(userEntity);
    }

    @Override
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleService.deleteByUserId(userId);
        // 删除用户与岗位表
        userPostService.deleteByUserId(userId);
        return sysUserMapper.deleteById(userId);
    }

    @Override
    public int deleteUserByIds(Long[] userIds) {
        for (Long userId : userIds)
        {
            checkUserAllowed(new SysUserEntity(userId));
        }
        // 删除用户与角色关联
        userRoleService.deleteByUserIds(userIds);
        // 删除用户与岗位关联
        userPostService.deleteByUserIds(userIds);
        return sysUserMapper.deleteBatchIds(Arrays.asList(userIds));
    }


}
