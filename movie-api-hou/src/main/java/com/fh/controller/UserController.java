package com.fh.controller;

import com.fh.model.User;
import com.fh.model.UserQuery;
import com.fh.model.UserQuery2;
import com.fh.service.UserService;
import com.fh.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("selectuser")
    public ServerResponse selectuser(@RequestBody UserQuery userQuery){
        try {
            DataTableResult dataTableResult= userService.selectuser(userQuery);
            return ServerResponse.success(ResponseEnum.SUCCESS,dataTableResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("addUser")
    public ServerResponse addUser(User user){
        try {
            userService.addUser(user);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("selectUserById")
    public ServerResponse selectUserById(String token){
        try {
            if(!redisTemplate.hasKey(token)){
                return ServerResponse.error(ResponseEnum.ERROR);
            }
            Integer id = (Integer) redisTemplate.opsForValue().get(token);
            User user=userService.selectUserById(id);
            return ServerResponse.success(ResponseEnum.SUCCESS,user);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("updateUser")
    public ServerResponse updateUser(User user){
        try {
            User user1 = userService.selectUserById(user.getId());
            if(StringUtils.isNotBlank(user1.getFilename())&&!user1.getFilename().equals(user.getFilename())){
                AliOssUtil.deleteimage(user1.getFilename());
            }
            userService.updateUser(user);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }

    @RequestMapping("deleteUser")
    public ServerResponse deleteUser(Integer id){
        try {
            User user = userService.selectUserById(id);
            if(StringUtils.isNotBlank(user.getFilename())){
                AliOssUtil.deleteimage(user.getFilename());
            }
            userService.deleteUser(id);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("deleteAllUser")
    public ServerResponse deleteAllUser(@RequestParam("ids[]") List<Integer> ids){
        try {
            for (Integer id : ids) {
                User user = userService.selectUserById(id);
                if(StringUtils.isNotBlank(user.getFilename())){
                   AliOssUtil.deleteimage(user.getFilename());
                }
            }
            userService.deleteAllUser(ids);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }

    @RequestMapping("uploadUserImage")
    public ServerResponse uploadUserImage(MultipartFile image){
        try {
            String images = AliOssUtil.uploadimage(image.getInputStream(), image.getOriginalFilename(), "images");
            return ServerResponse.success(ResponseEnum.SUCCESS,images);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }

    @RequestMapping("importExcel")
    public ServerResponse importExcel(MultipartFile excel){
        try {
            String[]filedarr={"name","realname","deptid","sex","email","phonenumber","filename","birthday","createDate","updateDate"};
            List<User> importexcel = ExcelUtil.importexcel(excel.getInputStream(), User.class, filedarr);
            for (User user : importexcel) {
                userService.addUser(user);
            }
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }

    @RequestMapping("exportExcel")
    public void exportExcel(UserQuery2 userQuery, HttpServletRequest request, HttpServletResponse response){
        try {
            DataTableResult selectuser = userService.selectuser(userQuery);
            String[]filedarr={"name","realname","deptid","sex","email","phonenumber","filename","birthday","createDate","updateDate"};
            String[]headarr={"用户名","真实姓名","部门","性别","邮箱","手机号","图片名","生日","创建日期","修改日期"};
            XSSFWorkbook workbook = ExcelUtil.generateWorkBook(selectuser.getData(), User.class, headarr, filedarr, 
                    "用户列表");
            ExcelUtil.excelDownload(workbook,request,response, UUID.randomUUID().toString()+".xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
