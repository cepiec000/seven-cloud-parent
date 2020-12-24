package com.seven.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.admin.bean.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 菜单权限表
 * @author chendongdong
 * @date 2020-12-21 15:15:48
 * @version 1.0
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {


    /**
     * 根据用户ID 获取 用户关联角色下 所有菜单
     * @param userId
     * @return
     */
    List<SysMenuEntity> selectMenusByUserId(@Param("userId") Integer userId);
}
