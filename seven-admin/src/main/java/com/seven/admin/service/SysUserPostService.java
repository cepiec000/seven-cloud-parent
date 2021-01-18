package com.seven.admin.service;

import com.seven.admin.bean.entity.SysUserEntity;

/**
 * 用户与岗位关联表
 *
 * @author chendongdong
 * @date 2021-01-04 10:43:24
 * @version 1.0
 */
public interface SysUserPostService {

    /**
     * 批量新增
     * @param user
     * @return
     */
    int insertUserPost(SysUserEntity user);

    /**
     * 根据用户ID 删除
     * @param userId
     * @return
     */
    int deleteByUserId(Long userId);

    /**
     * 批量删除
     * @param userIds
     * @return
     */
    int deleteByUserIds(Long[] userIds);

    /**
     * 根据岗位ID 统计
     * @param postId
     * @return
     */
    int countByPostId(Long postId);
}

