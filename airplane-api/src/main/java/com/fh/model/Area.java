package com.fh.model;

import com.baomidou.mybatisplus.annotation.TableId;

public class Area {

  /*  id 主键
    name 地区名称
    type 比如1代表城市 2代表机场 3代表航站楼
            pid
*/
@TableId
  private Integer id;
  private String name;
  private Integer type;
  private Integer pid;


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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
