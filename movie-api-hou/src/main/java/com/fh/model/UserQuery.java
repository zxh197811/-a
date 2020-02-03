package com.fh.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fh.util.DataTablePageBean;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class UserQuery extends DataTablePageBean {
    private String name;
    private String realname;
    private String phonenumber;
    private String email;
    private Integer sex;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date minbirthday;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date maxbirthday;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date mincreateDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date maxcreateDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date minupdateDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date maxupdateDate;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getMinbirthday() {
        return minbirthday;
    }

    public void setMinbirthday(Date minbirthday) {
        this.minbirthday = minbirthday;
    }

    public Date getMaxbirthday() {
        return maxbirthday;
    }

    public void setMaxbirthday(Date maxbirthday) {
        this.maxbirthday = maxbirthday;
    }

    public Date getMincreateDate() {
        return mincreateDate;
    }

    public void setMincreateDate(Date mincreateDate) {
        this.mincreateDate = mincreateDate;
    }

    public Date getMaxcreateDate() {
        return maxcreateDate;
    }

    public void setMaxcreateDate(Date maxcreateDate) {
        this.maxcreateDate = maxcreateDate;
    }

    public Date getMinupdateDate() {
        return minupdateDate;
    }

    public void setMinupdateDate(Date minupdateDate) {
        this.minupdateDate = minupdateDate;
    }

    public Date getMaxupdateDate() {
        return maxupdateDate;
    }

    public void setMaxupdateDate(Date maxupdateDate) {
        this.maxupdateDate = maxupdateDate;
    }
}
