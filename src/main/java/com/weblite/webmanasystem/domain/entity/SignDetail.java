package com.weblite.webmanasystem.domain.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SignDetail {
    private Integer id;

    private String uId;

    /**
     * 登记登录时间
     */
    private Date signTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }
}