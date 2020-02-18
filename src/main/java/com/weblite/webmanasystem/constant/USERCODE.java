package com.weblite.webmanasystem.constant;

public enum USERCODE {
    STU("STU","一般用户"),
    MANAGER("MANAGER","管理员");


    private String code;
    private String auth;

    USERCODE() {
    }

    USERCODE(String code) {
        this.code = code;
    }

    USERCODE(String code, String auth) {
        this.code = code;
        this.auth = auth;
    }

    public String getCode() {
        return code;
    }

    public USERCODE setCode(String code) {
        this.code = code;
        return this;
    }

    public String getAuth() {
        return auth;
    }

    public USERCODE setAuth(String auth) {
        this.auth = auth;
        return this;
    }
}
