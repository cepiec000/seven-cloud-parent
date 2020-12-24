package com.seven.admin.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/23 14:41
 */
@Data
public class AddRoleDTO {
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "角色名称")
    @NotNull(message = "角色名称不能为空")
    private String roleName;

    @ApiModelProperty(value = "角色code")
    @NotNull(message = "角色编号不能为空")
    private String roleCode;

    @ApiModelProperty(value = "描述")
    private String roleDesc;

    /** 菜单组 */
    @ApiModelProperty(value = "所拥有的菜单")
    private Integer[] menuIds;
}
