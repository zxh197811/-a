package com.fh.controller;

import com.fh.model.Area;
import com.fh.model.AreaQuery;
import com.fh.service.AreaService;
import com.fh.util.DataTableResult;
import com.fh.util.ResponseEnum;
import com.fh.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AreaController {

        @Autowired
        private AreaService areaService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("selectAreaBy")
    public ServerResponse selectAreaBy(@RequestBody AreaQuery areaQuery){
        try {
            DataTableResult dataTableResult= areaService.selectAreaBy(areaQuery);
            return ServerResponse.success(ResponseEnum.SUCCESS,dataTableResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }



    @RequestMapping("selectArea")
    public ServerResponse selectArea(){
        try {
            List<Area> areaList= areaService.selectArea();
            return ServerResponse.success(ResponseEnum.SUCCESS,areaList);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("addArea")
    public ServerResponse addArea(Area area){
        try {
           areaService.addArea(area);
            return  ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }

    @RequestMapping("selectAreaById")
    public ServerResponse selectAreaById(String token){
        try {
            if(!redisTemplate.hasKey(token)){
                return ServerResponse.error(ResponseEnum.ERROR);
            }
            Integer id= (Integer) redisTemplate.opsForValue().get(token);
           Area area=areaService.selectAreaById(id);
            return  ServerResponse.success(ResponseEnum.SUCCESS,area);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("updateArea")
    public ServerResponse updateArea(Area area){
        try {
            areaService.updateArea(area);
            return  ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("deleteArea")
    public ServerResponse deleteArea(Integer id){
        try {
            areaService.deleteArea(id);
            return  ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("deleteAllArea")
    public ServerResponse deleteAllArea(@RequestParam("ids[]")List<Integer> ids){
        try {
            areaService.deleteAllArea(ids);
            return  ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }





}
