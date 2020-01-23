package com.fh.interceptor;


import com.fh.util.JsonUtil;
import com.fh.util.ResponseEnum;
import com.fh.util.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MiInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String abc = request.getHeader("abc");
        if(StringUtils.isBlank(abc)){
            JsonUtil.outJson(response, ServerResponse.error(ResponseEnum.ERROR));
            return false;
        }

             if(!redisTemplate.hasKey(abc)){
                 JsonUtil.outJson(response, ServerResponse.error(ResponseEnum.ERROR));
                 return false;
             }

             if(redisTemplate.opsForHash().delete(abc,abc)<0){
                 JsonUtil.outJson(response, ServerResponse.error(ResponseEnum.ERROR));
                 return false;
             }


        return true;
    }
}
