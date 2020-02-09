package com.fh.util;

public enum  ResponseEnum {

    SUCCESS(200,"请求成功"),
    ERROR(500,"请求失败"),
    OBJECT_NULL(501,"对象为空"),
    YZM_NULL(502,"验证码为空"),
    USERNAME_NULL(503,"用户名为空"),
    PASS_NULL(504,"密码为空"),
    PHONE_NULL(505,"手机号为空"),
    YZM_ERROR(506,"验证码错误"),
    USER_EXIST(507,"用户名已存在"),
    PHONE_EXIST(508,"手机号已存在"),
    SMS_ERROR(509,"短信发送失败"),
    EMAIL_NULL(510,"邮箱为空"),
    NICKNAME_NULL(511,"昵称为空"),
    CODE_NULL(512,"验证码失效"),
    TOKEN_IS_NULL(513,"token为空"),
    STOCK_IS_ALL_NULL(515,"库存全部为空"),
    CART_NULL(514,"购物车为空"),
   ALL_STOCK_IS_NOT_ENOUGH(516,"购物车中所有商品库存不足");
    ;
    private Integer code;

    private String message;


    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
