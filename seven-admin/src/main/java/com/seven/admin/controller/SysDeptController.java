package com.seven.admin.controller;

import com.seven.admin.bean.query.DeptQuery;
import com.seven.admin.bean.vo.DeptVO;
import com.seven.admin.service.SysDeptService;
import com.seven.comm.core.page.PageInfo;
import com.seven.comm.core.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/23 18:13
 */
@Api(tags = "部门管理")
@RestController
@RequestMapping("/system/dept")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    @PostMapping("/list")
    @ApiOperation(value = "部门列表")
    public ApiResponse<PageInfo<DeptVO>> list(@RequestBody DeptQuery deptQuery) {

        PageInfo<DeptVO> pageInfo = sysDeptService.queryByPage(deptQuery);
        return ApiResponse.success(pageInfo);
    }
}
