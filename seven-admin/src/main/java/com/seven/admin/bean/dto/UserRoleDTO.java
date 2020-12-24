package com.seven.admin.bean.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2020/12/23 12:07
 */
@Data
public class UserRoleDTO {
    @NotNull(message = "用户ID不能为空")
    private Integer userId;
    @NotNull(message = "角色ID不能为空")
    private List<Integer> roleIds;
}
