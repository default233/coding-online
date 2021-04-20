package com.chen.biz.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author danger
 * @date 2021/4/15
 */
@TableName("exec_message")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecMessage {
    private String error;
    private String stdout;
}
