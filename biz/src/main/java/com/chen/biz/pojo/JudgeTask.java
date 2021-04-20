package com.chen.biz.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author danger
 * @date 2021/4/15
 */
@TableName("judge_task")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeTask {

    private String appName;

    private int submitId;

    private int compilerId;

    private int problemId;

    private String source;

    private int timeLimit;

    private int memoryLimit;

}
