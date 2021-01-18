package com.seven.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seven.admin.bean.entity.SysRoleEntity;
import com.seven.admin.bean.query.SysRoleQuery;
import com.seven.admin.constant.UserConstants;
import com.seven.admin.mapper.SysRoleMapper;
import com.seven.admin.service.SysRoleDeptService;
import com.seven.admin.service.SysRoleMenuService;
import com.seven.admin.service.SysUserRoleService;
import com.seven.comm.core.config.SevenQueryWrapper;
import com.seven.comm.core.enums.BetweenEnum;
import com.seven.comm.core.execption.SevenException;
import com.seven.comm.core.utils.SpringUtils;
import com.seven.comm.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seven.admin.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 角色信息表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2021-01-04 10:43:24
 */

@Slf4j
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private SysRoleMenuService roleMenuService;

    @Autowired
    private SysRoleDeptService roleDeptService;

    @Override
    public SysRoleEntity selectRoleById(Long roleId) {
        return sysRoleMapper.selectById(roleId);
    }

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRoleEntity> perms = sysRoleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRoleEntity perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<SysRoleEntity> selectByUserName(String userName) {
        return sysRoleMapper.selectByUserName(userName);
    }

    @Override
    public List<SysRoleEntity> selectRoleList(SysRoleQuery role) {
        SevenQueryWrapper<SysRoleEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.seq("del_flag", "0");
        queryWrapper.slike("role_name", role.getRoleName());
        queryWrapper.seq("status", role.getStatus());
        queryWrapper.slike("role_key", role.getRoleKey());
        queryWrapper.dateBetween("create_time", role, BetweenEnum.ALL_CONTAIN);
        queryWrapper.last(role.getDataScope());
        return sysRoleMapper.selectList(queryWrapper);
    }

    @Override
    public List<SysRoleEntity> selectRoleAll() {
        return SpringUtils.getAopProxy(this).selectRoleList(new SysRoleQuery());
    }

    @Override
    public List<Integer> selectRoleListByUserId(Long userId) {
        return sysRoleMapper.selectRoleIdByUserId(userId);
    }

    @Override
    public String checkRoleNameUnique(SysRoleEntity role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRoleEntity info = checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    private SysRoleEntity checkRoleNameUnique(String roleName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_name", roleName);
        queryWrapper.last(" limit 1");
        return sysRoleMapper.selectOne(queryWrapper);
    }

    @Override
    public String checkRoleKeyUnique(SysRoleEntity role) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_key", role.getRoleKey());
        queryWrapper.last(" limit 1");
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRoleEntity roleEntity = sysRoleMapper.selectOne(queryWrapper);
        if (roleEntity != null && !roleEntity.getRoleId().equals(roleId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public void checkRoleAllowed(SysRoleEntity role) {
        if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin()) {
            throw new SevenException("不允许操作超级管理员角色");
        }
    }

    @Override
    public int countUserRoleByRoleId(Long roleId) {
        return userRoleService.countByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRole(SysRoleEntity role) {
        sysRoleMapper.insert(role);
        return roleMenuService.insertRoleMenu(role);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRole(SysRoleEntity role) {
        // 修改角色信息
        sysRoleMapper.updateById(role);
        // 删除角色与菜单关联
        roleMenuService.deleteByRoleId(role.getRoleId());
        return roleMenuService.insertRoleMenu(role);
    }

    @Override
    public int updateRoleStatus(SysRoleEntity role) {
        SysRoleEntity roleEntity = selectRoleById(role.getRoleId());
        if (roleEntity != null) {
            roleEntity.setStatus(role.getStatus());
            roleEntity.setUpdateTime(new Date());
            return sysRoleMapper.updateById(roleEntity);
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int authDataScope(SysRoleEntity role) {
        SysRoleEntity roleEntity = selectRoleById(role.getRoleId());
        if (roleEntity!=null){
            roleEntity.setDataScope(role.getDataScope());
            roleEntity.setUpdateTime(new Date());
            sysRoleMapper.updateById(roleEntity);
            roleDeptService.deleteByRoleId(role.getRoleId());
        }
        return roleDeptService.insertRoleDept(role);
    }


    @Override
    public int deleteRoleById(Long roleId) {
        return 0;
    }

    @Override
    public int deleteRoleByIds(Long[] roleIds) {
        return 0;
    }


}
