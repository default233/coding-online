package com.chen.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chen.biz.compiler.CmdStrings;
import com.chen.biz.pojo.*;
import com.chen.biz.service.*;
import com.chen.biz.utils.ExecutorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author danger
 * @date 2021/4/15
 */
@Slf4j
@Service
public class JudgeServiceImpl implements JudgeService {

//    @Value("${workspace}")
//    private String workspace;
//
//    @Value("${judge_data}")
//    private String judgeData;
//
//    @Value("${judge_script}")
//    private String judgeScript;
//
//    @Value("${dangerousKeys}")
//    private String dangerousKeys;
//    @Value("${gccAddition}")
//    private String gccAddition;
//    @Value("${g++Addition}")
//    private String gAddition;
//    @Value("${python_cacheName}")
//    private String pythonCacheName;

    @Value("${compile.filepath}")
    private String CompileFilePath;
    @Value("${compile.relativePath}")
    private String relativePath;

    @Value("${compile.dangerousKeyWords}")
    private String dangerousKeyWords;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionStatusService questionStatusService;

    @Autowired
    private JudgeResultService judgeResultService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserPassService userPassService;

    @Autowired
    private OutputExampleService outputExampleService;

    @Autowired
    private InputExampleService inputExampleService;

    @Override
    @Transactional
    public JudgeResult judge(JudgeTask task) {
        // 用于存储运行结果
        JudgeResult result = new JudgeResult();
        // 设置运行结果的判题 id 和用户 id
        result.setJudgeTaskId(task.getJudgeTaskId());
        result.setUserId(task.getUserId());
        // 根据判题 id 创建源代码文件
        String path = CompileFilePath + task.getJudgeTaskId();
        File file = new File(path);
        file.mkdirs();
        try {
            createFile(task.getCompilerId(), path, task.getSource());
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(8);
            result.setErrorMessage("创建文件失败！");
            // 出现错误时，删除文件
            ExecutorUtil.exec(CmdStrings.REMOVE_FILE_WINDOWS + path);
            // 将判题结果插入
            judgeResultService.insert(result);
            // 更新题目状态及个人状态
            questionStatusService.updateStatus(task.getQuestionId(), result.getStatus());
            userInfoService.updateStatus(task.getUserId(), result.getStatus());
            return result;
        }
        // 检查源码字符流中是否包含危险字符
        if (!verify(task.getSource())) {
            result.setStatus(7);
            result.setErrorMessage("使用了不安全的函数");
            ExecutorUtil.exec(CmdStrings.REMOVE_FILE_WINDOWS + path);
            // 将判题结果插入
            judgeResultService.insert(result);
            // 更新题目状态及个人状态
            questionStatusService.updateStatus(task.getQuestionId(), result.getStatus());
            userInfoService.updateStatus(task.getUserId(), result.getStatus());
            return result;
        }

        // 调用编译器对源文件进行编译
        String message = compile(task.getCompilerId(), path, task.getJudgeTaskId());
        log.info(message);
        if (message != null && task.getCompilerId() != 4) {
            result.setStatus(6);
            result.setErrorMessage("编译错误：\n" + message);
            ExecutorUtil.exec(CmdStrings.REMOVE_FILE_WINDOWS + path);
            // 将判题结果插入
            judgeResultService.insert(result);
            // 更新题目状态及个人状态
            questionStatusService.updateStatus(task.getQuestionId(), result.getStatus());
            userInfoService.updateStatus(task.getUserId(), result.getStatus());
            return result;
        }

        log.info("编译完成");
        // 生成可执行文件的执行命令
        String process = process(task.getCompilerId(), path);

        // 执行可执行文件
        parseToResult(process, result, task);
        // 删除源文件
        ExecutorUtil.exec(CmdStrings.REMOVE_FILE_WINDOWS + path);
        // 将判题结果插入
        judgeResultService.insert(result);
        // 更新题目状态及个人状态
        questionStatusService.updateStatus(task.getQuestionId(), result.getStatus());
        userInfoService.updateStatus(task.getUserId(), result.getStatus());

        // 更新通过题目表
        UserPass filter = new UserPass();
        filter.setUserId(task.getUserId());
        filter.setQuestionId(task.getQuestionId());
        QueryWrapper<UserPass> wrapper = new QueryWrapper<>(filter);
        List<UserPass> userPasses = userPassService.selectList(wrapper);
        if (userPasses.isEmpty()) {
            filter.setIsPassed(true);
            userPassService.insert(filter);
        } else {
            UpdateWrapper<UserPass> updateWrapper = new UpdateWrapper<>(filter);
            filter.setIsPassed(true);
            userPassService.updateByEntity(filter, updateWrapper);
        }
        return result;
    }


