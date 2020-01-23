package com.fh.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.List;

public class Flight {

   /* id 航班id
    name 航班名称
    type_id 机型id
    start_time 起飞时间
    end_time 到大时间
    start_terminal_id 出发机场航站楼id
    end_terminal_id 到大机场航站楼id*/
@TableId
   private Integer id;
   private String name;
   private Integer typeid;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date starttime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date endtime;
   private Integer startterminalid;
   private Integer endterminalid;
   @TableField(exist = false)
   private String typename;
    @TableField(exist = false)
   private String typetype;
    @TableField(exist = false)
    private String planename;
    @TableField(exist = false)
    private String starttimestr;
    @TableField(exist = false)
    private String endtimestr;
    @TableField(exist = false)
    private List<Ticket> ticketList;


    public String getPlanename() {
        return planename;
    }

    public void setPlanename(String planename) {
        this.planename = planename;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public String getEndtimestr() {
        return endtimestr;
    }

    public void setEndtimestr(String endtimestr) {
        this.endtimestr = endtimestr;
    }

    public String getStarttimestr() {
        return starttimestr;
    }

    public void setStarttimestr(String starttimestr) {
        this.starttimestr = starttimestr;
    }

    public String getTypetype() {
        return typetype;
    }

    public void setTypetype(String typetype) {
        this.typetype = typetype;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getStartterminalid() {
        return startterminalid;
    }

    public void setStartterminalid(Integer startterminalid) {
        this.startterminalid = startterminalid;
    }

    public Integer getEndterminalid() {
        return endterminalid;
    }

    public void setEndterminalid(Integer endterminalid) {
        this.endterminalid = endterminalid;
    }
}
