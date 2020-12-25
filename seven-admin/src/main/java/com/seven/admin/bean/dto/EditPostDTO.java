package com.seven.admin.bean.dto;

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
public class EditPostDTO implements Serializable {

private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "岗位ID")
    @NotNull(message = "岗位ID不能为空")
    private Integer postId;

    @ApiModelProperty(value = "岗位编码")
    @NotEmpty(message = "岗位code不能为空")
    private String postCode;

    @ApiModelProperty(value = "岗位名称")
    @NotEmpty(message = "岗位名称不能为空")
    private String postName;

    @ApiModelProperty(value = "显示顺序")
    private Integer postSort;

    @ApiModelProperty(value = "状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;
}
