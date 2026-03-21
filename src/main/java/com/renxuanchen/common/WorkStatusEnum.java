package com.renxuanchen.common;

public enum WorkStatusEnum {

    NORMAL(0, "正常"),
    LATE(1, "迟到"),
    LEAVE(2, "早退"),
    LATE_AND_LEAVE(3, "迟到且早退"),
    OVERTIME(4, "加班");
    ;
    private int code;
    private String name;

    WorkStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
