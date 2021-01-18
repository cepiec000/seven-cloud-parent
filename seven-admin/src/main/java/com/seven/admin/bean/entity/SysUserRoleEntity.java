package com.seven.admin.bean.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用户和角色关联表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2021-01-04 10:43:24
 */
@TableName("sys_user_role")
@Getter
@Setter
@ApiModel(description = "用户和角色关联表")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysUserRoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @TableField
    @ApiModelProperty(value = "角色ID")
    private Long roleId;
}
