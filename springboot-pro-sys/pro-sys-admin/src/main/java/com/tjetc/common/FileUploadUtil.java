package com.tjetc.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public final class FileUploadUtil {
    public static String upload(MultipartFile multipartFile,String basePath,
                                String returnPathPrefix) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        String strLocalDateNow = dtf.format(now);
        basePath = basePath.endsWith("/") ? basePath : basePath + "/";
        basePath = basePath + returnPathPrefix +"/"+ strLocalDateNow;
        File file = new File(basePath);
        if(!file.exists()){
            file.mkdirs();
        }
        String originFilename = multipartFile.getOriginalFilename();
        int lastIndexOf = originFilename.lastIndexOf(".");
        String suffxName = originFilename.substring(lastIndexOf + 1);
        String newFileName = UUID.randomUUID().toString().replaceAll("-","");
        String newFileNamePath = basePath +"/"+newFileName + "." + suffxName;
        File newNamePathFile = new File(newFileNamePath);
        try {
            multipartFile.transferTo(newNamePathFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "/"+returnPathPrefix+"/"+strLocalDateNow+"/"+newFileName+"/"+suffxName;
    }
}
