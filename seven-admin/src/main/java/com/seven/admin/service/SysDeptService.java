package com.seven.admin.service;

import com.seven.admin.bean.dto.AddDeptDTO;
import com.seven.admin.bean.dto.EditDeptDTO;
import com.seven.admin.bean.entity.SysDeptEntity;
import com.seven.admin.bean.query.DeptQuery;
import com.seven.admin.bean.vo.DeptVO;
import com.seven.comm.core.page.PageInfo;

import java.util.List;

/**
 * 部门管理
 *
 * @author chendongdong
 * @date 2020-12-21 15:15:48
 * @version 1.0
 */
public interface SysDeptService {

    /**
     * 获取部门列表
     * @param deptQuery
     * @return
     */
    PageInfo<DeptVO> queryByPage(DeptQuery deptQuery);


    /**
     * 删除部门
     * @param deptId
     * @return
     */
    Boolean delete(Integer deptId);

    /**
     * 添加部门
     * @param dept
     * @return
     */
    Boolean addDept(AddDeptDTO dept);

    /**
     * 修改部门
     * @param dept
     * @return
     */
    Boolean updateDept(EditDeptDTO dept);

    /**
     * 根据部门名称 查询部门
     * @param deptName
     * @return
     */
    SysDeptEntity checkDeptNameUnique(String deptName);


    /**
     * 根据ID 获取部门
     * @param deptId
     * @return
     */
    SysDeptEntity get(Integer deptId);

    /**
     * 获取子级部门
     * @param deptId
     * @return
     */
    List<SysDeptEntity> queryChildrenByParentId(Integer deptId);
}

