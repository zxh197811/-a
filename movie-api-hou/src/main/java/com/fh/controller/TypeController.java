package com.fh.controller;


import com.fh.model.Area;
import com.fh.model.Type;
import com.fh.model.TypeQuery;
import com.fh.service.TypeService;
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
public class TypeController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("selectTypeBy")
    public ServerResponse selectTypeBy(@RequestBody TypeQuery typeQuery){
        try {
            DataTableResult dataTableResult= typeService.selectTypeBy(typeQuery);
            return ServerResponse.success(ResponseEnum.SUCCESS,dataTableResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }



    @RequestMapping("selectType")
    public ServerResponse selectType(){
        try {
            List<Type> typeList= typeService.selectType();
            return ServerResponse.success(ResponseEnum.SUCCESS,typeList);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }

    @RequestMapping("addType")
    public ServerResponse addType(Type type){
        try {
            typeService.addType(type);
            return  ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }

    @RequestMapping("selectTypeById")
    public ServerResponse selectTypeById(String token){
        try {
            if(!redisTemplate.hasKey(token)){
                return ServerResponse.error(ResponseEnum.ERROR);
            }
            Integer id= (Integer) redisTemplate.opsForValue().get(token);
            Type type=typeService.selectTypeById(id);
            return  ServerResponse.success(ResponseEnum.SUCCESS,type);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("updateType")
    public ServerResponse updateType(Type type){
        try {
            typeService.updateType(type);
            return  ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("deleteType")
    public ServerResponse deleteType(Integer id){
        try {
            typeService.deleteType(id);
            return  ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("deleteAllType")
    public ServerResponse deleteAllType(@RequestParam("ids[]")List<Integer> ids){
        try {
            typeService.deleteAllType(ids);
            return  ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }
    
}
