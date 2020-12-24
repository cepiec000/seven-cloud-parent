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
 * 菜单权限表
 * 
 * @author chendongdong
 * @date 2020-12-21 15:15:48
 * @version 1.0
 */
@TableName("sys_menu")
@Getter
@Setter
@ApiModel(description = "菜单权限表")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysMenuEntity implements Serializable {

private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单权限标识")
    private String permission;

    @ApiModelProperty(value = "前端URL")
    private String path;

    @ApiModelProperty(value = "父菜单ID")
    private Integer parentId;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "VUE页面")
    private String component;

    @ApiModelProperty(value = "排序值")
    private Integer sort;

    @ApiModelProperty(value = "0-开启，1- 关闭")
    private String keepAlive;

    @ApiModelProperty(value = "菜单类型 （0菜单 1按钮）")
    private String type;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除标记(0--正常 1--删除)")
    private Integer delFlag;
}
