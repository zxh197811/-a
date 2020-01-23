package com.fh.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
@TableName("orderitem")
public class OrderItem {
   /* Id 订单详情id，自增
    User_id 会员id
    Order_id 订单id
    Ticket_id 机票id
    Ticket_type 机票类型 1代表头等舱，2代表经济舱
    Price 票价
    Real_name 乘机人姓名
    Id_card 乘机人身份证号码*/
@TableId
   private Integer id;
   private Integer userid;
   private String orderid;
   private Integer ticketid;
   private Integer tickettype;
   private BigDecimal price;
   private String realname;
   private Long idcard;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Integer getTicketid() {
        return ticketid;
    }

    public void setTicketid(Integer ticketid) {
        this.ticketid = ticketid;
    }

    public Integer getTickettype() {
        return tickettype;
    }

    public void setTickettype(Integer tickettype) {
        this.tickettype = tickettype;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Long getIdcard() {
        return idcard;
    }

    public void setIdcard(Long idcard) {
        this.idcard = idcard;
    }
}
