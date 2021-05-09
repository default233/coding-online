package com.chen.biz.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author danger
 * @date 2021/5/8
 */
@TableName("user_pass")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPass {
    @TableId(value = "user_pass_id")
    private Long userPassId;
    @TableField("user_id")
    private Long userId;
    @TableField("question_id")
    private Long questionId;
    @TableField("is_passed")
    private Boolean isPassed;

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
