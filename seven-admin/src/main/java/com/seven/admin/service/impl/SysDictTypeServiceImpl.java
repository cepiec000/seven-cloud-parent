package com.seven.admin.service.impl;

import com.seven.admin.bean.entity.SysDictDataEntity;
import com.seven.admin.bean.entity.SysDictTypeEntity;
import com.seven.admin.bean.query.SysDictTypeQuery;
import com.seven.admin.constant.UserConstants;
import com.seven.admin.mapper.SysDictTypeMapper;
import com.seven.admin.service.SysDictDataService;
import com.seven.admin.utils.DictUtils;
import com.seven.comm.core.config.SevenQueryWrapper;
import com.seven.comm.core.enums.BetweenEnum;
import com.seven.comm.core.execption.SevenException;
import com.seven.comm.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seven.admin.service.SysDictTypeService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;


/**
 * 字典类型表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2021-01-04 10:43:24
 */

@Slf4j
@Service("sysDictTypeService")
public class SysDictTypeServiceImpl implements SysDictTypeService {
    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;

    @Autowired
    private SysDictDataService dictDataService;


    @Override
    public List<SysDictTypeEntity> selectDictTypeList(SysDictTypeQuery query) {
        SevenQueryWrapper<SysDictTypeEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.slike("dict_name", query.getDictName());
        queryWrapper.seq("status", query.getStatus());
        queryWrapper.slike("dict_type", query.getDictType());
        queryWrapper.dateBetween("create_time", query, BetweenEnum.ALL_CONTAIN);
        return sysDictTypeMapper.selectList(queryWrapper);
    }

    @Override
    public List<SysDictTypeEntity> selectDictTypeAll() {
        return selectDictTypeList(new SysDictTypeQuery());
    }

    @Override
    public List<SysDictDataEntity> selectDictDataByType(String dictType) {
        List<SysDictDataEntity> dictDatas = DictUtils.getDictCache(dictType);
        if (StringUtils.isNotEmpty(dictDatas)) {
            return dictDatas;
        }
        dictDatas = dictDataService.selectDictDataByType(dictType);
        if (StringUtils.isNotEmpty(dictDatas)) {
            DictUtils.setDictCache(dictType, dictDatas);
            return dictDatas;
        }
        return null;
    }

    @Override
    public SysDictTypeEntity selectDictTypeById(Long dictId) {
        return sysDictTypeMapper.selectById(dictId);
    }

    @Override
    public SysDictTypeEntity selectDictTypeByType(String dictType) {
        SevenQueryWrapper<SysDictTypeEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.seq("dict_type", dictType);
        return sysDictTypeMapper.selectOne(queryWrapper);
    }

    @Override
    public int deleteDictTypeByIds(Long[] dictIds) {
        for (Long dictId : dictIds) {
            SysDictTypeEntity dictType = selectDictTypeById(dictId);
            if (dictDataService.countDictDataByType(dictType.getDictType()) > 0) {
                throw new SevenException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
            }
        }
        int count = sysDictTypeMapper.deleteBatchIds(Arrays.asList(dictIds));
        if (count > 0) {
            DictUtils.clearDictCache();
        }
        return count;
    }

    @Override
    public void clearCache() {
        DictUtils.clearDictCache();
    }

    @Override
    public int insertDictType(SysDictTypeEntity dictType) {
        int rows = sysDictTypeMapper.insert(dictType);
        if (rows > 0) {
            DictUtils.clearDictCache();
        }
        return 0;
    }

    @Override
    public int updateDictType(SysDictTypeEntity dictType) {
        SysDictTypeEntity oldDict = selectDictTypeById(dictType.getDictId());
        dictDataService.updateDictDataType(oldDict.getDictType(), dictType.getDictType());
        int row = sysDictTypeMapper.updateById(dictType);
        if (row > 0) {
            DictUtils.clearDictCache();
        }
        return row;
    }

    @Override
    public String checkDictTypeUnique(SysDictTypeEntity dictType) {
        Long dictId = StringUtils.isNull(dictType.getDictId()) ? -1L : dictType.getDictId();
        SysDictTypeEntity dict = selectDictTypeByType(dictType.getDictType());
        if (StringUtils.isNotNull(dictType) && dict.getDictId().longValue() != dictId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
