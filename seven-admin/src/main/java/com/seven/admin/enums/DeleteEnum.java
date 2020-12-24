package com.seven.admin.enums;

public enum DeleteEnum {
    NO_DELETE(0, "未删除"),
    DELETED(1, "已删除");
    private String desc;
    private Integer code;

    DeleteEnum(Integer code, String desc) {
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
