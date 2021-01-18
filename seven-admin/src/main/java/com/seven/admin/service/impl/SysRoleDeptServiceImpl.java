package com.seven.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.admin.bean.entity.SysRoleDeptEntity;
import com.seven.admin.bean.entity.SysRoleEntity;
import com.seven.admin.mapper.SysRoleDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seven.admin.service.SysRoleDeptService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


/**
 * 角色和部门关联表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2021-01-04 10:43:24
 */

@Slf4j
@Service("sysRoleDeptService")
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptMapper, SysRoleDeptEntity> implements SysRoleDeptService {
    @Autowired
    private SysRoleDeptMapper sysRoleDeptMapper;

    @Override
    public int deleteByRoleId(Long roleId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id", roleId);
        return sysRoleDeptMapper.delete(queryWrapper);
    }

    @Override
    public int insertRoleDept(SysRoleEntity role) {
        int rows = 1;
        // 新增角色与部门（数据权限）管理
        List<SysRoleDeptEntity> list = new ArrayList<SysRoleDeptEntity>();
        for (Long deptId : role.getDeptIds()) {
            SysRoleDeptEntity rd = new SysRoleDeptEntity();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (list.size() > 0) {
            saveBatch(list);
            rows = list.size();
        }
        return rows;
    }
}
