package com.tjetc.common;

import lombok.Getter;

@Getter
public class JsonResult<T> {
    private int state;
    private String message;
    private T data;

    //定义私有的构造方法，不允许new对象
    private JsonResult(int state, String message, T data) {
        this.state = state;
        this.message = message;
        this.data = data;
    }
    //下面的E和T仅是占位的
    public static <E> JsonResult success(String message,E data){
        return new JsonResult(0,message,data);
    }

    public static <T> JsonResult success(T data){
        return new JsonResult(0,null,data);
    }

    public static JsonResult fail(String message){
        return new JsonResult(0,message,null);
    }

    public static <T> JsonResult fail(String message,T data){
        return new JsonResult(0,message,data);
    }

    public static JsonResult fail(int state,String message){
        return new JsonResult(state,message,null);
    }
}
