package com.fh.util;

public class ServerResponse {

    private int code;

    private String name;

    private Object data;

    private Object ticket;

    public ServerResponse(int code, String name, Object data, Object ticket) {
        this.code = code;
        this.name = name;
        this.data = data;
        this.ticket = ticket;
    }
/* public static ServerResponse success(Object o){
        return  new ServerResponse(200,"请求成功",o);

    }

    public static ServerResponse success(){
        return new ServerResponse(200,"请求成功",null);
    }

    public static ServerResponse error(){
        return new ServerResponse(500,"请求失败",null);
    }
*/


    public static ServerResponse success(ResponseEnum responseEnum,Object o){
        return  new ServerResponse(responseEnum.getCode(),responseEnum.getMessage(),o);

    }

    public static ServerResponse success(ResponseEnum responseEnum,Object o,Object o1){
        return  new ServerResponse(responseEnum.getCode(),responseEnum.getMessage(),o,o1);

    }

    public static ServerResponse success(ResponseEnum responseEnum){
        return new ServerResponse(responseEnum.getCode(),responseEnum.getMessage(),null);
    }

    public static ServerResponse error(ResponseEnum responseEnum){
        return new ServerResponse(responseEnum.getCode(),responseEnum.getMessage(),null);
    }

    public static ServerResponse error(Integer code,String message){
        return new ServerResponse(code,message,null);
    }

    private ServerResponse(int code, String name, Object data) {
        this.code = code;
        this.name = name;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
