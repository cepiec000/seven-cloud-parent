package com.seven.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.seven.admin.bean.entity.SysDictDataEntity;
import com.seven.admin.bean.query.SysDictDataQuery;
import com.seven.admin.mapper.SysDictDataMapper;
import com.seven.admin.service.SysDictDataService;
import com.seven.admin.utils.DictUtils;
import com.seven.comm.core.config.SevenQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 字典数据表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2021-01-04 10:43:24
 */

@Slf4j
@Service("sysDictDataService")
public class SysDictDataServiceImpl implements SysDictDataService {
    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Override
    public List<SysDictDataEntity> selectDictDataByType(String dictType) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("status", "0");
        queryWrapper.eq("dict_type", dictType);
        queryWrapper.orderByAsc("dict_sort");
        return sysDictDataMapper.selectList(queryWrapper);
    }

    @Override
    public int countDictDataByType(String dictType) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("dict_type", dictType);
        return sysDictDataMapper.selectCount(queryWrapper);
    }

    @Override
    public int updateDictDataType(String oldType, String newType) {
        UpdateWrapper<SysDictDataEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("dict_type", newType);
        updateWrapper.eq("dict_type", oldType);
        return sysDictDataMapper.update(null, updateWrapper);
    }

    @Override
    public List<SysDictDataEntity> selectDictDataList(SysDictDataQuery dictData) {
        SevenQueryWrapper<SysDictDataEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.seq("dict_type", dictData.getDictType());
        queryWrapper.slike("dict_label", dictData.getDictLabel());
        queryWrapper.seq("status", dictData.getStatus());
        return sysDictDataMapper.selectList(queryWrapper);
    }

    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        SevenQueryWrapper<SysDictDataEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.seq("dict_type", dictType);
        queryWrapper.slike("dict_value", dictValue);
        SysDictDataEntity dataEntity = sysDictDataMapper.selectOne(queryWrapper);
        if (dataEntity != null) {
            return dataEntity.getDictLabel();
        }
        return null;
    }

    @Override
    public SysDictDataEntity selectDictDataById(Long dictCode) {
        SevenQueryWrapper<SysDictDataEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.seq("dict_code", dictCode);
        return sysDictDataMapper.selectOne(queryWrapper);
    }

    @Override
    public int deleteDictDataByIds(Long[] dictCodes) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("dict_code", dictCodes);
        int row = sysDictDataMapper.delete(queryWrapper);
        if (row > 0) {
            DictUtils.clearDictCache();
        }
        return 0;
    }

    @Override
    public int insertDictData(SysDictDataEntity dictData) {
        int row = sysDictDataMapper.insert(dictData);
        if (row > 0) {
            DictUtils.clearDictCache();
        }
        return 0;
    }

    @Override
    public int updateDictData(SysDictDataEntity dictData) {
        int row = sysDictDataMapper.updateById(dictData);
        if (row > 0) {
            DictUtils.clearDictCache();
        }
        return 0;
    }
}
