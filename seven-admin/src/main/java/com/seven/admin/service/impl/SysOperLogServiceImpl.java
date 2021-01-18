package com.seven.admin.service.impl;

import com.seven.admin.bean.entity.SysOperLogEntity;
import com.seven.admin.bean.query.SysOperLogQuery;
import com.seven.admin.mapper.SysOperLogMapper;
import com.seven.comm.core.config.SevenQueryWrapper;
import com.seven.comm.core.enums.BetweenEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seven.admin.service.SysOperLogService;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;


/**
 * 操作日志记录
 *
 * @author chendongdong
 * @version 1.0
 * @date 2021-01-04 10:43:24
 */

@Slf4j
@Service("sysOperLogService")
public class SysOperLogServiceImpl implements SysOperLogService {
    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    @Override
    public void insertOperlog(SysOperLogEntity operLog) {
        sysOperLogMapper.insert(operLog);
    }

    @Override
    public List<SysOperLogEntity> selectOperLogList(SysOperLogQuery operLog) {
        SevenQueryWrapper<SysOperLogEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.slike("title", operLog.getTitle());
        queryWrapper.seq("business_type", operLog.getBusinessType());
        queryWrapper.sin("business_type", Arrays.asList(operLog.getBusinessTypes()));
        queryWrapper.seq("status", operLog.getStatus());
        queryWrapper.slike("operName", operLog.getOperName());
        queryWrapper.dateBetween("oper_time", operLog.getBeginTime(), operLog.getEndTime(), BetweenEnum.ALL_CONTAIN);
        return sysOperLogMapper.selectList(queryWrapper);
    }

    @Override
    public int deleteOperLogByIds(Long[] operIds) {
        return sysOperLogMapper.deleteBatchIds(Arrays.asList(operIds));
    }

    @Override
    public SysOperLogEntity selectOperLogById(Long operId) {
        return sysOperLogMapper.selectById(operId);
    }

    @Override
    public void cleanOperLog() {
        sysOperLogMapper.clearOperLog();
    }
}
