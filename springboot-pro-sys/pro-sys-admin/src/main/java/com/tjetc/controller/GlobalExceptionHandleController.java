package com.tjetc.controller;


import com.tjetc.common.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
//@RestControllerAdvice=@ControllerAdvice+@ResponseBody
@Slf4j
public class GlobalExceptionHandleController {

    //有顺序，兜底处理
    @ExceptionHandler({Exception.class})
    public JsonResult Exception(Exception e){
        e.printStackTrace();
        log.error(e.toString());
        return JsonResult.fail("服务端异常异常");
    }
}
