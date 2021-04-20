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
@TableName("input_example")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputExample {

    @TableId(value = "input_example_id")
    private Long inputExampleId;

    @TableField(value = "question_id")
    private Long questionId;

    @TableField(value = "input_example")
    private String inputExample;

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
