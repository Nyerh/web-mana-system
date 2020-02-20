package com.weblite.webmanasystem.domain.dto;

import org.springframework.stereotype.Component;

/**
 * @author ：Beatrice
 * @date ：Created in 2020/2/17 15:13
 * @Description:
 */
@Component
public class UserInfoDto {

    private String uId;

    private String aIdentity;

    public UserInfoDto() {
    }

    public UserInfoDto(String uId, String aIdentity) {
        this.uId = uId;
        this.aIdentity = aIdentity;
    }

    public String getuId() {
        return uId;
    }

    public UserInfoDto setuId(String uId) {
        this.uId = uId;
        return this;
    }

    public String getaIdentity() {
        return aIdentity;
    }

    public UserInfoDto setaIdentity(String aIdentity) {
        this.aIdentity = aIdentity;
        return this;
    }
}
