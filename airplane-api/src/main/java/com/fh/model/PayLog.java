package com.fh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
@TableName("paylog")
public class PayLog {
   /* Out_trade_no 商户订单号，用于到时候调用微信统一下单接口和查询订单接口
    Order_id 订单号
    Transaction_id 微信订单号
    User_id 用户iD
    Pay_money 支付金额
    Pay_status 支付状态 1代表未支付 2代表已支付
    Pay_type 支付类型 1代表微信支付 2代表支付宝支付*/
@TableId(type= IdType.INPUT)
   private String outtradeno;
   private String orderid;
   private String transactionid;
   private Integer userid;
   private BigDecimal paymoney;
   private Integer paystatus;
   private Integer paytype;


    public String getOuttradeno() {
        return outtradeno;
    }

    public void setOuttradeno(String outtradeno) {
        this.outtradeno = outtradeno;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public BigDecimal getPaymoney() {
        return paymoney;
    }

    public void setPaymoney(BigDecimal paymoney) {
        this.paymoney = paymoney;
    }

    public Integer getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(Integer paystatus) {
        this.paystatus = paystatus;
    }

    public Integer getPaytype() {
        return paytype;
    }

    public void setPaytype(Integer paytype) {
        this.paytype = paytype;
    }
}
