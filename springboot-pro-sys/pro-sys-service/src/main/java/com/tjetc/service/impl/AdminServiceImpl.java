package com.tjetc.service.impl;

import com.tjetc.common.JsonResult;
import com.tjetc.common.JwtTokenUtil;
import com.tjetc.dao.AdminMapper;
import com.tjetc.entity.Admin;
import com.tjetc.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    @Value("${token.expiration}")
    private int tokenExpiration;
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public JsonResult findById(Long id) {
        // mybatisplus提供的单表查询操作
        Admin admin = adminMapper.selectById(id);
        return JsonResult.success(admin);
    }

    @Override
    public JsonResult login(String username, String password) {

        if(StringUtils.isAnyBlank(username,password)){
            return JsonResult.fail("用户名不能为空");
        }
        //根据用户名和密码查询
        Admin admin = adminMapper.selectByUsernameAndPassword(username,password);
        if(admin == null){
            return JsonResult.fail("用户名或者密码错误");
        } else {
            //用户名或者密码正确
            Map<String,Object> claims = new HashMap<>();
            claims.put("AdminId",admin.getId());
            String token = JwtTokenUtil.generateToken(claims, "admin", tokenExpiration);
            //token和admin对象全部给前端
            return JsonResult.success(token,admin);
        }
    }
}
