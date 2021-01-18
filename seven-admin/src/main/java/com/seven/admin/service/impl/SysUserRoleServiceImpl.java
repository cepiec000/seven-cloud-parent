package com.seven.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.admin.bean.entity.SysUserEntity;
import com.seven.admin.bean.entity.SysUserRoleEntity;
import com.seven.admin.mapper.SysUserRoleMapper;
import com.seven.comm.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seven.admin.service.SysUserRoleService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户和角色关联表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2021-01-04 10:43:24
 */

@Slf4j
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements SysUserRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public int insertUserRole(SysUserEntity user) {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<SysUserRoleEntity> list = new ArrayList<>();
            for (Long roleId : roles) {
                SysUserRoleEntity ur = new SysUserRoleEntity();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                saveBatch(list);
                return list.size();
            }
        }
        return 0;
    }

    @Override
    public int deleteByUserId(Long userId) {
        QueryWrapper queryWrapper= new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        return sysUserRoleMapper.delete(queryWrapper);
    }

    @Override
    public int deleteByUserIds(Long[] userIds) {
        QueryWrapper queryWrapper= new QueryWrapper();
        queryWrapper.in("user_id",userIds);
        return sysUserRoleMapper.delete(queryWrapper);
    }

    @Override
    public int countByRoleId(Long roleId) {
        QueryWrapper queryWrapper= new QueryWrapper();
        queryWrapper.in("role_id",roleId);
        return sysUserRoleMapper.selectCount(queryWrapper);
    }
}
