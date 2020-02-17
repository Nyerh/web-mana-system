package com.weblite.webmanasystem.constant;

public enum STATE {
    SystemErr(0, "系统错误"),
    Success(100, "操作成功"),
    ParamErr(101, "参数错误"),
    InfoErr(102, "信息错误"),
    Error(103, "操作失败"),
    PermissionDenied(104, "没有权限"),
    PassportErr(105, "用户校验失败");

    private int state;
    private String msg;

    STATE() {
    }

    STATE(int state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public STATE setState(int state) {
        this.state = state;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public STATE setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
