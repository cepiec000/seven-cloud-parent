package com.seven.admin.controller;

import com.seven.admin.bean.dto.AddPostDTO;
import com.seven.admin.bean.dto.AddUserDTO;
import com.seven.admin.bean.dto.EditPostDTO;
import com.seven.admin.bean.dto.EditUserDTO;
import com.seven.admin.bean.query.PostQuery;
import com.seven.admin.bean.query.UserQuery;
import com.seven.admin.bean.vo.PostVO;
import com.seven.admin.bean.vo.UserVO;
import com.seven.admin.service.SysPostService;
import com.seven.admin.service.SysUserPostService;
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
 * @date 2020/12/24 14:54
 */
@Api(tags = "岗位管理")
@RestController
@RequestMapping("/system/post")
public class SysPostController {
    @Autowired
    private SysPostService postService;


    @RequiresPermissions("system:post:list")
    @PostMapping("/list")
    @ApiOperation(value = "用户列表")
    public ApiResponse<PageInfo<PostVO>> list(@RequestBody PostQuery post) {
        PageInfo<PostVO> pageInfo = postService.queryVoList(post);
        return ApiResponse.success(pageInfo);
    }

    @PostMapping("add")
    @RequiresPermissions("system:post:add")
    @ApiOperation(value = "添加岗位")
    public ApiResponse<Boolean> add(@Validated AddPostDTO post) {
        Boolean rest = postService.addPost(post);
        return ApiResponse.success(rest);
    }

    @PostMapping("update")
    @RequiresPermissions("system:post:edit")
    @ApiOperation(value = "修改岗位")
    public ApiResponse<Boolean> update(@Validated EditPostDTO post) {
        Boolean rest = postService.updatePost(post);
        return ApiResponse.success(rest);
    }

    @GetMapping("delete")
    @RequiresPermissions("system:post:delete")
    @ApiOperation(value = "删除岗位")
    public ApiResponse<Boolean> delete(@RequestParam("ids") String ids) {
        Integer[] userIds = Convert.toIntArray(ids);
        Boolean rest = postService.delete(userIds);
        return ApiResponse.success(rest);
    }
}
