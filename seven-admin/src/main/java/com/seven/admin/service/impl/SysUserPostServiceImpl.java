package com.seven.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.admin.bean.entity.SysUserPostEntity;
import com.seven.admin.mapper.SysUserPostMapper;
import com.seven.admin.service.SysUserPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户与岗位关联表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2020-12-24 14:00:27
 */

@Slf4j
@Service("sysUserPostService")
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPostEntity> implements SysUserPostService {

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Override
    public int deleteByUserId(Integer userId) {
        QueryWrapper<SysUserPostEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return userPostMapper.delete(queryWrapper);
    }

    @Override
    public int addUserPost(Integer userId, List<Integer> postIds) {
        if (userId == null || userId.equals(0) || CollectionUtils.isEmpty(postIds)) {
            return 0;
        }
        List<SysUserPostEntity> list = new ArrayList<SysUserPostEntity>();
        postIds.forEach(item -> {
            SysUserPostEntity userPost = new SysUserPostEntity();
            userPost.setUserId(userId);
            userPost.setPostId(item);
            list.add(userPost);
        });
        if (list.size() > 0 && saveBatch(list)) {
            return list.size();
        }
        return 0;
    }

    @Override
    public List<SysUserPostEntity> queryByPostId(Integer postId) {
        QueryWrapper<SysUserPostEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id", postId);
        return userPostMapper.selectList(queryWrapper);
    }


}
