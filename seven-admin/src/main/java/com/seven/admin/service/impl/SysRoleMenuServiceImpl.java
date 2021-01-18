package com.seven.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.admin.bean.entity.SysRoleEntity;
import com.seven.admin.bean.entity.SysRoleMenuEntity;
import com.seven.admin.mapper.SysRoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seven.admin.service.SysRoleMenuService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


/**
 * 角色和菜单关联表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2021-01-04 10:43:24
 */

@Slf4j
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenuEntity> implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public int countByMenuId(Long menuId) {
        QueryWrapper<SysRoleMenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_id", menuId);
        return sysRoleMenuMapper.selectCount(queryWrapper);
    }

    @Override
    public int insertRoleMenu(SysRoleEntity role) {
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenuEntity> list = new ArrayList<>();
        for (Long menuId : role.getMenuIds()) {
            SysRoleMenuEntity rm = new SysRoleMenuEntity();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0) {
            saveBatch(list);
            rows = list.size();
        }
        return rows;
    }

    @Override
    public int deleteByRoleId(Long roleId) {
        QueryWrapper<SysRoleMenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        return sysRoleMenuMapper.delete(queryWrapper);
    }
}
