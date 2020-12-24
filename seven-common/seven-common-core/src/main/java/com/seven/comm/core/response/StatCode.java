package com.seven.comm.core.response;

/**
 * 公共系统异常 code码
 *
 * @author v_chendongdong
 * @date 2020/12/21
 */
public enum StatCode implements ErrorCode {
    SUCCESS(1, "SUCCESS"),
    FAILED(-1, "FAILED"),
    PARAMETER_ERROR(90001, "参数异常"),
    MISSING_REQUIRED_PARAMETER(20002, "缺少必填参数"),
    UNKNOWN_EXCEPTION(20003, "未知异常,请联系管理员"),
    TOKEN_BE_EMPTY(20004, "token不能为空");

    private int code;
    private String msg;

    private StatCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
