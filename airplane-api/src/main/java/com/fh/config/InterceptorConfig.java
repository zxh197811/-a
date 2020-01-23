package com.fh.config;

import com.fh.interceptor.LoginInterceptor;
import com.fh.interceptor.MiInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;
   @Autowired
    private MiInterceptor miInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
           registry.addInterceptor(loginInterceptor)
                   .addPathPatterns("/*")
                   .excludePathPatterns("/selectType","/selectCity",
                           "/dl","/pageselect");

      registry.addInterceptor(miInterceptor)
                .addPathPatterns("/addOrder");


    }
}
