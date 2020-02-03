package com.fh.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fh.util.DataTablePageBean;
import java.util.Date;

public class DeptQuery extends DataTablePageBean {

    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date mincreateDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date maxcreateDate;


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
}
