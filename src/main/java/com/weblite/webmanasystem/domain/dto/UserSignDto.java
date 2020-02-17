package com.weblite.webmanasystem.domain.dto;

import java.util.Date;

/**
 * @author ：Beatrice
 * @date ：Created in 2020/2/15 16:41
 * @Description:用户登录信息
 */
public class UserSignDto {
    private Integer id;

    private String uId;

    private Date signTime;

    public UserSignDto() {

    }

    public UserSignDto(Integer id, String uId, Date signTime) {
        this.id = id;
        this.uId = uId;
        this.signTime = signTime;
    }

    public Integer getId() {
        return id;
    }

    public UserSignDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getuId() {
        return uId;
    }

    public UserSignDto setuId(String uId) {
        this.uId = uId;
        return this;
    }

    public Date getSignTime() {
        return signTime;
    }

    public UserSignDto setSignTime(Date signTime) {
        this.signTime = signTime;
        return this;
    }
}
