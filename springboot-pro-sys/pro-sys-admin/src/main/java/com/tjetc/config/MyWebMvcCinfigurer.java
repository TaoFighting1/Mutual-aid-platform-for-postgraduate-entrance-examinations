package com.tjetc.config;

import com.tjetc.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class MyWebMvcCinfigurer implements WebMvcConfigurer {
//    @Autowired
    private LoginInterceptor loginInterceptor;
    private String excludePaths[] = {
            "/login","/**/*.js","/image/**","/error"
    };

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePaths);
    }
}
