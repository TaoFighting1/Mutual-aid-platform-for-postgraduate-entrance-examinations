package com.tjetc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_admin")
public class Admin {
    //@TableId 对表主键字段与属性映射，value是表示字段名称，type是主键类型
    @TableId(value="id",type= IdType.AUTO)
    private Long id;
    @TableField(value = "username")
    private String username;
    private String password;
    @TableField("image_path")
    private String imagePath;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;

}
