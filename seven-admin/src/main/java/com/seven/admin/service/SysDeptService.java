package com.seven.admin.service;

import com.seven.admin.bean.query.DeptQuery;
import com.seven.admin.bean.vo.DeptVO;
import com.seven.comm.core.page.PageInfo;

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
}

