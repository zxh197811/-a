package com.fh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;
@TableName("t_order")
public class Order {

   /* id 订单号(时间戳+雪花算法)
    User_id 会员id
    Status 订单状态 1代表未支付 2代表已支付
    Create_time 订单创建时间
    Flight_id 航班id
    Pay_time 支付时间
    Total_count 总票数
    Total_price 总票价
    Pay_type 支付方式:1代表在线支付 2代表现金支付*/

@TableId(type= IdType.INPUT)
   private String id;
   private Integer userid;
   private Integer status;
   private Date createtime;
   private Integer flightid;
   private Date paytime;
   private Long totalcount;
   private BigDecimal totalprice;
   private Integer paytype;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getFlightid() {
        return flightid;
    }

    public void setFlightid(Integer flightid) {
        this.flightid = flightid;
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

    public Long getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(Long totalcount) {
        this.totalcount = totalcount;
    }

    public BigDecimal getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    public Integer getPaytype() {
        return paytype;
    }

    public void setPaytype(Integer paytype) {
        this.paytype = paytype;
    }
}
