package com.chen.biz.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author danger
 * @date 2021/4/16
 */
@TableName("question")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @TableId(value = "question_id")
    private Long questionId;

    @TableField(value = "question_order")
    private Long questionOrder;

    @TableField(value = "question_title")
    private String title;

    @TableField(value = "question_type_id")
    private Long questionTypeId;

    @TableField(value = "question_difficulty")
    private String questionDifficulty;

    @TableField(value = "question_description")
    private String questionDescription;

    @TableField(value = "input_description")
    private String inputDescription;

    @TableField(value = "output_description")
    private String outputDescription;

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
