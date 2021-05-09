package com.chen.biz.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author danger
 * @date 2021/5/8
 */
@TableName("question_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionStatus {

    @TableId(value = "question_status_id")
    private Long questionStatusId;
    @TableField(value = "question_id")
    private Long questionId;
    @TableField(value = "question_order")
    private Long questionOrder;
    @TableField(value = "question_title")
    private String title;
    @TableField(value = "question_type_id")
    private Long questionTypeId;
    @TableField(value = "question_difficulty")
    private String questionDifficulty;
    @TableField(value = "question_submit")
    private Integer questionSubmit;
    @TableField(value = "question_success")
    private Integer questionSuccess;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    @TableField(value = "is_delete", fill = FieldFill.INSERT)
    private Boolean isDelete;
    @TableField("description")
    private String description;

    public BigDecimal getPassRate() {
        BigDecimal submit = new BigDecimal(questionSubmit);
        if (submit.compareTo(new BigDecimal(1)) == 0)
            return submit.setScale(2, BigDecimal.ROUND_HALF_UP);
        else {
            BigDecimal success = new BigDecimal(questionSuccess);
            BigDecimal passRate = success.divide(submit,2, BigDecimal.ROUND_HALF_UP);
            return passRate.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }
}
