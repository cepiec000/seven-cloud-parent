package com.seven.comm.core.execption;

/**
 * 自定义系统异常
 * @author v_chendongdong
 * @date 2020/12/21
 */
public class SevenException extends RuntimeException {
    private int code=-1;
    private Object data=null;
    private static final long serialVersionUID = 87623487264872394L;
    public SevenException(){

    }

    public SevenException(String msg) {
        super(msg, (Throwable)null, true, false);
    }

    public SevenException(int code, String msg) {
        super(msg, (Throwable)null, true, false);
        this.code = code;
    }

    public SevenException(int code, String msg, Object data) {
        super(msg, (Throwable)null, true, false);
        this.code = code;
        this.data = data;
    }

    public SevenException(int code, String msg, Object data, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
