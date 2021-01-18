package com.seven.comm.core.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "0-正常，1-删除")
    private Integer pageNo = 1;
    @ApiModelProperty(value = "0-正常，1-删除")
    private Integer size = 20;
    @ApiModelProperty(value = "排序")
    private String orderBy;

    private Date beginTime;
    private Date endTime;
    private String dataScope;
}
