package com.seven.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.admin.bean.entity.SysRoleDeptEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * 角色和部门关联表
 * @author chendongdong
 * @date 2021-01-04 10:43:24
 * @version 1.0
 */
@Mapper
public interface SysRoleDeptMapper extends BaseMapper<SysRoleDeptEntity> {


}
