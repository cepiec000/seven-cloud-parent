package com.seven.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.admin.bean.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户和角色关联表
 * @author chendongdong
 * @date 2021-01-04 10:43:24
 * @version 1.0
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleEntity> {


}
