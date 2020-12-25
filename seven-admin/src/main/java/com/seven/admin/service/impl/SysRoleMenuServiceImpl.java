package com.seven.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.admin.bean.entity.SysRoleMenuEntity;
import com.seven.admin.mapper.SysRoleMenuMapper;
import com.seven.admin.service.SysRoleMenuService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


/**
 * 角色菜单表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2020-12-21 15:15:48
 */

@Slf4j
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenuEntity> implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Override
    public void deleteByRoleId(Integer roleId) {
        QueryWrapper<SysRoleMenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        roleMenuMapper.delete(queryWrapper);
    }

    @Override
    public int addRoleMenu(Integer roleId, List<Integer> menuIds ) {
        if (roleId == null || menuIds == null) {
            return 0;
        }
        List<SysRoleMenuEntity> list = new ArrayList<>();
        for (Integer menuId : menuIds) {
            SysRoleMenuEntity roleMenu = new SysRoleMenuEntity();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            list.add(roleMenu);
        }
        if (CollectionUtils.isNotEmpty(list)) {
            saveBatch(list);
        }
        return list.size();
    }
}
