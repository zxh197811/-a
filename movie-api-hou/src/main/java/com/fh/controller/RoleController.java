package com.fh.controller;

import com.fh.model.Dept;
import com.fh.model.DeptQuery;
import com.fh.model.Role;
import com.fh.model.RoleQuery;
import com.fh.service.RoleService;
import com.fh.util.DataTableResult;
import com.fh.util.ResponseEnum;
import com.fh.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("selectRole")
    public ServerResponse selectRole(@RequestBody RoleQuery roleQuery){
        try {
            DataTableResult dataTableResult=roleService.selectRole(roleQuery);
            return ServerResponse.success(ResponseEnum.SUCCESS,dataTableResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }

    @RequestMapping("roleselect")
    public ServerResponse roleselect(){
        try {
            List<Role> roleList=roleService.roleselect();
            return ServerResponse.success(ResponseEnum.SUCCESS,roleList);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("addRole")
    public ServerResponse addRole(Role role){
        try {
            roleService.addRole(role);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("selectRoleById")
    public ServerResponse selectRoleById(String token){
        try {
            if(!redisTemplate.hasKey(token)){
                return ServerResponse.error(ResponseEnum.ERROR);
            }
            Integer id = (Integer) redisTemplate.opsForValue().get(token);
            Role role= roleService.selectRoleById(id);
            return ServerResponse.success(ResponseEnum.SUCCESS,role);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("updateRole")
    public ServerResponse updateRole(Role role){
        try {
            roleService.updateRole(role);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }

    @RequestMapping("deleteRole")
    public ServerResponse deleteRole(Integer id){
        try {
            roleService.deleteRole(id);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("deleteAllRole")
    public ServerResponse deleteAllRole(@RequestParam("ids[]") List<Integer> ids){
        try {
            roleService.deleteAllRole(ids);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }

    @RequestMapping("updateStatus")
    public ServerResponse updateStatus(Integer id){
        try {
            roleService.updateStatus(id);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }



}
