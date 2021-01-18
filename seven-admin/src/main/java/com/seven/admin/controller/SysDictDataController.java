package com.seven.admin.controller;

import com.seven.admin.annotation.Log;
import com.seven.admin.bean.entity.SysDictDataEntity;
import com.seven.admin.bean.query.SysDictDataQuery;
import com.seven.admin.service.SysDictDataService;
import com.seven.admin.service.SysDictTypeService;
import com.seven.admin.utils.SecurityUtils;
import com.seven.comm.core.enums.BusinessType;
import com.seven.comm.core.response.ApiResponse;
import com.seven.comm.core.utils.StringUtils;
import com.seven.mybatis.pagehelper.PageTools;
import com.seven.mybatis.pagehelper.bean.PageInfo;
import com.seven.mybatis.pagehelper.bean.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {
    @Autowired
    private SysDictDataService dictDataService;

    @Autowired
    private SysDictTypeService dictTypeService;

    @PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping("/list")
    public ApiResponse<PageInfo<SysDictDataEntity>> list(SysDictDataQuery dictData) {
        PageTools.startPage(dictData.getPageNo(), dictData.getSize());
        List<SysDictDataEntity> list = dictDataService.selectDictDataList(dictData);
        return ApiResponse.success(new PageResult<>(list).toPageInfo());
    }


    /**
     * 查询字典数据详细
     */
    @PreAuthorize("@ss.hasPermi('system:dict:query')")
    @GetMapping(value = "/{dictCode}")
    public ApiResponse getInfo(@PathVariable Long dictCode) {
        return ApiResponse.success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public ApiResponse dictType(@PathVariable String dictType) {
        List<SysDictDataEntity> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data)) {
            data = new ArrayList<SysDictDataEntity>();
        }
        return ApiResponse.success(data);
    }

    /**
     * 新增字典类型
     */
    @PreAuthorize("@ss.hasPermi('system:dict:add')")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    public ApiResponse add(@Validated @RequestBody SysDictDataEntity dict) {
        dict.setCreateBy(SecurityUtils.getUsername());
        return toAjax(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */
    @PreAuthorize("@ss.hasPermi('system:dict:edit')")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public ApiResponse edit(@Validated @RequestBody SysDictDataEntity dict) {
        dict.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictCodes}")
    public ApiResponse remove(@PathVariable Long[] dictCodes) {
        return toAjax(dictDataService.deleteDictDataByIds(dictCodes));
    }
}
