package com.seven.comm.core.response;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 统一API 返回结构
 * @author v_chendongdong
 * @data 2020/12/21
 * @param <T>
 */
@ApiModel("返回消息体")
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = -6267859767710893277L;
    @ApiModelProperty("状态码，1为成功，否则为失败")
    private Integer code = 1;
    @ApiModelProperty("消息")
    private String message = "";
    @ApiModelProperty("数据")
    private T data;

    public ApiResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(StatCode statCode) {
        this.code = statCode.getCode();
        this.message = statCode.getMsg();
    }

    public static ApiResponse success() {
        return new ApiResponse(StatCode.SUCCESS);
    }

    public static ApiResponse success(Object data) {
        return new ApiResponse(StatCode.SUCCESS.getCode(), StatCode.SUCCESS.getMsg(),data);
    }

    public static ApiResponse success(Integer code,String message) {
        return new ApiResponse(StatCode.SUCCESS.getCode(), StatCode.SUCCESS.getMsg(),null);
    }

    public static ApiResponse success(String message) {
        return new ApiResponse(StatCode.SUCCESS.getCode(),message,null);
    }

    public static ApiResponse failed() {
        return new ApiResponse(StatCode.FAILED);
    }

    public static ApiResponse failed(String msg) {
        return new ApiResponse(StatCode.FAILED.getCode(),msg,null);
    }



    public static ApiResponse failed(Integer code, String msg) {
        return new ApiResponse(code,msg,null);
    }


    public static ApiResponse failed(ErrorCode errorCode) {
        return new ApiResponse(errorCode.getCode(),errorCode.getMsg(),null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
