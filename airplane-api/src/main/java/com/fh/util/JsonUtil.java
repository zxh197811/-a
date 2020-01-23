package com.fh.util;

import java.io.*;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	public static void outJson(HttpServletResponse response,Object object){
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
        ObjectMapper mapper=new ObjectMapper();
        try {
            PrintWriter writer = response.getWriter();
            String jsonStr = mapper.writeValueAsString(object);
            writer.print(jsonStr);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
