package com.seven.admin.service;


import com.seven.admin.bean.dto.AddPostDTO;
import com.seven.admin.bean.dto.AddUserDTO;
import com.seven.admin.bean.dto.EditPostDTO;
import com.seven.admin.bean.dto.EditUserDTO;
import com.seven.admin.bean.entity.SysPostEntity;
import com.seven.admin.bean.query.PostQuery;
import com.seven.admin.bean.vo.PostVO;
import com.seven.comm.core.page.PageInfo;

/**
 * 岗位信息表
 *
 * @author chendongdong
 * @date 2020-12-24 14:00:27
 * @version 1.0
 */
public interface SysPostService {
    /**
     * 查询岗位列表
     * @param post
     * @return
     */
    PageInfo<PostVO> queryVoList(PostQuery post);

    /**
     * 添加岗位
     * @param post
     * @return
     */
    Boolean addPost(AddPostDTO post);

    /**
     * 修改岗位
     * @param post
     * @return
     */
    Boolean updatePost(EditPostDTO post);

    /**
     * 删除岗位
     * @param userIds
     * @return
     */
    Boolean delete(Integer[] userIds);

    /**
     * 根据ID 获取岗位
     * @param id
     * @return
     */
    SysPostEntity get(Integer id);
    /**
     * 根据部门名获取部门
     * @param postName
     * @return
     */
    SysPostEntity checkPostNameUnique(String postName);

    /**
     * 根据部门code获取部门
     * @param postCode
     * @return
     */
    SysPostEntity checkPostCodeUnique(String postCode);
}

