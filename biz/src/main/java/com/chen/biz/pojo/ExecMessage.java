package com.chen.biz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 命令行提示信息类
 * @author danger
 * @date 2021/4/15
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecMessage {
    private String error;
    private String stdout;
}
