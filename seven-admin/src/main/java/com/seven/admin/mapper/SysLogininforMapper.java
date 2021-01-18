package com.seven.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.admin.bean.entity.SysLogininforEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * 系统访问记录
 * @author chendongdong
 * @date 2021-01-04 10:43:24
 * @version 1.0
 */
@Mapper
public interface SysLogininforMapper extends BaseMapper<SysLogininforEntity> {


    /**
     * 清空登录日志
     */
    void cleanLogininfor();
}
