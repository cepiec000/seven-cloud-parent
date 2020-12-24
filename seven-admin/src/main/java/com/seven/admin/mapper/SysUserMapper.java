package com.seven.admin.mapper;

import com.seven.admin.bean.entity.SysUserEntity;
import com.seven.admin.bean.query.UserQuery;
import com.seven.comm.core.config.SevenBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2020-12-21 15:15:48
 */
@Mapper
public interface SysUserMapper extends SevenBaseMapper<SysUserEntity> {

    /**
     * 分页查询 未绑定 用户的角色
     *
     * @param user
     * @return
     */
    List<SysUserEntity> unassignedRoleList(@Param("user") UserQuery user, @Param("start") int start, @Param("end") int end);

    /**
     * 查询未绑定用户角色 总条数
     *
     * @param user
     * @return
     */
    int countUnassignedRole(@Param("user") UserQuery user);
}
