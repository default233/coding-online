package com.chen.biz.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author danger
 * @date 2021/5/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeInformation {
    @TableId(value = "question_type_id")
    private Long questionTypeId;
    @TableField(value = "question_type")
    private String questionType;
    @TableField(value = "problem_nums")
    private Integer problemNums;
}
