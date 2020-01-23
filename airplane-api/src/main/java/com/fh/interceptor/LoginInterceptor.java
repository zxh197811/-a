package com.fh.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.model.User;
import com.fh.util.JsonUtil;
import com.fh.util.JwtUtil;
import com.fh.util.ResponseEnum;
import com.fh.util.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(StringUtils.isBlank(token)){
            JsonUtil.outJson(response, ServerResponse.error(ResponseEnum.TOKEN_IS_NULL));
            return false;
        }
        String[] tokenarr = token.split("\\.");
        if(tokenarr.length<3){
            return false;
        }
        /*String nokeybase=tokenarr[0];
        String keybase=tokenarr[1];
        String key="dKskJsd23#3$%!~CS32*";
        String md5Hex = MD5Util.md5Hex(nokeybase + key);
        String newkeybase = Base64.getEncoder().encodeToString(md5Hex.getBytes());
        if(!newkeybase.equals(keybase)){
            return false;
        }
         String memberstr = new String(Base64.getDecoder().decode(nokeybase));
        */
        String memberjsonstr = null;
        try {
            memberjsonstr = JwtUtil.verifyToken(token);
        } catch (Exception e) {
            JsonUtil.outJson(response, ServerResponse.error(ResponseEnum.TOKEN_IS_NULL));
            return false;
        }
        User member = JSONObject.parseObject(memberjsonstr,User.class);
        if(!redisTemplate.hasKey("user:"+member.getId())){
            JsonUtil.outJson(response, ServerResponse.error(ResponseEnum.TOKEN_IS_NULL));
            return false;
        }
        redisTemplate.expire("user:"+member.getId(),30, TimeUnit.MINUTES);
        request.setAttribute("user",member);
        return true;
    }
}
