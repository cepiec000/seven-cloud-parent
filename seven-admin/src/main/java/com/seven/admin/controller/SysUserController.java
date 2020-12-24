package com.seven.admin.controller;

import com.seven.admin.bean.dto.AddUserDTO;
import com.seven.admin.bean.dto.EditUserDTO;
import com.seven.admin.bean.dto.UserRoleDTO;
import com.seven.admin.bean.query.UserQuery;
import com.seven.admin.bean.vo.UserVO;
import com.seven.admin.enums.UserLockEnum;
import com.seven.admin.service.SysUserService;
import com.seven.comm.core.page.PageInfo;
import com.seven.comm.core.response.ApiResponse;
import com.seven.comm.core.response.StatCode;
import com.seven.comm.core.utils.Convert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/user")
@Api(tags = "用户管理")
public class SysUserController {
    @Autowired
    private SysUserService userService;

    @RequiresPermissions("system:user:list")
    @PostMapping("/list")
    @ApiOperation(value = "用户列表")
    public ApiResponse<PageInfo<UserVO>> list(@RequestBody UserQuery user) {
        PageInfo<UserVO> pageInfo = userService.queryUserVoList(user);
        return ApiResponse.success(pageInfo);
    }

    @PostMapping("add")
    @RequiresPermissions("system:user:add")
    @ApiOperation(value = "添加用户")
    public ApiResponse<Boolean> add(@Validated AddUserDTO user) {
        Boolean rest = userService.addUser(user);
        return ApiResponse.success(rest);
    }

    @PostMapping("update")
    @RequiresPermissions("system:user:edit")
    @ApiOperation(value = "修改用户")
    public ApiResponse<Boolean> update(@Validated EditUserDTO user) {
        Boolean rest = userService.updateUser(user);
        return ApiResponse.success(rest);
    }

    @GetMapping("resetPwd")
    @RequiresPermissions("system:user:resetPwd")
    @ApiOperation(value = "重置密码")
    public ApiResponse<Boolean> resetPwd(@RequestParam("userId") Integer userId) {
        Boolean rest = userService.resetPwd(userId);
        return ApiResponse.success(rest);
    }

    @PostMapping("/authRole/setAuthRole")
    @RequiresPermissions("system:user:setRule")
    @ApiOperation(value = "设置用户角色")
    public ApiResponse<Boolean> setAuthRole(@RequestBody UserRoleDTO roleDTO) {
        Boolean rest = userService.setAuthRole(roleDTO);
        return ApiResponse.success(rest);
    }

    @GetMapping("delete")
    @RequiresPermissions("system:user:delete")
    @ApiOperation(value = "删除用户")
    public ApiResponse<Boolean> delete(@RequestParam("ids") String ids) {
        Integer[] userIds = Convert.toIntArray(ids);
        Boolean rest = userService.delete(userIds);
        return ApiResponse.success(rest);
    }

    @GetMapping("changeLockFlag")
    @RequiresPermissions("system:user:edit")
    @ApiOperation(value = "修改用户状态")
    public ApiResponse<Boolean> changeLockFlag(@RequestParam("userId") Integer userId, @RequestParam("lockFlag") Integer lockFlag) {
        if (!lockFlag.equals(UserLockEnum.UN_LOCK.getCode()) && !lockFlag.equals(UserLockEnum.LOCKED.getCode())) {
            return ApiResponse.failed(StatCode.PARAMETER_ERROR);
        }
        Boolean rest = userService.changeLockFlag(userId, lockFlag);
        return ApiResponse.success(rest);
    }

    @RequiresPermissions("system:role:list")
    @PostMapping("/authUser/unassignedList")
    @ApiOperation(value = "未分配角色的用户列表")
    @ResponseBody
    public ApiResponse<PageInfo<UserVO>> unassignedList(@RequestBody UserQuery user) {
        PageInfo<UserVO> pageInfo = userService.unassignedRoleList(user);
        return ApiResponse.success(pageInfo);
    }
}
