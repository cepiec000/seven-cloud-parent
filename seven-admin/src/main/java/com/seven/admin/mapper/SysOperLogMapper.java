package com.seven.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.admin.bean.entity.SysOperLogEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * 操作日志记录
 * @author chendongdong
 * @date 2021-01-04 10:43:24
 * @version 1.0
 */
@Mapper
public interface SysOperLogMapper extends BaseMapper<SysOperLogEntity> {


    /**
     * 清空操作日志
     */
    void clearOperLog();
}
