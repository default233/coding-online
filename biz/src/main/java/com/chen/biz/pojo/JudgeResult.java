package com.chen.biz.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author danger
 * @date 2021/4/15
 */
@TableName("judge_result")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeResult {

    private Integer submitId;
    private Integer status;
    private Integer timeUsed;
    private Integer memoryUsed;
    private String errorMessage;

}
