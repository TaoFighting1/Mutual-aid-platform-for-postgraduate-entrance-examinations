package com.tjetc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjetc.entity.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {
    Admin selectByUsernameAndPassword(@Param("username")String username,
                                      @Param("password")String password);
    Page<Admin> selectPageLikeUsername(Page<Admin> pageAdmin,@Param("username") String username);

    Admin selectByUsername(@Param("username")String username);
}
