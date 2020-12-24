package com.seven.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.admin.bean.entity.SysRoleEntity;
import com.seven.admin.bean.query.RoleQuery;
import com.seven.comm.core.config.SevenBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 系统角色表
 * @author chendongdong
 * @date 2020-12-21 15:15:48
 * @version 1.0
 */
@Mapper
public interface SysRoleMapper extends SevenBaseMapper<SysRoleEntity> {


}
