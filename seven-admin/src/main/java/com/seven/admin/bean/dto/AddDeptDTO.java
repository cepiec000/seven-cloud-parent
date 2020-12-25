package com.seven.admin.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 部门管理
 * 
 * @author chendongdong
 * @date 2020-12-21 15:15:48
 * @version 1.0
 */
@Getter
@Setter
@ApiModel(description = "部门管理")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddDeptDTO implements Serializable {

private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "部门名称")
    @NotEmpty(message = "部门名称不能为空")
    private String name;

    @ApiModelProperty(value = "父部门ID")
    @NotNull(message = "父部门不能为空")
    private Integer parentId;

    @ApiModelProperty(value = "负责人")
    @NotEmpty(message = "负责人不可为空")
    private String leader;

    @ApiModelProperty(value = "联系电话")
    @NotEmpty(message = "联系电话不能为空")
    private String phone;

    @ApiModelProperty(value = "email")
    @NotEmpty(message = "邮箱不能为空")
    private String email;

}
