package com.seven.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.admin.bean.entity.SysDeptEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 部门表
 * @author chendongdong
 * @date 2021-01-04 10:43:24
 * @version 1.0
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDeptEntity> {


    /**
     * 根据角色ID 查询部门树信息
     * @param roleId
     * @param deptCheckStrictly
     * @return
     */
    List<Integer> selectDeptListByRoleId(@Param("roleId") Long roleId, @Param("deptCheckStrictly") boolean deptCheckStrictly);

    /**
     * 修改所在部门的父级部门状态
     *
     * @param dept 部门
     */
    void updateDeptStatus(SysDeptEntity dept);
    /**
     * 根据ID查询所有子部门
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    List<SysDeptEntity> selectChildrenDeptById(Long deptId);
    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    void updateDeptChildren(@Param("depts") List<SysDeptEntity> depts);
}
