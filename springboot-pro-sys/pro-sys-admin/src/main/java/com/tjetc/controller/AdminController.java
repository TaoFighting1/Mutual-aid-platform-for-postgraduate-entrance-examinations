package com.tjetc.controller;
import com.tjetc.common.FileUploadUtil;
import com.tjetc.common.JsonResult;
import com.tjetc.common.vo.param.AdminParam;
import com.tjetc.entity.Admin;
import com.tjetc.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Value("${file.basePath}")
    private String fileBasePath;

    @RequestMapping("detail/{id}")
    public JsonResult detail(@PathVariable("id") Long id) {
        return adminService.findById(id);
    }

    @RequestMapping("page")
    public JsonResult page(@RequestParam(value = "username",required = false,defaultValue = "") String username,
                           @RequestParam(value = "pageNo",required = false,defaultValue = "1") Integer pageNo,
                           @RequestParam(value = "pageSize",required = false,defaultValue = "2") Integer pageSize){
        return adminService.findPage(username,pageNo,pageSize);
    }

    @RequestMapping("delete/{id}")
    public JsonResult delete(@PathVariable("id") Long id) {
        return adminService.deleteById(id);
    }

    @RequestMapping("upload/image")
    public JsonResult uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
        String imagePath = FileUploadUtil.upload(imageFile, fileBasePath, "image");
        return JsonResult.success(imagePath);
    }

    @RequestMapping("save")
    public JsonResult save(@RequestBody AdminParam adminParam) {
        return adminService.save(adminParam);
    }
}
