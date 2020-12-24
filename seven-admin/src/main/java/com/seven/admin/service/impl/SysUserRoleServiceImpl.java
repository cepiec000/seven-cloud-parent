package com.seven.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.admin.bean.entity.SysUserRoleEntity;
import com.seven.admin.mapper.SysUserRoleMapper;
import com.seven.admin.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户角色表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2020-12-21 15:15:48
 */

@Slf4j
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Override
    public List<Integer> queryRoleIdByUserId(Integer userId) {
        final List<Integer> list = new ArrayList<Integer>();
        if (userId == null || userId.equals(0)) {
            return list;
        }
        List<SysUserRoleEntity> roles = queryRoleByUserId(userId);
        roles.parallelStream().forEach(role -> {
            list.add(role.getRoleId());
        });
        return list;
    }

    @Override
    public List<SysUserRoleEntity> queryRoleByUserId(Integer userId) {
        List<SysUserRoleEntity> roles = new ArrayList<SysUserRoleEntity>();
        if (userId == null || userId.equals(0)) {
            return roles;
        }
        QueryWrapper<SysUserRoleEntity> queryWrapper = new QueryWrapper<SysUserRoleEntity>();
        queryWrapper.eq("user_id", userId);
        return userRoleMapper.selectList(queryWrapper);
    }

    @Override
    public int addUserRole(Integer userId, List<Integer> roleIds) {
        if (userId == null || userId.equals(0) || CollectionUtils.isEmpty(roleIds)) {
            return 0;
        }
        List<SysUserRoleEntity> list = new ArrayList<SysUserRoleEntity>();
        roleIds.forEach(item -> {
            SysUserRoleEntity userRole = new SysUserRoleEntity();
            userRole.setUserId(userId);
            userRole.setRoleId(item);
            list.add(userRole);
        });
        if (list.size() > 0 && saveBatch(list)) {
            return list.size();
        }
        return 0;
    }

    @Override
    public int deleteByUserId(Integer userId) {
        QueryWrapper<SysUserRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return userRoleMapper.delete(query());
    }

    @Override
    public List<SysUserRoleEntity> queryUserByRoleId(Integer roleId) {
        QueryWrapper<SysUserRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        return userRoleMapper.selectList(query());
    }

    @Override
    public int batchSaveUserRole(List<SysUserRoleEntity> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        saveBatch(list);
        return list.size();
    }

    @Override
    public Boolean delete(SysUserRoleEntity userRoleEntity) {
        QueryWrapper<SysUserRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", userRoleEntity.getRoleId());
        queryWrapper.eq("user_id", userRoleEntity.getUserId());
        if (userRoleMapper.delete(queryWrapper) > 0) {
            return true;
        }
        return false;
    }
}
