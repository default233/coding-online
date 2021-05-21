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
public class ClassInformation {
    @TableId(value = "class_id")
    private Long classId;
    @TableField(value = "class_name")
    private String className;
    @TableField(value = "student_nums")
    private Integer studentNums;
}
