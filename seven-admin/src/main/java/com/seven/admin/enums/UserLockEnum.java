package com.seven.admin.enums;

public enum UserLockEnum {
    UN_LOCK(0, "未锁定"),
    LOCKED(1, "已锁定");
    private String desc;
    private Integer code;

    UserLockEnum(Integer code, String desc) {
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
