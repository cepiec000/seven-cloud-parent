package com.seven.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.admin.bean.entity.SysUserPostEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户与岗位关联表
 * @author chendongdong
 * @date 2021-01-04 10:43:24
 * @version 1.0
 */
@Mapper
public interface SysUserPostMapper extends BaseMapper<SysUserPostEntity> {


}
