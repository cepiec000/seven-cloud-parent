package com.seven.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.admin.bean.entity.SysUserEntity;
import com.seven.admin.bean.query.SysUserQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 用户信息表
 * @author chendongdong
 * @date 2021-01-04 10:43:24
 * @version 1.0
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUserEntity> {


    List<SysUserEntity> selectUserList(SysUserQuery user);

}