    private boolean verify(String source) {
        String[] keys = dangerousKeyWords.split(",");
        for (String key: keys) {
            if (source.contains(key))
                return false;
        }
        return true;
    }
//    @Override
//    public JudgeResult judge(JudgeTask task) {
//        JudgeResult result = new JudgeResult();
//        result.setJudgeTaskId(task.getJudgeTaskId());
//        String path = workspace + "/" + task.getJudgeTaskId();
//        File file = new File(path);
//        file.mkdirs();
//        try {
//            createFile(task.getCompilerId(), path, task.getSource());
//        } catch (Exception e) {
//            e.printStackTrace();
//            result.setStatus(8);
//            ExecutorUtil.exec("rm -rf " + path);
//            return result;
//        }
//        //verify the key
//        if (!verify(task.getSource())) {
//            result.setStatus(9);
//            ExecutorUtil.exec("rm -rf " + path);
//            return result;
//        }
//        //compile the source
//        String message = compile(task.getCompilerId(), path);
//        if (message != null && task.getCompilerId() != 4) {
//            result.setStatus(7);
//            result.setErrorMessage(message);
//            ExecutorUtil.exec("rm -rf " + path);
//            return result;
//        }
//        //chmod -R 755 path
//        ExecutorUtil.exec("chmod -R 755 " + path);
//        //judge
//        String process = process(task.getCompilerId(), path);
//        String judge_data = judgeData + "/" + task.getQuestionId();
//        String cmd = "python2 " + judgeScript + " " + process + " " + judge_data + " " + path + " " + task.getTimeLimit() + " " + task.getMemoryLimit();
//        parseToResult(cmd, result);
//        ExecutorUtil.exec("rm -rf " + path);
//        return result;
//    }
//
    @Override
    public String compile(int compilerId, String path, Long judgeTaskId) {
        String cmd = "";
        switch (compilerId) {
            case 1:
                cmd = "cmd /c gcc " + path + "\\main.c -o " + path + "\\main -Wall -lm -O2 -std=c99 --static -DONLINE_JUDGE";
                break;
            case 2:
                cmd = "cmd /c g++ " + path + "\\main.cpp -O2 -Wall -lm --static -DONLINE_JUDGE -o " +path +"\\main";
                break;
            case 3:
                cmd = "cmd /c set CLASSPATH="+ path +" && javac "+ path + "\\Main.java";
                break;
            case 4:
                cmd = "cmd /c python -m py_compile "+ path +"\\main.py";
                break;
        }

//        String cmdCompile = "cmd.exe && E: && cd " + relativePath + " && " + cmd;
//        String cmdCompile = "cmd /c set CLASSPATH="+ path +" && javac "+ path + "\\Main.java";

        return ExecutorUtil.exec(cmd).getError();
    }
//

//
    private String process(int compileId, String path) {
        switch (compileId) {
            case 1:
                return "cmd /c " + path + "\\main";
            case 2:
                return "cmd /c " + path + "\\main";
            case 3:
                return "cmd /c set CLASSPATH="+ path + " && java Main";
            case 4:
                return "python " + path + "\\main.py";
        }
        return null;
    }
//
    private void parseToResult(String cmd, JudgeResult result, JudgeTask judgeTask) {

        Runtime runtime = Runtime.getRuntime();
        // 记录开始结束时间
        long startMemory = runtime.freeMemory();
        long startTime = System.currentTimeMillis();
        // 执行可执行文件
        Long questionId = judgeTask.getQuestionId();
        List<InputExample> inputExamples = inputExampleService.getInputExampleById(questionId);
        InputExample inputExample = inputExamples.get(0);
        String inputExampleString = inputExample.getInputExample();

        ExecMessage exec = ExecutorUtil.execWithInput(cmd, inputExampleString);
        long endMemory = runtime.freeMemory();
        long endTime = System.currentTimeMillis();

        if (exec.getError() != null) {
            // 运行时错误
            result.setStatus(5);
            result.setErrorMessage("运行时错误：\n"+exec.getError());

            log.error("=====error====" + result.getJudgeTaskId() + ":" + exec.getError());
        } else {
            int timeUsed = (int) (endTime - startTime);
            int memoryUsed = (int) (endMemory - startMemory);
            if(memoryUsed < 0) {
                memoryUsed = 0;
            }
            result.setTimeUsed(timeUsed);
            result.setMemoryUsed(memoryUsed);
            if (timeUsed > judgeTask.getTimeLimit()*1000) {
                result.setStatus(4);
                result.setErrorMessage("超出了题目的时间限制!");
                return;
            }
            if (memoryUsed > judgeTask.getMemoryLimit()*1024) {
                result.setStatus(3);
                result.setErrorMessage("超出了题目的内存限制!");
                return;
            }

            List<OutputExample> outputExamples = outputExampleService.getOutputExampleById(questionId);
            OutputExample outputExample = outputExamples.get(0);
            String outputExampleString = outputExample.getOutputExample();
            log.info(outputExampleString);
            log.info(exec.getStdout());
            if (!outputExampleString.trim().equals(exec.getStdout().trim())) {
                result.setStatus(1);
                result.setErrorMessage("错误答案!");
                return;
            }

            result.setStatus(0);
            result.setErrorMessage("正确!");
        }
    }

//    if (exec.getError() != null) {
//        // 运行时错误
//        result.setStatus(5);
//        result.setErrorMessage(exec.getError());
//        log.error("=====error====" + result.getJudgeTaskId() + ":" + exec.getError());
//    } else {
////            Stdout out = JSON.parseObject(exec.getStdout(), Stdout.class);
////            log.info("=====stdout====" + out);
////            result.setStatus(out.getStatus());
////            result.setTimeUsed(out.getMaxTime().intValue());
////            result.setMemoryUsed(out.getMaxMemory().intValue());
//
//    }

    private static void createFile(int compilerId, String path, String source) throws Exception {
        String filename = "";
        switch (compilerId) {
            case 1:
                filename = CmdStrings.FILE_NAME_GCC;
                break;
            case 2:
                filename = CmdStrings.FILE_NAME_GPP;
                break;
            case 3:
                filename = CmdStrings.FILE_NAME_JAVA;
                break;
            case 4:
                filename = CmdStrings.FILE_NAME_PYTHON;
                break;
        }
        File file = new File(path + "/" + filename);
        file.createNewFile();
        OutputStream output = new FileOutputStream(file);
        PrintWriter writer = new PrintWriter(output);
        writer.print(source);
        writer.close();
        output.close();
    }

}
