package com.seven.admin.bean.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.seven.comm.core.page.BaseQuery;
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
public class DeptQuery extends BaseQuery implements Serializable {

private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "开始时间")
    private Date begin;

    @ApiModelProperty(value = "截至时间")
    private Date end;

}
