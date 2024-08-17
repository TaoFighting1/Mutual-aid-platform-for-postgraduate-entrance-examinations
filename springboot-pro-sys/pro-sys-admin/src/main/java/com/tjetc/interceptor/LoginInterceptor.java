package com.tjetc.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjetc.common.JsonResult;
import com.tjetc.common.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        HttpSession session = request.getSession();
//        Object user = session.getAttribute("username");
//        if(user==null){
//            String result = "{\"code\":2000,\"msg\":\"未登录\"}";
//            response.setHeader("Content-Type", "application/json;charset=utf-8");
//            response.getWriter().print(result);
//            return false;
//        }
        //校验token
        //获取token（请求头中的token
        String tokenValue = request.getHeader("token");
        if(tokenValue!=null && !"".equals(tokenValue.trim())){
            try{
                JwtTokenUtil.parseJwt(tokenValue);
                return true;
            }catch(Exception e){
                log.error("解析token异常，{}",e.getMessage());
            }
        }
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        JsonResult result = JsonResult.fail(-1,"未登录或者登录过期");
        //将对象转为静态方法
        String json = objectMapper.writeValueAsString(result);
        response.getWriter().write(json);
        return false;
    }
}
