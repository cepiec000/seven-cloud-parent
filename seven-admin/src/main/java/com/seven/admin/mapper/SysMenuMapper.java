package com.seven.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.admin.bean.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 菜单权限表
 * @author chendongdong
 * @date 2021-01-04 10:43:24
 * @version 1.0
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {


    /**
     * 查询用户管理菜单权限
     * @param userId
     * @return
     */
    List<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据用户ID 查询菜单权限
     * @param menu
     * @param userId
     * @return
     */
    List<SysMenuEntity> selectMenuListByUserId(@Param("menu") SysMenuEntity menu, @Param("userId") Long userId);

    /**
     * 查询所有菜单
     * @return
     */
    List<SysMenuEntity> selectMenuTreeAll();

    /**
     * 根据用户ID 查询菜单
     * @param userId
     * @return
     */
    List<SysMenuEntity> selectMenuTreeByUserId(Long userId);

    /**
     * 根据角色 查询菜单
     * @param roleId
     * @param menuCheckStrictly
     * @return
     */
    List<Integer> selectMenuListByRoleId(@Param("roleId") Long roleId, @Param("menuCheckStrictly") boolean menuCheckStrictly);
}
