package com.seven.admin.controller;

import com.seven.admin.bean.query.LoginUserQuery;
import com.seven.admin.constant.AdminStatCode;
import com.seven.comm.core.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {


    @ApiOperation(value = "登录")
    @PostMapping(value = "login")
    public ApiResponse<Boolean> login(@RequestBody LoginUserQuery user) {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword(), user.isRemember());
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            return ApiResponse.success();
        } catch (AuthenticationException e) {
            String message = e.getMessage();
            if (StringUtils.isNotEmpty(message)) {
                return ApiResponse.failed(message);
            }

            return ApiResponse.failed(AdminStatCode.TOKEN_BE_EMPTY);
        }
    }
}
