package com.seven.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seven.admin.bean.dto.AddPostDTO;
import com.seven.admin.bean.dto.AddUserDTO;
import com.seven.admin.bean.dto.EditPostDTO;
import com.seven.admin.bean.dto.EditUserDTO;
import com.seven.admin.bean.entity.SysPostEntity;
import com.seven.admin.bean.entity.SysUserEntity;
import com.seven.admin.bean.query.PostQuery;
import com.seven.admin.bean.vo.PostVO;
import com.seven.admin.bean.vo.UserVO;
import com.seven.admin.constant.AdminStatCode;
import com.seven.admin.enums.DeleteEnum;
import com.seven.admin.enums.UserLockEnum;
import com.seven.admin.mapper.SysPostMapper;
import com.seven.admin.service.SysUserPostService;
import com.seven.comm.core.config.SevenQueryWrapper;
import com.seven.comm.core.enums.BetweenEnum;
import com.seven.comm.core.execption.SevenException;
import com.seven.comm.core.page.Page;
import com.seven.comm.core.page.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seven.admin.service.SysPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 岗位信息表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2020-12-24 14:00:27
 */

@Slf4j
@Service("sysPostService")
public class SysPostServiceImpl implements SysPostService {

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserPostService userPostService;

    @Override
    public PageInfo<PostVO> queryVoList(PostQuery post) {
        PageInfo<PostVO> pageInfo = new PageInfo<>();

        SevenQueryWrapper<SysPostEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.slikeLeft("post_name", post.getPostName());
        queryWrapper.slikeLeft("post_code", post.getPostCode());
        queryWrapper.dateBetween("create_time", post.getBegin(), post.getEnd(), BetweenEnum.ALL_CONTAIN);

        Page<SysPostEntity> page = postMapper.selectByPage(queryWrapper, post.getPageNo(), post.getSize());
        List<SysPostEntity> list = page.getRows();
        if (CollectionUtils.isEmpty(list)) {
            return pageInfo;
        }
        List<PostVO> nList = new ArrayList<>(list.size());
        list.forEach(item -> {
            PostVO postVO = new PostVO();
            BeanUtils.copyProperties(item, postVO);
            nList.add(postVO);
        });
        pageInfo.setRows(nList);
        return pageInfo;
    }

    @Override
    public Boolean addPost(AddPostDTO post) {
        if (checkPostNameUnique(post.getPostName()) != null) {
            throw new SevenException(AdminStatCode.POST_NAME_DUPLICATE.getMsg());
        }
        if (checkPostCodeUnique(post.getPostCode()) != null) {
            throw new SevenException(AdminStatCode.POST_CODE_DUPLICATE.getMsg());
        }
        SysPostEntity postEntity = new SysPostEntity();
        BeanUtils.copyProperties(post, postEntity);
        postEntity.setCreateTime(new Date());
        postEntity.setUpdateTime(new Date());
        postEntity.setStatus(DeleteEnum.NO_DELETE.getCode());
        postEntity.setPostSort(queryCountPost());
        if (postMapper.insert(postEntity) == 1) {
            return true;
        }
        return false;
    }

    public int queryCountPost() {
        return postMapper.selectCount(new QueryWrapper<>());
    }

    @Override
    public Boolean updatePost(EditPostDTO post) {
        SysPostEntity postEntity = checkPostNameUnique(post.getPostName());
        if (postEntity != null && !postEntity.getPostId().equals(post.getPostId())) {
            throw new SevenException(AdminStatCode.POST_NAME_DUPLICATE.getMsg());
        }
        postEntity = checkPostCodeUnique(post.getPostCode());
        if (postEntity != null && !postEntity.getPostId().equals(post.getPostId())) {
            throw new SevenException(AdminStatCode.POST_CODE_DUPLICATE.getMsg());
        }
        BeanUtils.copyProperties(post, postEntity);
        postEntity.setUpdateTime(new Date());
        if (postMapper.insert(postEntity) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean delete(Integer[] userIds) {
        if (userIds == null) {
            return false;
        }
        for (int id : userIds) {
           List list =  userPostService.queryByPostId(id);
           if (list!=null && list.size()>0){
               SysPostEntity post = get(id);
               throw new SevenException(String.format("%1$s已分配,不能删除", post.getPostName()));
           }
        }
        return true;
    }

    @Override
    public SysPostEntity get(Integer id) {
        QueryWrapper<SysPostEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id", id);
        return postMapper.selectOne(queryWrapper);
    }

    @Override
    public SysPostEntity checkPostNameUnique(String postName) {
        QueryWrapper<SysPostEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_name", postName);
        SysPostEntity user = postMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public SysPostEntity checkPostCodeUnique(String postCode) {
        QueryWrapper<SysPostEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_code", postCode);
        SysPostEntity user = postMapper.selectOne(queryWrapper);
        return user;
    }
}
