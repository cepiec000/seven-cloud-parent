package com.seven.admin.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class EditUserDTO {
    @ApiModelProperty(value = "主键ID")
    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    @Max(value = 20,message = "手机号长度不正确")
    private String phone;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "部门ID")
    @NotNull(message = "部门不能为空")
    private Integer deptId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @ApiModelProperty(value = "0-正常，1-锁定")
    private String lockFlag;

    @ApiModelProperty(value = "角色组")
    private List<Integer> roleIds;

    @ApiModelProperty(value = "岗位组")
    private List<Integer> postIds;

}
