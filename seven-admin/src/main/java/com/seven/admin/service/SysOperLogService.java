package com.seven.admin.service;

import com.seven.admin.bean.entity.SysOperLogEntity;
import com.seven.admin.bean.query.SysOperLogQuery;

import java.util.List;

/**
 * 操作日志记录
 *
 * @author chendongdong
 * @date 2021-01-04 10:43:24
 * @version 1.0
 */
public interface SysOperLogService {
    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    public void insertOperlog(SysOperLogEntity operLog);

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    public List<SysOperLogEntity> selectOperLogList(SysOperLogQuery operLog);

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    public int deleteOperLogByIds(Long[] operIds);

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    public SysOperLogEntity selectOperLogById(Long operId);

    /**
     * 清空操作日志
     */
    public void cleanOperLog();
}

