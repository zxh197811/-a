package com.fh.service.impl;

import com.fh.mapper.OrderMapper;
import com.fh.mapper.PayLogMapper;
import com.fh.model.Order;
import com.fh.model.PayLog;
import com.fh.service.PayService;
import com.fh.util.ResponseEnum;
import com.fh.util.ServerResponse;
import com.github.wxpay.sdk.MyWxConfig;
import com.github.wxpay.sdk.WXPay;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayServiceImpl implements PayService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired(required = false)
    private OrderMapper orderMapper;
    @Autowired(required = false)
    private PayLogMapper payLogMapper;


    @Override
    public ServerResponse updatepay(Integer id) {
        if(!redisTemplate.hasKey("paylog:"+id)){
            return ServerResponse.error(ResponseEnum.ERROR);
        }
        PayLog payLog = (PayLog) redisTemplate.opsForValue().get("paylog:" + id);
        try {
            WXPay wxPay=new WXPay(new MyWxConfig());
            Map<String,String> paramMap=new HashMap<>();
            paramMap.put("body","天才商城支付订单");
            paramMap.put("out_trade_no",payLog.getOuttradeno());
            /*paramMap.put("total_fee",payLog.getPayMoney().multiply(new BigDecimal("100")).intValue()+"");*/
            paramMap.put("total_fee","1");
            paramMap.put("spbill_create_ip","127.0.0.1");
            //将预支付订单是失效时间设为在系统当前时间上加5分钟
//            paramMap.put("time_expire",sdf.format(new Date().getTime() + 5 * 60 * 1000));
            SimpleDateFormat sim=new SimpleDateFormat("yyyyMMddHHmmss");
            paramMap.put("time_expire", sim.format(DateUtils.addMinutes(new Date(),2)));
            paramMap.put("notify_url", "http://www.baidu.com");
            paramMap.put("trade_type", "NATIVE");
            Map<String, String> resultmap = wxPay.unifiedOrder(paramMap);
             if(!resultmap.get("return_code").equalsIgnoreCase("success")){
                 return ServerResponse.error(123456,resultmap.get("return_msg"));
             }
            if(!resultmap.get("result_code").equalsIgnoreCase("success")){
                return ServerResponse.error(123456,resultmap.get("err_code_des"));
            }

           return ServerResponse.success(ResponseEnum.SUCCESS,resultmap.get("code_url"));

        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(ResponseEnum.ERROR);
        }



    }


    @Override
    public ServerResponse queryPayStatus(Integer id) {
        //先判断当前登录会员是否有支付日志
        if(!redisTemplate.hasKey("paylog:" + id)){
            return ServerResponse.error(ResponseEnum.ERROR);
        }

        //从redis中取出支付日志
        PayLog payLog = (PayLog) redisTemplate.opsForValue().get("paylog:" + id);

        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("out_trade_no",payLog.getOuttradeno());
        try {
                WXPay wxPay = new WXPay(new MyWxConfig());
                Map<String, String> resultMap = wxPay.orderQuery(paramMap);
                if(!resultMap.get("return_code").equalsIgnoreCase("success")){
                    return ServerResponse.error(123456,"获取支付状态失败，错误信息为："+resultMap.get("return_msg"));
                }
                if(!resultMap.get("result_code").equalsIgnoreCase("success")){
                    return ServerResponse.error(123456,"获取支付状态失败，错误信息为："+resultMap.get("err_code_des"));
                }
                if(resultMap.get("trade_state").equalsIgnoreCase("success")){

                    Order order = new Order();
                    order.setId(payLog.getOrderid());
                    order.setPaytime(new Date());
                    order.setStatus(2);
                    orderMapper.updateById(order);



                    payLog.setPaystatus(2);
                    payLog.setTransactionid(resultMap.get("transaction_id"));

                    payLogMapper.insert(payLog);

                    //清空用户支付日志
                    redisTemplate.delete("paylog:" + id);

                    return ServerResponse.success(ResponseEnum.SUCCESS,payLog.getOuttradeno());
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.error(ResponseEnum.ERROR);
    }

    @Override
    public ServerResponse selectPayMoney(String outtradeno) {
        PayLog payLog = payLogMapper.selectById(outtradeno);
        return ServerResponse.success(ResponseEnum.SUCCESS,payLog.getPaymoney());
    }
}
