package com.fh.controller;

import com.fh.model.User;
import com.fh.service.PayService;
import com.fh.util.ResponseEnum;
import com.fh.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PayController {
    @Autowired
    private PayService payService;



    @RequestMapping("pay")
    public ServerResponse pay(HttpServletRequest request){
        try {
            User user = (User) request.getAttribute("user");
            if(user==null){
                return ServerResponse.error(ResponseEnum.ERROR);
            }
            return payService.updatepay(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }


    }


    @RequestMapping("queryPayStatus")
    public ServerResponse queryPayStatus(HttpServletRequest request){
        try {
            User user = (User) request.getAttribute("user");
            if(user==null){
                return ServerResponse.error(ResponseEnum.ERROR);
            }
            ServerResponse serverResponse = payService.queryPayStatus(user.getId());
            return serverResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }


    @RequestMapping("selectPayMoney")
    public ServerResponse selectPayMoney(String outtradeno){
        try {
            ServerResponse serverResponse = payService.selectPayMoney(outtradeno);
            return serverResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }
    }









}
