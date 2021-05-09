package com.chen.biz.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author danger
 * @date 2021/4/15
 */
@TableName("judge_stdout")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stdout {
    private Integer status;
    private Long maxMemory;
    private Long maxTime;
}
