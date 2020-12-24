package com.seven.admin.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统角色表
 * 
 * @author chendongdong
 * @date 2020-12-21 15:15:48
 * @version 1.0
 */
@TableName("sys_role")
@Getter
@Setter
@ApiModel(description = "系统角色表")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysRoleEntity implements Serializable {

private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer roleId;

    @ApiModelProperty(value = "")
    private String roleName;

    @ApiModelProperty(value = "")
    private String roleCode;

    @ApiModelProperty(value = "")
    private String roleDesc;

    @ApiModelProperty(value = "")
    private Date createTime;

    @ApiModelProperty(value = "")
    private Date updateTime;

    @ApiModelProperty(value = "删除标识（0-正常,1-删除）")
    private Integer delFlag;
}
