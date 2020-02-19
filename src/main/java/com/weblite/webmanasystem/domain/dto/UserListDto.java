package com.weblite.webmanasystem.domain.dto;

/**
 * @author ：Beatrice
 * @date ：Created in 2020/2/18 17:23
 * @Description:
 */
public class UserListDto {
    private String aIdentify;
    private String uId;
    private String username;

    public UserListDto() {
    }

    public UserListDto(String aIdentify, String uId, String username) {
        this.aIdentify = aIdentify;
        this.uId = uId;
        this.username = username;
    }

    public String getaIdentify() {
        return aIdentify;
    }

    public UserListDto setaIdentify(String aIdentify) {
        this.aIdentify = aIdentify;
        return this;
    }

    public String getuId() {
        return uId;
    }

    public UserListDto setuId(String uId) {
        this.uId = uId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserListDto setUsername(String username) {
        this.username = username;
        return this;
    }
}
