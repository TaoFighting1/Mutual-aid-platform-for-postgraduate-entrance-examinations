package com.tjetc.service;

import com.tjetc.common.JsonResult;

public interface AdminService {
    JsonResult findById(Long id);
    JsonResult login(String username, String password);
}
