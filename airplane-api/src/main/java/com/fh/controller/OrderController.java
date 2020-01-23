package com.fh.controller;

import com.fh.model.OrderInfo;
import com.fh.model.PayLog;
import com.fh.model.User;
import com.fh.service.OrderService;
import com.fh.util.IdUtil;
import com.fh.util.ResponseEnum;
import com.fh.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
public class OrderController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OrderService orderService;
    @RequestMapping("inittoken")
    public ServerResponse inittoken(){
        try {//0代表手机，1代表邮箱
            Long aLong = IdUtil.createnoId();
            String token=aLong+"";
            redisTemplate.opsForHash().put(token,token,token);
            redisTemplate.expire(token,30, TimeUnit.MINUTES);
            return ServerResponse.success(ResponseEnum.SUCCESS,token);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }

    }


    @RequestMapping("addOrder")
    public ServerResponse addOrder(@RequestBody OrderInfo orderInfo,HttpServletRequest request){
        try {//0代表手机，1代表邮箱
            User user = (User) request.getAttribute("user");
            return orderService.addOrder(orderInfo,user.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }

    }

    @RequestMapping("selectOrderById")
    public ServerResponse selectOrderById(HttpServletRequest request){
        try {//0代表手机，1代表邮箱
            User user = (User) request.getAttribute("user");
            PayLog payLog = (PayLog) redisTemplate.opsForValue().get("paylog:" + user.getId());
            return orderService.selectOrderById(payLog.getOrderid());
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }

    }


    @RequestMapping("selectOrderItem")
    public ServerResponse selectOrderItem(String id){
        try {//0代表手机，1代表邮箱
            return orderService.selectOrderItem(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }

    }




}
