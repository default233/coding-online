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
@TableName("output_example")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputExample {
    @TableId(value = "output_example_id")
    private Long outputExampleId;

    @TableField(value = "question_id")
    private Long questionId;

    @TableField(value = "output_example")
    private String outputExample;

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
