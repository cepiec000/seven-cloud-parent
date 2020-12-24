package com.seven.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.admin.bean.dto.AddRoleDTO;
import com.seven.admin.bean.dto.EditRoleDTO;
import com.seven.admin.bean.dto.RoleUserDTO;
import com.seven.admin.bean.dto.UserRoleOnlyDTO;
import com.seven.admin.bean.entity.SysRoleEntity;
import com.seven.admin.bean.entity.SysUserEntity;
import com.seven.admin.bean.entity.SysUserRoleEntity;
import com.seven.admin.bean.query.RoleQuery;
import com.seven.admin.bean.vo.RoleVO;
import com.seven.admin.bean.vo.UserVO;
import com.seven.admin.constant.AdminStatCode;
import com.seven.admin.enums.DeleteEnum;
import com.seven.admin.mapper.SysRoleMapper;
import com.seven.admin.service.SysRoleMenuService;
import com.seven.admin.service.SysRoleService;
import com.seven.admin.service.SysUserRoleService;
import com.seven.comm.core.config.SevenQueryWrapper;
import com.seven.comm.core.enums.BetweenEnum;
import com.seven.comm.core.execption.SevenException;
import com.seven.comm.core.page.Page;
import com.seven.comm.core.page.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 系统角色表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2020-12-21 15:15:48
 */

@Slf4j
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysUserRoleService userRoleService;
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysRoleMenuService roleMenuService;


    @Override
    public List<SysRoleEntity> queryRolesByUserId(Integer userId) {
        List<SysRoleEntity> result = new ArrayList<>();
        List<Integer> ruleIds = userRoleService.queryRoleIdByUserId(userId);
        if (CollectionUtils.isEmpty(ruleIds)) {
            return result;
        }
        QueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id", ruleIds);
        queryWrapper.eq("del_flag", DeleteEnum.NO_DELETE.getCode());
        return roleMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addRule(AddRoleDTO roleDTO) {
        if (checkRoleNameUnique(roleDTO.getRoleName()) != null) {
            throw new SevenException(AdminStatCode.ROLE_NAME_DUPLICATE.getMsg());
        }
        if (checkCodeUnique(roleDTO.getRoleCode()) != null) {
            throw new SevenException(AdminStatCode.ROLE_CODE_DUPLICATE.getMsg());
        }
        SysRoleEntity roleEntity = new SysRoleEntity();
        BeanUtils.copyProperties(roleDTO, roleEntity);
        roleEntity.setDelFlag(DeleteEnum.NO_DELETE.getCode());
        roleEntity.setCreateTime(new Date());
        roleEntity.setUpdateTime(new Date());
        if (roleMapper.insert(roleEntity) > 0
                && roleMenuService.addRoleMenu(roleEntity.getRoleId(), roleDTO.getMenuIds()) > 0) {
            return true;
        }
        return false;
    }


    @Override
    public Boolean updateRole(EditRoleDTO roleDTO) {
        SysRoleEntity roleEntity = checkRoleNameUnique(roleDTO.getRoleName());
        if (roleEntity != null && !roleEntity.getRoleId().equals(roleDTO.getRoleId())) {
            throw new SevenException(AdminStatCode.ROLE_NAME_DUPLICATE.getMsg());
        }
        roleEntity = checkCodeUnique(roleDTO.getRoleCode());
        if (roleEntity != null && !roleEntity.getRoleId().equals(roleDTO.getRoleId())) {
            throw new SevenException(AdminStatCode.ROLE_CODE_DUPLICATE.getMsg());
        }
        BeanUtils.copyProperties(roleDTO, roleEntity);
        roleEntity.setUpdateTime(new Date());
        if (roleMapper.updateById(roleEntity) > 0) {
            //删除角色下所有菜单
            roleMenuService.deleteByRoleId(roleDTO.getRoleId());
            //重新添加角色菜单
            roleMenuService.addRoleMenu(roleEntity.getRoleId(), roleDTO.getMenuIds());
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer[] roleIds) {
        if (roleIds == null || roleIds.length == 0) {
            return false;
        }
        for (Integer id : roleIds) {
            List<SysUserRoleEntity> list = userRoleService.queryUserByRoleId(id);
            if (CollectionUtils.isNotEmpty(list)) {
                SysRoleEntity roleEntity = get(id);
                throw new SevenException(String.format("%1$s已分配,不能删除", roleEntity.getRoleName()));
            }
            //删除角色与菜单的管理
            roleMenuService.deleteByRoleId(id);
            //删除角色
            roleMapper.deleteById(id);
        }
        return true;
    }

    @Override
    public SysRoleEntity get(Integer id) {
        return roleMapper.selectById(id);
    }

    @Override
    public PageInfo<RoleVO> queryRoleList(RoleQuery role) {
        PageInfo<RoleVO> pageInfo = new PageInfo<>();

        SevenQueryWrapper<SysRoleEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.seq("role_name", role.getRoleName());
        queryWrapper.slikeLeft("role_code", role.getRoleCode());
        queryWrapper.dateBetween("create_time", role.getBegin(), role.getEnd(), BetweenEnum.ALL_CONTAIN);

        Page<SysRoleEntity> page = roleMapper.selectByPage(queryWrapper, role.getPageNo(), role.getSize());
        List<SysRoleEntity> list = page.getRows();
        if (CollectionUtils.isEmpty(list)) {
            return pageInfo;
        }
        List<RoleVO> nList = new ArrayList<>(list.size());
        list.forEach(item -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(item, roleVO);
            nList.add(roleVO);
        });
        pageInfo.setRows(nList);
        return pageInfo;
    }

    @Override
    public SysRoleEntity checkRoleNameUnique(String roleName) {
        SevenQueryWrapper<SysRoleEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.seq("role_name", roleName);
        queryWrapper.eq("del_flag", DeleteEnum.NO_DELETE.getCode());
        return roleMapper.selectOne(queryWrapper);
    }

    @Override
    public SysRoleEntity checkCodeUnique(String roleCode) {
        SevenQueryWrapper<SysRoleEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.seq("role_code", roleCode);
        queryWrapper.eq("del_flag", DeleteEnum.NO_DELETE.getCode());
        return roleMapper.selectOne(queryWrapper);
    }

    @Override
    public Boolean authUserAll(RoleUserDTO roleUserDTO) {
        List<SysUserRoleEntity> list = new ArrayList<>();
        for (Integer userId : roleUserDTO.getUserIds()) {
            SysUserRoleEntity userRole = new SysUserRoleEntity();
            userRole.setRoleId(roleUserDTO.getRoleId());
            userRole.setUserId(userId);
            list.add(userRole);
        }
        if (userRoleService.batchSaveUserRole(list) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean cancelAuthUser(UserRoleOnlyDTO userRoleOnly) {
        SysUserRoleEntity userRoleEntity=new SysUserRoleEntity();
        userRoleEntity.setUserId(userRoleOnly.getUserId());
        userRoleEntity.setRoleId(userRoleOnly.getRoleId());
        return userRoleService.delete(userRoleEntity);
    }


}
