package com.seven.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.admin.bean.entity.SysUserEntity;
import com.seven.admin.bean.entity.SysUserPostEntity;
import com.seven.admin.mapper.SysUserPostMapper;
import com.seven.comm.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seven.admin.service.SysUserPostService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户与岗位关联表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2021-01-04 10:43:24
 */

@Slf4j
@Service("sysUserPostService")
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPostEntity> implements SysUserPostService {
    @Autowired
    private SysUserPostMapper sysUserPostMapper;

    @Override
    public int insertUserPost(SysUserEntity user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts)) {
            // 新增用户与岗位管理
            List<SysUserPostEntity> list = new ArrayList<>();
            for (Long postId : posts) {
                SysUserPostEntity up = new SysUserPostEntity();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                saveBatch(list);
                return list.size();
            }
        }
        return 0;
    }

    @Override
    public int deleteByUserId(Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        return sysUserPostMapper.delete(queryWrapper);
    }

    @Override
    public int deleteByUserIds(Long[] userIds) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("user_id", userIds);
        return sysUserPostMapper.delete(queryWrapper);
    }

    @Override
    public int countByPostId(Long postId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("post_id", postId);
        return sysUserPostMapper.selectCount(queryWrapper);
    }
}
