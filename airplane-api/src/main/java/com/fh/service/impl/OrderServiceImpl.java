package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fh.mapper.OrderItemMapper;
import com.fh.mapper.OrderMapper;
import com.fh.mapper.TicketMapper;
import com.fh.model.*;
import com.fh.service.OrderService;
import com.fh.util.IdUtil;
import com.fh.util.ResponseEnum;
import com.fh.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired(required = false)
    private OrderMapper orderMapper;
    @Autowired(required = false)
    private OrderItemMapper orderItemMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired(required = false)
    private TicketMapper ticketMapper;

    @Override
    public ServerResponse addOrder(OrderInfo orderInfo,Integer userid) {
        List<OrderItem> orderItemList = orderInfo.getOrderItemList();
        List<OrderItem> notenoughTicketList=new ArrayList<>();
        List<OrderItem> ticketList=new ArrayList<>();
        Long totalcount=0L;
        BigDecimal totalprice=new BigDecimal("0");
        for (OrderItem orderItem : orderItemList) {
            Ticket ticket = ticketMapper.selectById(orderItem.getTicketid());
            if(ticket.getTotalcount()<1){
                notenoughTicketList.add(orderItem);
            }else{
                UpdateWrapper updateWrapper=new UpdateWrapper();
                updateWrapper.eq("id",orderItem.getTicketid());
                updateWrapper.ge("totalcount",1);
                ticket.setTotalcount(ticket.getTotalcount()-1);
                int update = ticketMapper.update(ticket, updateWrapper);
                if(update==0){
                    notenoughTicketList.add(orderItem);
                }else{
                    ticketList.add(orderItem);
                    totalcount++;
                    totalprice=totalprice.add(orderItem.getPrice());
                }

            }
        }
        if(notenoughTicketList.size()==orderItemList.size()){
            return ServerResponse.success(ResponseEnum.SUCCESS,notenoughTicketList);
        }
        String orderid = IdUtil.createId();
        Order order=new Order();
        order.setUserid(userid);
        order.setCreatetime(new Date());
        order.setPaytype(1);
        order.setTotalprice(totalprice);
        order.setStatus(1);
        order.setId(orderid);
        order.setTotalcount(totalcount);
        order.setFlightid(orderInfo.getFlightid());
        orderMapper.insert(order);


        for (OrderItem orderItem : ticketList) {
            orderItem.setUserid(userid);
            orderItem.setOrderid(orderid);
            orderItemMapper.insert(orderItem);
        }


        PayLog payLog=new PayLog();
        payLog.setUserid(userid);
        payLog.setPaytype(1);
        payLog.setPaystatus(1);
        payLog.setPaymoney(totalprice);
        payLog.setOrderid(orderid);
        payLog.setOuttradeno(IdUtil.createId());
        redisTemplate.opsForValue().set("paylog:"+userid,payLog,30, TimeUnit.MINUTES);

        return ServerResponse.success(ResponseEnum.SUCCESS,notenoughTicketList);
    }


    @Override
    public ServerResponse selectOrderById(String orderid) {
        Order order = orderMapper.selectById(orderid);
        return ServerResponse.success(ResponseEnum.SUCCESS,order);
    }


    @Override
    public ServerResponse selectOrderItem(String id) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("orderid",id);
        List<OrderItem> list = orderItemMapper.selectList(queryWrapper);
        return ServerResponse.success(ResponseEnum.SUCCESS,list);
    }
}
