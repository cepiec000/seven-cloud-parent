package com.seven.admin.controller;

import com.seven.admin.bean.dto.AddDeptDTO;
import com.seven.admin.bean.dto.AddPostDTO;
import com.seven.admin.bean.dto.EditDeptDTO;
import com.seven.admin.bean.dto.EditPostDTO;
import com.seven.admin.bean.query.DeptQuery;
import com.seven.admin.bean.vo.DeptVO;
import com.seven.admin.service.SysDeptService;
import com.seven.comm.core.page.PageInfo;
import com.seven.comm.core.response.ApiResponse;
import com.seven.comm.core.utils.Convert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("add")
    @RequiresPermissions("system:dept:add")
    @ApiOperation(value = "添加部门")
    public ApiResponse<Boolean> add(@Validated AddDeptDTO dept) {
        Boolean rest = sysDeptService.addDept(dept);
        return ApiResponse.success(rest);
    }

    @PostMapping("update")
    @RequiresPermissions("system:dept:edit")
    @ApiOperation(value = "修改部门")
    public ApiResponse<Boolean> update(@Validated EditDeptDTO dept) {
        Boolean rest = sysDeptService.updateDept(dept);
        return ApiResponse.success(rest);
    }

    @GetMapping("delete")
    @RequiresPermissions("system:dept:delete")
    @ApiOperation(value = "删除部门")
    public ApiResponse<Boolean> delete(@RequestParam("deptId") Integer deptId) {
        Boolean rest = sysDeptService.delete(deptId);
        return ApiResponse.success(rest);
    }
}
