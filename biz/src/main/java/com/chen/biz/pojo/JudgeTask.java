package com.chen.biz.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 判题任务实体类
 * @author danger
 * @date 2021/4/15
 */
@TableName("judge_task")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeTask {

    @TableId(value = "judge_task_id")
    private Long judgeTaskId;

    @TableField(value = "question_id")
    private Long questionId;

    @TableField(value = "compiler_id")
    private Integer compilerId;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "source")
    private String source;

    @TableField(value = "time_limit")
    private Integer timeLimit;
    @TableField(value = "memory_limit")
    private Integer memoryLimit;
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
