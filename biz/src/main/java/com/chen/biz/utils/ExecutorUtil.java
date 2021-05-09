package com.chen.biz.utils;

import com.chen.biz.pojo.ExecMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.*;

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
        String error = message(exec.getErrorStream());
        res.setError(error);
        if (!StringUtils.hasLength(error))
            res.setStdout(message(exec.getInputStream()));
        System.out.println("res = " + res);
        return res;
    }

    public static ExecMessage execWithInput(String cmd, String input) {
        log.info(cmd);
        Runtime runtime = Runtime.getRuntime();
        Process exec = null;
        try {
            exec = runtime.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
            return new ExecMessage(e.getMessage(), null);
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(exec.getOutputStream()));
        try {
            bw.write(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ExecMessage res = new ExecMessage();
        String error = message(exec.getErrorStream());
        res.setError(error);
        if (!StringUtils.hasLength(error))
            res.setStdout(message(exec.getInputStream()));
        System.out.println("res = " + res);
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
            reader = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
            StringBuilder message = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                message.append(str);
                message.append("\n");
            }
            reader.close();
            String result = message.toString();
            if (result.equals("")) {
                return null;
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
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
