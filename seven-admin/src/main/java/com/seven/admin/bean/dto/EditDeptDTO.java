package com.seven.admin.bean.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

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
public class EditDeptDTO implements Serializable {

private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "部门ID")
    @NotNull(message = "部门ID不能为空")
    private Integer deptId;

    @ApiModelProperty(value = "部门名称")
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

    @ApiModelProperty(value = "是否删除  0：有效  0：停用")
    @NotNull(message = "状态不能为空")
    private Integer status;
}
