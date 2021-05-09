package com.chen.biz.compiler;

import com.chen.biz.pojo.ExecMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 执行命令行工具类
 * @author danger
 * @date 2021/4/15
 */
@Slf4j
public class ExecutorUtil {

    /**
     * 执行命令行并返回信息
     * @param cmd 待执行的命令
     * @return 执行信息
     */
    public static ExecMessage exec(String cmd) {
        log.info(cmd);
        Runtime runtime = Runtime.getRuntime();
        Process exec = null;
        try {
            exec = runtime.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
            return new ExecMessage(e.getMessage(), null);
        }
        ExecMessage res = new ExecMessage();
        res.setError(message(exec.getErrorStream()));
        res.setStdout(message(exec.getInputStream()));
        return res;
    }


    /**
     * 用于将命令行提示转为字符串
     * @param inputStream 流信息
     * @return 信息字符串
     */
    private static String message(InputStream inputStream) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            StringBuilder message = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                message.append(str);
            }
            String result = message.toString();
            if (result.equals("")) {
                return null;
            }
            return result;
        } catch (IOException e) {
            return e.getMessage();
        } finally {
            try {
                inputStream.close();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
