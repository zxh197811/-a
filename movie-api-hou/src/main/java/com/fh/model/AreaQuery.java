package com.fh.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fh.util.DataTablePageBean;

import java.util.Date;

public class AreaQuery extends DataTablePageBean {

    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date mincreateDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date maxcreateDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date minupdateDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date maxupdateDate;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
