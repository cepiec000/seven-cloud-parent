package com.seven.admin.controller;

import com.seven.admin.annotation.Log;
import com.seven.admin.bean.entity.SysPostEntity;
import com.seven.admin.bean.query.SysPostQuery;
import com.seven.admin.constant.UserConstants;
import com.seven.admin.service.SysPostService;
import com.seven.admin.utils.SecurityUtils;
import com.seven.comm.core.enums.BusinessType;
import com.seven.comm.core.response.ApiResponse;
import com.seven.mybatis.pagehelper.PageTools;
import com.seven.mybatis.pagehelper.bean.PageInfo;
import com.seven.mybatis.pagehelper.bean.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 岗位信息操作处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController {
    @Autowired
    private SysPostService postService;

    /**
     * 获取岗位列表
     */
    @PreAuthorize("@ss.hasPermi('system:post:list')")
    @GetMapping("/list")
    public ApiResponse<PageInfo<SysPostEntity>> list(SysPostQuery post) {
        PageTools.startPage(post.getPageNo(), post.getSize());
        List<SysPostEntity> list = postService.selectPostList(post);
        return ApiResponse.success(new PageResult<>(list).toPageInfo());
    }


    /**
     * 根据岗位编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:post:query')")
    @GetMapping(value = "/{postId}")
    public ApiResponse getInfo(@PathVariable Long postId) {
        return ApiResponse.success(postService.selectPostById(postId));
    }

    /**
     * 新增岗位
     */
    @PreAuthorize("@ss.hasPermi('system:post:add')")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping
    public ApiResponse add(@Validated @RequestBody SysPostEntity post) {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
            return ApiResponse.failed("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return ApiResponse.failed("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setCreateBy(SecurityUtils.getUsername());
        return toAjax(postService.insertPost(post));
    }

    /**
     * 修改岗位
     */
    @PreAuthorize("@ss.hasPermi('system:post:edit')")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public ApiResponse edit(@Validated @RequestBody SysPostEntity post) {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
            return ApiResponse.failed("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return ApiResponse.failed("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(postService.updatePost(post));
    }

    /**
     * 删除岗位
     */
    @PreAuthorize("@ss.hasPermi('system:post:remove')")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}")
    public ApiResponse remove(@PathVariable Long[] postIds) {
        return toAjax(postService.deletePostByIds(postIds));
    }

    /**
     * 获取岗位选择框列表
     */
    @GetMapping("/optionselect")
    public ApiResponse optionselect() {
        List<SysPostEntity> posts = postService.selectPostAll();
        return ApiResponse.success(posts);
    }
}
