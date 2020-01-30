package com.fh.config;

import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterUtil implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest= (HttpServletRequest) request;
        HttpServletResponse servletResponse= (HttpServletResponse) response;
        String method = servletRequest.getMethod();

        //设置允许跨域的响应头
        servletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"http://localhost:8080");
        servletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"content-type,abc,token");
        servletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"GET, POST, PUT, HEAD, PATCH, DELETE, OPTIONS, TRACE");
        servletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,"true");
        if(!method.equalsIgnoreCase("options")){
            chain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}
