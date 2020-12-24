package com.seven.admin.bean.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 部门管理
 * 
 * @author chendongdong
 * @date 2020-12-21 15:15:48
 * @version 1.0
 */
@TableName("sys_dept")
@Getter
@Setter
@ApiModel(description = "部门管理")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeptVO implements Serializable {

private static final long serialVersionUID = 1L;



    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer deptId;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除  -1：已删除  0：正常")
    private Integer delFlag;

    @ApiModelProperty(value = "")
    private Integer parentId;

    @ApiModelProperty(value = "上级部门名称")
    private String parentName;
}
