package com.weblite.webmanasystem.domain.entity;

import java.util.Date;

public class User {
    /**
     * 用户id
     */
    private String uId;

    /**
     * 用户名
     */
    private String uName;

    /**
     * 密码
     */
    private String uPassword;

    /**
     * 班级
     */
    private String uClass;

    /**
     * 年级
     */
    private String grade;

    /**
     * 学号
     */
    private String stuId;

    /**
     * 姓名
     */
    private String stuName;

    /**
     * 新增时间
     */
    private Date dataCreateTime;

    /**
     * 修改时间
     */
    private Date dataModifyTime;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuClass() {
        return uClass;
    }

    public void setuClass(String uClass) {
        this.uClass = uClass;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Date getDataCreateTime() {
        return dataCreateTime;
    }

    public void setDataCreateTime(Date dataCreateTime) {
        this.dataCreateTime = dataCreateTime;
    }

    public Date getDataModifyTime() {
        return dataModifyTime;
    }

    public void setDataModifyTime(Date dataModifyTime) {
        this.dataModifyTime = dataModifyTime;
    }
}