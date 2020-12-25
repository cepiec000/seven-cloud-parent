package com.seven.admin.enums;

public enum StatusEnum {
    VALID(0, "有效"),
    INVALID(1, "无效");
    private String desc;
    private Integer code;

    StatusEnum(Integer code, String desc) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
