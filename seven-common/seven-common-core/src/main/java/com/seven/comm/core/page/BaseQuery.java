package com.seven.comm.core.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseQuery {
    @ApiModelProperty(value = "0-正常，1-删除")
    private Integer pageNo=1;
    @ApiModelProperty(value = "0-正常，1-删除")
    private Integer size=20;
    @ApiModelProperty(value = "排序")
    private String orderBy;
}
