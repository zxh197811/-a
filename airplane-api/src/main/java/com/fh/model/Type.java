package com.fh.model;

import com.baomidou.mybatisplus.annotation.TableId;

public class Type {

    /*id 机型id
    name 机型名称
    type 机型类型(比如1代表大型，2代表中型，3代表小型)*/
   @TableId
    private Integer id;
    private String name;
    private Integer type;


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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
