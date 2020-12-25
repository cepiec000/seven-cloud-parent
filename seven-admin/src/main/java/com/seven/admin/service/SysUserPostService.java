package com.seven.admin.service;


import com.seven.admin.bean.dto.AddUserDTO;
import com.seven.admin.bean.dto.EditUserDTO;
import com.seven.admin.bean.entity.SysUserPostEntity;
import com.seven.admin.bean.query.PostQuery;
import com.seven.admin.bean.vo.UserVO;
import com.seven.comm.core.page.PageInfo;

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

    /**
     * 根据 岗位查询
     * @param postId
     * @return
     */
    List<SysUserPostEntity> queryByPostId(Integer postId);
}

