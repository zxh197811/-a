package com.fh.controller;

import com.fh.model.Permission;
import com.fh.service.PermissionService;
import com.fh.util.ResponseEnum;
import com.fh.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("selectPermission")
    public ServerResponse selectPermission(){
        try {
            List<Permission> list=permissionService.selectPermission();
            return ServerResponse.success(ResponseEnum.SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("addPermission")
    public ServerResponse addPermission(Permission permission){
        try {
            permissionService.addPermission(permission);
            return ServerResponse.success(ResponseEnum.SUCCESS,permission);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("selectPermissionById")
    public ServerResponse selectPermissionById(Integer id){
        try {
            Permission permission= permissionService.selectPermissionById(id);
            return ServerResponse.success(ResponseEnum.SUCCESS,permission);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("updatePermission")
    public ServerResponse updatePermission(Permission permission){
        try {
            permissionService.updatePermission(permission);
            return ServerResponse.success(ResponseEnum.SUCCESS,permission);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }



    @RequestMapping("deleteAllPermission")
    public ServerResponse deleteAllPermission(@RequestParam("ids[]") List<Integer> ids){
        try {
            permissionService.deleteAllPermission(ids);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }





}
