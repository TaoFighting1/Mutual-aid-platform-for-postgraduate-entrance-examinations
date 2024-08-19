package com.tjetc.common.vo.param;

import lombok.Data;

//用于接收前端传给controller方法的参数
@Data
public class AdminParam {
    private Long id;
    private String username;
    private String password;
    private String imagePath;
}
