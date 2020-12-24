package com.seven.admin.service;


import java.util.List;

/**
 * 用户与岗位关联表
 *
 * @author chendongdong
 * @date 2020-12-24 14:00:27
 * @version 1.0
 */
public interface SysUserPostService {

    /**
     * 根据用户删除用户 与岗位关联
     * @param id
     * @return
     */
    int deleteByUserId(Integer id);

    /**
     * 批量添加用户岗位
     * @param userId
     * @param postIds
     * @return
     */
    int addUserPost(Integer userId, List<Integer> postIds);
}

