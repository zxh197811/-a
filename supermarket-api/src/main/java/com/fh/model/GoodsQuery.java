package com.fh.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fh.util.DataTablePageBean;

import java.math.BigDecimal;
import java.util.Date;

public class GoodsQuery extends DataTablePageBean {

    private String name;
    private BigDecimal minprice;
    private BigDecimal maxprice;
    private Integer minsales;
    private Integer maxsales;
    private String barcode;
    private Integer minstock;
    private Integer maxstock;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date minproducedDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date maxproducedDate;
    private Integer minshelflife;
    private Integer maxshelflife;
    private Integer status;
    private Integer areaid;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMinprice() {
        return minprice;
    }

    public void setMinprice(BigDecimal minprice) {
        this.minprice = minprice;
    }

    public BigDecimal getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(BigDecimal maxprice) {
        this.maxprice = maxprice;
    }

    public Integer getMinsales() {
        return minsales;
    }

    public void setMinsales(Integer minsales) {
        this.minsales = minsales;
    }

    public Integer getMaxsales() {
        return maxsales;
    }

    public void setMaxsales(Integer maxsales) {
        this.maxsales = maxsales;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getMinstock() {
        return minstock;
    }

    public void setMinstock(Integer minstock) {
        this.minstock = minstock;
    }

    public Integer getMaxstock() {
        return maxstock;
    }

    public void setMaxstock(Integer maxstock) {
        this.maxstock = maxstock;
    }

    public Date getMinproducedDate() {
        return minproducedDate;
    }

    public void setMinproducedDate(Date minproducedDate) {
        this.minproducedDate = minproducedDate;
    }

    public Date getMaxproducedDate() {
        return maxproducedDate;
    }

    public void setMaxproducedDate(Date maxproducedDate) {
        this.maxproducedDate = maxproducedDate;
    }

    public Integer getMinshelflife() {
        return minshelflife;
    }

    public void setMinshelflife(Integer minshelflife) {
        this.minshelflife = minshelflife;
    }

    public Integer getMaxshelflife() {
        return maxshelflife;
    }

    public void setMaxshelflife(Integer maxshelflife) {
        this.maxshelflife = maxshelflife;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }
}
