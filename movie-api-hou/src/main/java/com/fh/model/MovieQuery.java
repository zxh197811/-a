package com.fh.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fh.util.DataTablePageBean;

import java.util.Date;

public class MovieQuery extends DataTablePageBean {

    private String name;
    private Double mingrade;
    private Double maxgrade;
    private Integer ishot;
    private Integer typeid;
    private Integer isshow;
    private Integer decade;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date minshowDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date maxshowDate;
    private Integer areaid;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMingrade() {
        return mingrade;
    }

    public void setMingrade(Double mingrade) {
        this.mingrade = mingrade;
    }

    public Double getMaxgrade() {
        return maxgrade;
    }

    public void setMaxgrade(Double maxgrade) {
        this.maxgrade = maxgrade;
    }

    public Integer getIshot() {
        return ishot;
    }

    public void setIshot(Integer ishot) {
        this.ishot = ishot;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Integer getIsshow() {
        return isshow;
    }

    public void setIsshow(Integer isshow) {
        this.isshow = isshow;
    }

    public Integer getDecade() {
        return decade;
    }

    public void setDecade(Integer decade) {
        this.decade = decade;
    }

    public Date getMinshowDate() {
        return minshowDate;
    }

    public void setMinshowDate(Date minshowDate) {
        this.minshowDate = minshowDate;
    }

    public Date getMaxshowDate() {
        return maxshowDate;
    }

    public void setMaxshowDate(Date maxshowDate) {
        this.maxshowDate = maxshowDate;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }
}
