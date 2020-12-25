package com.seven.admin.bean.query;

import com.seven.comm.core.page.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 岗位信息表
 * 
 * @author chendongdong
 * @date 2020-12-24 14:00:27
 * @version 1.0
 */
@Getter
@Setter
@ApiModel(description = "岗位信息表")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostQuery extends BaseQuery implements Serializable {

private static final long serialVersionUID = 1L;



    @ApiModelProperty(value = "岗位编码")
    private String postCode;

    @ApiModelProperty(value = "岗位名称")
    private String postName;

    @ApiModelProperty(value = "状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "创建时间")
    private Date begin;

    @ApiModelProperty(value = "创建时间")
    private Date end;

}
