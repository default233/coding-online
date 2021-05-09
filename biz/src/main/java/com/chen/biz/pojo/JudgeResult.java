package com.chen.biz.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 判题结果实体类
 * @author danger
 * @date 2021/4/15
 */
@TableName("judge_result")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeResult {
    @TableId(value = "judge_result_id")
    private Long judgeResultId;
    @TableField(value = "judge_task_id")
    private Long judgeTaskId;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "status")
    private Integer status;
    @TableField(value = "time_used")
    private Integer timeUsed;
    @TableField(value = "memory_used")
    private Integer memoryUsed;
    @TableField(value = "error_message")
    private String errorMessage;
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
