package com.fh.model;

import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigDecimal;

public class Ticket {

  /*  id 机票id
    flight_id 航班id
    type 机票类型 1代表经济舱，2代表头等舱
    total_count 票数
    price 票价*/
  @TableId
    private Integer id;
    private Integer flightid;
    private Integer type;
    private Integer totalcount;
    private BigDecimal price;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlightid() {
        return flightid;
    }

    public void setFlightid(Integer flightid) {
        this.flightid = flightid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(Integer totalcount) {
        this.totalcount = totalcount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
