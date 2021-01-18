package com.seven.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.admin.bean.entity.SysPostEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 岗位信息表
 * @author chendongdong
 * @date 2021-01-04 10:43:24
 * @version 1.0
 */
@Mapper
public interface SysPostMapper extends BaseMapper<SysPostEntity> {


    List<SysPostEntity> selectByUserName(String userName);

    List<Integer> selectPostIdByUserId(Long userId);
}
