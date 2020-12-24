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
 * 字典项
 * 
 * @author chendongdong
 * @date 2020-12-21 15:15:48
 * @version 1.0
 */
@TableName("sys_dict_item")
@Getter
@Setter
@ApiModel(description = "字典项")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysDictItemEntity implements Serializable {

private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Integer id;

    @ApiModelProperty(value = "")
    private Integer dictId;

    @ApiModelProperty(value = "")
    private String value;

    @ApiModelProperty(value = "")
    private String label;

    @ApiModelProperty(value = "")
    private String type;

    @ApiModelProperty(value = "")
    private String description;

    @ApiModelProperty(value = "排序（升序）")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "")
    private String remarks;

    @ApiModelProperty(value = "")
    private Integer delFlag;
}
