package com.tjetc.service;

import com.tjetc.common.JsonResult;
import com.tjetc.common.vo.param.AdminParam;

public interface AdminService {
    JsonResult findById(Long id);
    JsonResult login(String username, String password);
    JsonResult findPage(String username,Integer pageNo,Integer pageSize);
    JsonResult deleteById(Long id);
    JsonResult save(AdminParam adminParam);
}
