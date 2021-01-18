package com.seven.admin.controller;

import com.seven.admin.annotation.Log;
import com.seven.admin.bean.entity.SysDictTypeEntity;
import com.seven.admin.bean.query.SysDictTypeQuery;
import com.seven.admin.constant.UserConstants;
import com.seven.admin.service.SysDictTypeService;
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
 * 数据字典信息
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController
{
    @Autowired
    private SysDictTypeService dictTypeService;

    @PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping("/list")
    public ApiResponse<PageInfo<SysDictTypeEntity>> list(SysDictTypeQuery dictType)
    {
        PageTools.startPage(dictType.getPageNo(),dictType.getSize());
        List<SysDictTypeEntity> list = dictTypeService.selectDictTypeList(dictType);
        return ApiResponse.success(new PageResult<>(list).toPageInfo());
    }


    /**
     * 查询字典类型详细
     */
    @PreAuthorize("@ss.hasPermi('system:dict:query')")
    @GetMapping(value = "/{dictId}")
    public ApiResponse getInfo(@PathVariable Long dictId)
    {
        return ApiResponse.success(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @PreAuthorize("@ss.hasPermi('system:dict:add')")
    @Log(title = "字典类型", businessType = BusinessType.INSERT)
    @PostMapping
    public ApiResponse add(@Validated @RequestBody SysDictTypeEntity dict)
    {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict)))
        {
            return ApiResponse.failed("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setCreateBy(SecurityUtils.getUsername());
        return toAjax(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     */
    @PreAuthorize("@ss.hasPermi('system:dict:edit')")
    @Log(title = "字典类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public ApiResponse edit(@Validated @RequestBody SysDictTypeEntity dict)
    {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict)))
        {
            return ApiResponse.failed("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictIds}")
    public ApiResponse remove(@PathVariable Long[] dictIds)
    {
        return toAjax(dictTypeService.deleteDictTypeByIds(dictIds));
    }

    /**
     * 清空缓存
     */
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典类型", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clearCache")
    public ApiResponse clearCache()
    {
        dictTypeService.clearCache();
        return ApiResponse.success();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    public ApiResponse optionselect()
    {
        List<SysDictTypeEntity> dictTypes = dictTypeService.selectDictTypeAll();
        return ApiResponse.success(dictTypes);
    }
}
