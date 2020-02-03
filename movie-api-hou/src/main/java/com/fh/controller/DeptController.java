package com.fh.controller;

import com.fh.model.Dept;
import com.fh.model.DeptQuery;
import com.fh.service.DeptService;
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
public class DeptController {
    @Autowired
    private DeptService deptService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("deptselect")
    public ServerResponse deptselect(@RequestBody DeptQuery deptQuery){
        try {
            DataTableResult dataTableResult=deptService.deptselect(deptQuery);
            return ServerResponse.success(ResponseEnum.SUCCESS,dataTableResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }

    @RequestMapping("selectdept")
    public ServerResponse selectdept(){
        try {
            List<Dept> list=deptService.selectdept();
            return ServerResponse.success(ResponseEnum.SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("addDept")
    public ServerResponse addDept(Dept dept){
        try {
            deptService.addDept(dept);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("selectDeptById")
    public ServerResponse selectDeptById(String token){
        try {
            if(!redisTemplate.hasKey(token)){
                return ServerResponse.error(ResponseEnum.ERROR);
            }
            Integer id = (Integer) redisTemplate.opsForValue().get(token);
            Dept dept= deptService.selectDeptById(id);
            return ServerResponse.success(ResponseEnum.SUCCESS,dept);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("updateDept")
    public ServerResponse updateDept(Dept dept){
        try {
            deptService.updateDept(dept);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }

    @RequestMapping("deleteDept")
    public ServerResponse deleteDept(Integer id){
        try {
            deptService.deleteDept(id);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("deleteAllDept")
    public ServerResponse deleteAllDept(@RequestParam("ids[]") List<Integer> ids){
        try {
            deptService.deleteAllDept(ids);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }




}
