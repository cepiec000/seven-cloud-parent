package com.seven.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.admin.bean.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 角色信息表
 * @author chendongdong
 * @date 2021-01-04 10:43:24
 * @version 1.0
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {


    /**
     * 查询用户橘色
     * @param userId
     * @return
     */
    List<SysRoleEntity> selectRolePermissionByUserId(Long userId);

    /**
     * 根据用户名查询
     * @param userName
     * @return
     */
    List<SysRoleEntity> selectByUserName(String userName);

    /**
     * 根据用户ID 查询 角色ID
     * @param userId
     * @return
     */
    List<Integer> selectRoleIdByUserId(Long userId);
}
