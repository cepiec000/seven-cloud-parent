package com.seven.admin.service;

import com.seven.admin.bean.entity.SysDictDataEntity;
import com.seven.admin.bean.query.SysDictDataQuery;

import java.util.List;

/**
 * 字典数据表
 *
 * @author chendongdong
 * @date 2021-01-04 10:43:24
 * @version 1.0
 */
public interface SysDictDataService {

    /**
     * 根据 type查询字典数据
     * @param dictType
     * @return
     */
    List<SysDictDataEntity> selectDictDataByType(String dictType);

    /**
     * 统计 type下字典
     * @param dictType
     * @return
     */
    int countDictDataByType(String dictType);

    int updateDictDataType(String dictType, String dictType1);

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    public List<SysDictDataEntity> selectDictDataList(SysDictDataQuery dictData);

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    public String selectDictLabel(String dictType, String dictValue);

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    public SysDictDataEntity selectDictDataById(Long dictCode);

    /**
     * 批量删除字典数据信息
     *
     * @param dictCodes 需要删除的字典数据ID
     * @return 结果
     */
    public int deleteDictDataByIds(Long[] dictCodes);

    /**
     * 新增保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    public int insertDictData(SysDictDataEntity dictData);

    /**
     * 修改保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    public int updateDictData(SysDictDataEntity dictData);
}

