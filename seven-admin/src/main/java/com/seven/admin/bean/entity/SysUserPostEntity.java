package com.seven.admin.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户与岗位关联表
 * 
 * @author chendongdong
 * @date 2020-12-24 14:00:27
 * @version 1.0
 */
@TableName("sys_user_post")
@Getter
@Setter
@ApiModel(description = "用户与岗位关联表")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysUserPostEntity implements Serializable {

private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "岗位ID")
    private Integer postId;
}
