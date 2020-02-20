package com.weblite.webmanasystem.domain.entity;

import org.springframework.stereotype.Component;

@Component
public class Authority {
    private String aId;

    private String uId;

    /**
     * 权限身份，为一般用户和管理员
     */
    private String aIdentity;

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getaIdentity() {
        return aIdentity;
    }

    public void setaIdentity(String aIdentity) {
        this.aIdentity = aIdentity;
    }
}