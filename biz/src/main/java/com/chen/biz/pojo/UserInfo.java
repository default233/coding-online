package com.chen.biz.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author danger
 * @date 2021/4/21
 */
@TableName("user_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @TableId(value = "user_info_id")
    private Long userInfoId;
    @TableField("user_id")
    private Long userId;
    @TableField("username")
    private String username;
    @TableField("email")
    private String email;
    @TableField("sex")
    private String sex;
    @TableField("img")
    private String img;
    @TableField(value = "birthday")
    private LocalDate birthday;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    @TableField(value = "is_delete", fill = FieldFill.INSERT)
    private Boolean isDelete;
    @TableField("description")
    private String description;
}
