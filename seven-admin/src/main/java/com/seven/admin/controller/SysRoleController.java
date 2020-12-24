package com.seven.admin.controller;

import com.seven.admin.bean.dto.AddRoleDTO;
import com.seven.admin.bean.dto.EditRoleDTO;
import com.seven.admin.bean.dto.RoleUserDTO;
import com.seven.admin.bean.dto.UserRoleOnlyDTO;
import com.seven.admin.bean.query.RoleQuery;
import com.seven.admin.bean.vo.RoleVO;
import com.seven.admin.bean.vo.UserVO;
import com.seven.admin.service.SysRoleService;
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
 * @date 2020/12/23 14:38
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/system/role")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @RequiresPermissions("system:role:add")
    @PostMapping("/add")
    @ApiOperation(value = "新增角色")
    public ApiResponse<Boolean> addRole(@Validated AddRoleDTO roleDTO) {
        Boolean rest = roleService.addRule(roleDTO);
        return ApiResponse.success(rest);
    }


    @RequiresPermissions("system:role:edit")
    @PostMapping("/update")
    @ApiOperation(value = "修改角色")
    public ApiResponse<Boolean> update(@Validated EditRoleDTO roleDTO) {
        Boolean rest = roleService.updateRole(roleDTO);
        return ApiResponse.success(rest);
    }


    @RequiresPermissions("system:role:remove")
    @GetMapping("/remove")
    @ApiOperation(value = "刪除角色")
    public ApiResponse<Boolean> delete(@RequestParam("ids") String ids) {
        Integer[] roleIds = Convert.toIntArray(ids);
        Boolean rest = roleService.delete(roleIds);
        return ApiResponse.success(rest);
    }

    @RequiresPermissions("system:role:list")
    @PostMapping("/list")
    @ApiOperation(value = "角色列表")
    @ResponseBody
    public ApiResponse<PageInfo<RoleVO>> list(@RequestBody RoleQuery role) {
        PageInfo<RoleVO> pageInfo = roleService.queryRoleList(role);
        return ApiResponse.success(pageInfo);
    }

    @RequiresPermissions("system:role:list")
    @PostMapping("/authUser/authRoleAll")
    @ApiOperation(value = "批量用户授权")
    public ApiResponse<Boolean> authUserAll(@RequestBody RoleUserDTO roleUserDTO) {
        Boolean rest = roleService.authUserAll(roleUserDTO);
        return ApiResponse.success(rest);
    }

    @ApiOperation(value = "取消用户授权角色")
    @PostMapping("/authUser/cancel")
    @ResponseBody
    public ApiResponse<Boolean> cancelAuthUser(@RequestBody UserRoleOnlyDTO userRoleOnly){
        Boolean rest = roleService.cancelAuthUser(userRoleOnly);
        return ApiResponse.success(rest);
    }


}
