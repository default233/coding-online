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

@TableName("question_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionType {

    @TableId(value = "question_type_id")
    private Long questionTypeId;
    @TableField(value = "question_type")
    private String questionType;
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
