package com.seven.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.admin.bean.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * 日志表
 * @author chendongdong
 * @date 2020-12-21 15:15:48
 * @version 1.0
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLogEntity> {


}