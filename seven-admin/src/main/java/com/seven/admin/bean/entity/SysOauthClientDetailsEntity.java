package com.seven.admin.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * 终端信息表
 * 
 * @author chendongdong
 * @date 2020-12-21 15:15:48
 * @version 1.0
 */
@TableName("sys_oauth_client_details")
@Getter
@Setter
@ApiModel(description = "终端信息表")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysOauthClientDetailsEntity implements Serializable {

private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    @ApiModelProperty(value = "")
    private String clientId;

    @ApiModelProperty(value = "")
    private String resourceIds;

    @ApiModelProperty(value = "")
    private String clientSecret;

    @ApiModelProperty(value = "")
    private String scope;

    @ApiModelProperty(value = "")
    private String authorizedGrantTypes;

    @ApiModelProperty(value = "")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "")
    private String authorities;

    @ApiModelProperty(value = "")
    private Integer accessTokenValidity;

    @ApiModelProperty(value = "")
    private Integer refreshTokenValidity;

    @ApiModelProperty(value = "")
    private String additionalInformation;

    @ApiModelProperty(value = "")
    private String autoapprove;
}
