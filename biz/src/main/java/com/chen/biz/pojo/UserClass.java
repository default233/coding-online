package com.chen.biz.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author danger
 * @date 2021/5/13
 */
@TableName("user_class")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserClass {
    @TableId(value = "user_class_id")
    private Long userClassId;
    @TableField("user_id")
    private Long userId;
    @TableField("real_name")
    private String realName;
    @TableField("student_id")
    private String studentId;
    @TableField("class_id")
    private Long classId;
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
