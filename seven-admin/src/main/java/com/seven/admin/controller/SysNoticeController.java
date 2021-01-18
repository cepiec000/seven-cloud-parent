package com.seven.admin.controller;

import com.seven.admin.annotation.Log;
import com.seven.admin.bean.entity.SysNoticeEntity;
import com.seven.admin.bean.query.SysNoticeQuery;
import com.seven.admin.service.SysNoticeService;
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
 * 公告 信息操作处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {
    @Autowired
    private SysNoticeService noticeService;

    /**
     * 获取通知公告列表
     */
    @PreAuthorize("@ss.hasPermi('system:notice:list')")
    @GetMapping("/list")
    public ApiResponse<PageInfo<SysNoticeEntity>> list(SysNoticeQuery notice) {
        PageTools.startPage(notice.getPageNo(), notice.getSize());
        List<SysNoticeEntity> list = noticeService.selectNoticeList(notice);
        return ApiResponse.success(new PageResult<>(list).toPageInfo());
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @GetMapping(value = "/{noticeId}")
    public ApiResponse getInfo(@PathVariable Long noticeId) {
        return ApiResponse.success(noticeService.selectNoticeById(noticeId));
    }

    /**
     * 新增通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    public ApiResponse add(@Validated @RequestBody SysNoticeEntity notice) {
        notice.setCreateBy(SecurityUtils.getUsername());
        return toAjax(noticeService.insertNotice(notice));
    }

    /**
     * 修改通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public ApiResponse edit(@Validated @RequestBody SysNoticeEntity notice) {
        notice.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(noticeService.updateNotice(notice));
    }

    /**
     * 删除通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public ApiResponse remove(@PathVariable Long[] noticeIds) {
        return toAjax(noticeService.deleteNoticeByIds(noticeIds));
    }
}
