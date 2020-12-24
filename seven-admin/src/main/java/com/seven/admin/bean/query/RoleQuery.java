package com.seven.admin.bean.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.seven.comm.core.page.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统角色表
 * 
 * @author chendongdong
 * @date 2020-12-21 15:15:48
 * @version 1.0
 */
@ApiModel(description = "系统角色表")
@Data
public class RoleQuery extends BaseQuery implements Serializable {

private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer roleId;

    @ApiModelProperty(value = "")
    private String roleName;

    @ApiModelProperty(value = "")
    private String roleCode;

    @ApiModelProperty(value = "")
    private Date begin;

    @ApiModelProperty(value = "")
    private Date end;

    @ApiModelProperty(value = "")
    private Date updateTime;

    @ApiModelProperty(value = "删除标识（0-正常,1-删除）")
    private String delFlag;
}
