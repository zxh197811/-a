package com.fh.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fh.util.DataTablePageBean;

import java.util.Date;

public class FlightQuery extends DataTablePageBean {


      private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
      private Date starttime;
      private Integer typeid;
      private Integer idticket;
      private Integer type;
      private Integer typename;
      private Integer startareaid;
      private Integer endareaid;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Integer getIdticket() {
        return idticket;
    }

    public void setIdticket(Integer idticket) {
        this.idticket = idticket;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTypename() {
        return typename;
    }

    public void setTypename(Integer typename) {
        this.typename = typename;
    }

    public Integer getStartareaid() {
        return startareaid;
    }

    public void setStartareaid(Integer startareaid) {
        this.startareaid = startareaid;
    }

    public Integer getEndareaid() {
        return endareaid;
    }

    public void setEndareaid(Integer endareaid) {
        this.endareaid = endareaid;
    }
}
