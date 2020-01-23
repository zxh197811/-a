package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.mapper.UserMapper;
import com.fh.model.User;
import com.fh.service.UserService;
import com.fh.util.JwtUtil;
import com.fh.util.ResponseEnum;
import com.fh.util.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public ServerResponse dl(String name, String password) {
        if(StringUtils.isBlank(name)){
            return ServerResponse.error(ResponseEnum.ERROR);
        }
        if(StringUtils.isBlank(password)){
            return ServerResponse.error(ResponseEnum.ERROR);
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",name);
        User user = userMapper.selectOne(queryWrapper);
        if(user==null){
            return ServerResponse.error(ResponseEnum.ERROR);
        }
        if(!user.getPassword().equals(password)){
            return ServerResponse.error(ResponseEnum.ERROR);
        }
        User user1=new User();
        user1.setName(user.getName());
        user1.setId(user.getId());
        user1.setUuid(UUID.randomUUID().toString());
        String token = JwtUtil.createToken(user1);
        redisTemplate.opsForValue().set("user:"+user.getId(),123,30, TimeUnit.MINUTES);
        return ServerResponse.success(ResponseEnum.SUCCESS,token);
    }
}
