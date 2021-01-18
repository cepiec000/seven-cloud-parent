package com.seven.admin.service.impl;

import com.seven.admin.bean.entity.SysLogininforEntity;
import com.seven.admin.bean.query.SysLogininforQuery;
import com.seven.admin.mapper.SysLogininforMapper;
import com.seven.comm.core.config.SevenQueryWrapper;
import com.seven.comm.core.enums.BetweenEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seven.admin.service.SysLogininforService;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;


/**
 * 系统访问记录
 *
 * @author chendongdong
 * @version 1.0
 * @date 2021-01-04 10:43:24
 */

@Slf4j
@Service("sysLogininforService")
public class SysLogininforServiceImpl implements SysLogininforService {
    @Autowired
    private SysLogininforMapper sysLogininforMapper;

    @Override
    public void insertLogininfor(SysLogininforEntity logininfor) {
        sysLogininforMapper.insert(logininfor);
    }

    @Override
    public List<SysLogininforEntity> selectLogininforList(SysLogininforQuery logininfor) {
        SevenQueryWrapper<SysLogininforEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.slike("ipaddr", logininfor.getIpaddr());
        queryWrapper.seq("status", logininfor.getStatus());
        queryWrapper.slike("user_name", logininfor.getUserName());
        queryWrapper.dateBetween("login_time", logininfor.getBeginTime(), logininfor.getEndTime(), BetweenEnum.ALL_CONTAIN);
        return sysLogininforMapper.selectList(queryWrapper);
    }

    @Override
    public int deleteLogininforByIds(Long[] infoIds) {
        return sysLogininforMapper.deleteBatchIds(Arrays.asList(infoIds));
    }

    @Override
    public void cleanLogininfor() {
        sysLogininforMapper.cleanLogininfor();
    }
}
