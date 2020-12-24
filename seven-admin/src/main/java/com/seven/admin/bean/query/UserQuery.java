package com.seven.admin.bean.query;

import com.seven.comm.core.page.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class UserQuery extends BaseQuery {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String phone;


    @ApiModelProperty(value = "部门ID")
    private Integer deptId;

    @ApiModelProperty(value = "创建时间开始")
    private Date begin;

    @ApiModelProperty(value = "创建时间截至")
    private Date end;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "0-正常，1-锁定")
    private String lockFlag;

}
