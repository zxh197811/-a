package com.fh.controller;

import com.fh.model.User;
import com.fh.service.UserService;
import com.fh.util.ResponseEnum;
import com.fh.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
           @Autowired
           private UserService userService;

          @RequestMapping("dl")
    public ServerResponse dl(String name,String password){
              try {
                  return userService.dl(name,password);
              } catch (Exception e) {
                  e.printStackTrace();
                  return ServerResponse.error(ResponseEnum.ERROR);
              }



          }

    @RequestMapping("isdl")
    public ServerResponse isdl(HttpServletRequest request){
        try {
            User user = (User) request.getAttribute("user");
            if(user==null){
                return ServerResponse.error(ResponseEnum.ERROR);
            }
            return ServerResponse.success(ResponseEnum.SUCCESS,user.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }



    }




}
