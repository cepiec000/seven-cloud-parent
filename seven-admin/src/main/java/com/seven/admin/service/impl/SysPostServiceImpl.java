package com.seven.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.admin.bean.entity.SysPostEntity;
import com.seven.admin.bean.query.SysPostQuery;
import com.seven.admin.constant.UserConstants;
import com.seven.admin.mapper.SysPostMapper;
import com.seven.admin.service.SysPostService;
import com.seven.admin.service.SysUserPostService;
import com.seven.comm.core.config.SevenQueryWrapper;
import com.seven.comm.core.execption.SevenException;
import com.seven.comm.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;


/**
 * 岗位信息表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2021-01-04 10:43:24
 */

@Slf4j
@Service("sysPostService")
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPostEntity> implements SysPostService {
    @Autowired
    private SysPostMapper sysPostMapper;

    @Autowired
    private SysUserPostService userPostService;

    @Override
    public List<SysPostEntity> selectPostsByUserName(String userName) {
        return sysPostMapper.selectByUserName(userName);
    }

    @Override
    public List<SysPostEntity> selectPostList(SysPostQuery post) {
        SevenQueryWrapper<SysPostEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.slike("post_code", post.getPostCode());
        queryWrapper.seq("status", post.getStatus());
        queryWrapper.slike("post_name", post.getPostName());
        return sysPostMapper.selectList(queryWrapper);
    }

    @Override
    public List<SysPostEntity> selectPostAll() {
        return selectPostList(new SysPostQuery());
    }

    @Override
    public SysPostEntity selectPostById(Long postId) {
        return sysPostMapper.selectById(postId);
    }

    @Override
    public List<Integer> selectPostListByUserId(Long userId) {
        return sysPostMapper.selectPostIdByUserId(userId);
    }

    @Override
    public String checkPostNameUnique(SysPostEntity post) {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPostEntity info = checkPostNameUnique(post.getPostName());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    private SysPostEntity checkPostNameUnique(String postName) {
        SevenQueryWrapper<SysPostEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.seq("post_name", postName);
        queryWrapper.last(" limit 1");
        return sysPostMapper.selectOne(queryWrapper);
    }

    @Override
    public String checkPostCodeUnique(SysPostEntity post) {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPostEntity info = checkPostCodeUnique(post.getPostCode());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    private SysPostEntity checkPostCodeUnique(String postCode) {
        SevenQueryWrapper<SysPostEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.seq("post_code", postCode);
        queryWrapper.last(" limit 1");
        return sysPostMapper.selectOne(queryWrapper);
    }

    @Override
    public int countUserPostById(Long postId) {
        return userPostService.countByPostId(postId);
    }

    @Override
    public int deletePostById(Long postId) {
        return sysPostMapper.deleteById(postId);
    }

    @Override
    public int deletePostByIds(Long[] postIds) {
        for (Long postId : postIds) {
            if (countUserPostById(postId) > 0) {
                SysPostEntity post = selectPostById(postId);
                throw new SevenException(String.format("%1$s已分配,不能删除", post.getPostName()));
            }
        }
        return sysPostMapper.deleteBatchIds(Arrays.asList(postIds));
    }

    @Override
    public int insertPost(SysPostEntity post) {
        return sysPostMapper.insert(post);
    }

    @Override
    public int updatePost(SysPostEntity post) {
        return sysPostMapper.updateById(post);
    }

}
