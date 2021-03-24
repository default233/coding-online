package com.chen.biz.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户 SysUser 实体类
 * @author danger
 * @date 2021/3/9
 */
@TableName("sys_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {
    @TableId(value = "user_id", type = IdType.ASSIGN_UUID)
    private Long userId;
    @TableField("username")
    private String username;
    @TableField("email")
    private String email;
    @TableField("password")
    private String password;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField("is_delete")
    private Boolean isDelete;
    @TableField("description")
    private String description;

}
