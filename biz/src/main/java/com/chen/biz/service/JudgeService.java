package com.chen.biz.service;

import com.chen.biz.pojo.JudgeResult;
import com.chen.biz.pojo.JudgeTask;

/**
 * @author danger
 * @date 2021/4/15
 */
public interface JudgeService {
    JudgeResult judge(JudgeTask task);

    String compile(int compilerId, String path);
}
