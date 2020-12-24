package com.seven.comm.core.enums;

public enum BetweenEnum {
    LEFT_CONTAIN(0, "左边包含"),
    RIGHT_CONTAIN(1, "右边包含"),
    ALL_CONTAIN(1, "全部包含"),
    NO_CONTAIN(3,"全部不包含");
    private String desc;
    private Integer code;

    BetweenEnum(Integer code, String desc) {
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
