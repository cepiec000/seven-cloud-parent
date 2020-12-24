package com.seven.admin.service.impl;

import com.seven.admin.bean.entity.SysMenuEntity;
import com.seven.admin.mapper.SysMenuMapper;
import com.seven.admin.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


/**
 * 菜单权限表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2020-12-21 15:15:48
 */

@Slf4j
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Override
    public List<SysMenuEntity> queryMenusByUserId(Integer userId) {
        if (userId == null || userId.equals(0)) {
            return new ArrayList<>();
        }
        return menuMapper.selectMenusByUserId(userId);
    }
}
