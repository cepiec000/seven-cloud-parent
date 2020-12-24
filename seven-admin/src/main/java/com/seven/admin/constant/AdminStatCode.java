package com.seven.admin.constant;

import com.seven.comm.core.response.ErrorCode;

public enum  AdminStatCode implements ErrorCode {
    TOKEN_BE_EMPTY(100001, "账号或密码错误！"),
    USER_NOT_FIND(100002,"用户不存在！"),
    USER_ON_LOCKED(100003,"帐号已被锁定，禁止登录！"),
    USERNAME_DUPLICATE(100004,"用户名已存在，请修改后保存！"),
    PHONE_DUPLICATE(100005,"手机号已存在，请修改后保存！"),
    EMAIL_DUPLICATE(100006,"邮箱已存在，请修改后保存！"),
    ROLE_NAME_DUPLICATE(100007,"角色名称已存在，请修改后保存！" ),
    ROLE_CODE_DUPLICATE(100008,"角色编号已存在，请修改后保存！" );

    private int code;
    private String msg;

    private AdminStatCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
