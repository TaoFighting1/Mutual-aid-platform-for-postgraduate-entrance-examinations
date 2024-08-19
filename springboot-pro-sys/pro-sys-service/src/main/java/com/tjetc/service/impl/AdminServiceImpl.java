package com.tjetc.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjetc.common.JsonResult;
import com.tjetc.common.JwtTokenUtil;
import com.tjetc.common.vo.data.AdminView;
import com.tjetc.common.vo.param.AdminParam;
import com.tjetc.dao.AdminMapper;
import com.tjetc.entity.Admin;
import com.tjetc.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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
        AdminView adminView = new AdminView();
        BeanUtils.copyProperties(admin, adminView);
        return JsonResult.success(adminView);
    }

    @Override
    public JsonResult login(String username, String password) {
        // StringUtils.isAnyBlank 实参中任意一个为空，就返回true
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
            AdminView adminView = new AdminView();
            BeanUtils.copyProperties(admin,adminView);
            //token和admin对象全部给前端
            return JsonResult.success(token,adminView);
        }
    }

    @Override
    public JsonResult findPage(String username, Integer pageNo, Integer pageSize) {
        // 设置页码和每页数量
        Page<Admin> page = Page.of(pageNo,pageSize);
        // 把page作为分页参数，传入方法中
        Page<Admin> adminPage = adminMapper.selectPageLikeUsername(page,username);
//        page =adminMapper.selectPageLikeUsername(page,username);
        // convert方法转换Page 中records集合元素类型
        // new Function<Admin, AdminView>() {}; 表示 创建一个 实现Function接口的匿名类 对象
        IPage<AdminView> adminView = adminPage.convert(new Function<Admin, AdminView>() {
            public AdminView apply(Admin admin) {
                AdminView adminView = new AdminView();
                // 前提是两个对象属性名要保持一致
                BeanUtils.copyProperties(admin,adminView);
                return adminView;
            }
        });
        return JsonResult.success(page);
//        return JsonResult.success(adminPage);
    }

    @Override
    public JsonResult deleteById(Long id) {
        int rows = adminMapper.deleteById(id);
        if(rows > 0){
            return JsonResult.success("删除成功");
        }
        else{
            return JsonResult.fail(1,"删除失败");
        }
    }

    //@Transactional 用在service方法上，保证事务
    //注意：默认情况只对运行时异常起作用
    @Transactional
    @Override
    public JsonResult save(AdminParam adminParam) {
        Admin admin = new Admin();
        LocalDateTime now = LocalDateTime.now();
        //通过判断id值有无。判断是新增还是更新
        if(adminParam.getId()==null || adminParam.getId()<=0){
            //新增
            //判断用户名或者密码不能为空
            if(StringUtils.isAnyBlank(adminParam.getUsername(),adminParam.getPassword())){
                return JsonResult.fail("用户名或者密码不能为空");
            }
            //判断用户名不能重复
            Admin existAdmin = adminMapper.selectByUsername(adminParam.getUsername());
            if(existAdmin != null){
                return JsonResult.fail("用户名已经存在");
            }
            BeanUtils.copyProperties(adminParam,admin);
            admin.setCreateTime(now);
            admin.setUpdateTime(now);
            adminMapper.insert(admin);
            return JsonResult.success("新增成功",null);
        }else{
            //更新
            //根据id查询Admin信息
//            System.out.println();
            admin = adminMapper.selectById(adminParam.getId());
            if(admin == null){
                return JsonResult.fail("用户不存在，更新失败");
            }
            //要更新的字段
            admin.setImagePath(adminParam.getImagePath());
            admin.setUpdateTime(now);
            int rows = adminMapper.updateById(admin);
            //判断更新结果
            if(rows > 0){
                return JsonResult.success("更新成功",null);
            }else{
                return JsonResult.fail("更新失败");
            }

        }
    }





}
