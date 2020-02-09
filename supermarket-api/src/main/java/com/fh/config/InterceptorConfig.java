package com.fh.config;

import com.fh.util.MiInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Autowired
    private MiInterceptor miInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
           /*registry.addInterceptor(loginInterceptor)
                   .addPathPatterns("/*")
                   .excludePathPatterns("/productIsHot",
                           "/category","/yz","/phone","/name","/member/register","/dl"
                   ,"/member/query","/checkCodeServlet");*/

        registry.addInterceptor(miInterceptor)
                .addPathPatterns("/updateStock");


    }
}
