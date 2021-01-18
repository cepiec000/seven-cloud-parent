package com.seven.admin.bean.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 角色和部门关联表
 * 
 * @author chendongdong
 * @date 2021-01-04 10:43:24
 * @version 1.0
 */
@TableName("sys_role_dept")
@Getter
@Setter
@ApiModel(description = "角色和部门关联表")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysRoleDeptEntity implements Serializable {

private static final long serialVersionUID = 1L;

    @TableField
    @ApiModelProperty(value = "角色ID")
    private Long roleId;
    @TableField
    @ApiModelProperty(value = "部门ID")
    private Long deptId;
}
