package com.chen.student;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chen.biz.pojo.InputExample;
import com.chen.biz.pojo.OutputExample;
import com.chen.biz.pojo.Question;
import com.chen.biz.service.InputExampleService;
import com.chen.biz.service.OutputExampleService;
import com.chen.biz.service.QuestionService;
import com.chen.student.data.ReadFile;
import com.chen.student.utils.QuestionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author danger
 * @date 2021/4/19
 */
@SpringBootTest
public class DBInsert {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private InputExampleService inputExampleService;
    @Autowired
    private OutputExampleService outputExampleService;

    @Transactional
    @Test
    public void test3() {
        List<String> strings = ReadFile.toListByFileReader("E:\\final\\coding-online\\student\\src\\main\\java\\com\\chen\\student\\data\\data_a_c.txt");
        for (String string : strings) {
            JSONObject jsonObject = JSON.parseObject(string);
            Object problemSetProblem = jsonObject.get("problemSetProblem");
            JSONObject problemSetProblemMap = JSON.parseObject(problemSetProblem.toString());
            Question question = new Question();
            question.setQuestionOrder(QuestionUtils.questionOrder++);
            question.setTitle(problemSetProblemMap.get("title").toString());
            question.setQuestionTypeId(3L);
            int score = Integer.parseInt(problemSetProblemMap.get("score").toString());
            if (score <= 10) {
                question.setQuestionDifficulty("简单");
            } else if (score <= 15){
                question.setQuestionDifficulty("中等");
            } else {
                question.setQuestionDifficulty("困难");
            }
            String description = problemSetProblemMap.get("description").toString();

            if (description.contains("输入格式")) {
                String input = description.substring(description.indexOf("输入格式")+5, description.indexOf("输出格式")-4).trim();
                question.setInputDescription(input);
            }
            if (description.contains("输出格式")) {
                String output = description.substring(description.indexOf("输出格式")+5).trim();
                if (output.contains("输入样例"))
                    output = output.substring(0, output.indexOf("输入样例")).trim();
                if (output.contains("###"))
                    output = output.substring(0, output.indexOf("###")).trim();
                question.setOutputDescription(output);
            }

            question.setQuestionDescription(description);

            Object problemConfig = problemSetProblemMap.get("problemConfig");
            JSONObject problemConfigJson = JSON.parseObject(problemConfig.toString());
            Object codeCompletionProblemConfig = problemConfigJson.get("codeCompletionProblemConfig");
            if (codeCompletionProblemConfig == null) {
                codeCompletionProblemConfig = problemConfigJson.get("programmingProblemConfig");
            }
            JSONObject codeCompletionProblemConfigJson = JSON.parseObject(codeCompletionProblemConfig.toString());

            question.setMemoryLimit(Integer.parseInt(codeCompletionProblemConfigJson.get("memoryLimit").toString()));
            question.setTimeLimit(Integer.parseInt(codeCompletionProblemConfigJson.get("timeLimit").toString()));

            questionService.insertQuestion(question);

            if (description.contains("输入样例")) {
                String inputE = description.substring(description.indexOf("输入样例"), description.indexOf("输出样例"));
                inputE = inputE.substring(inputE.indexOf("```in")+5, inputE.lastIndexOf("```")).trim();
                String outputE = description.substring(description.lastIndexOf("输出样例"));
                if (!outputE.contains("```out")) {
                    outputE = outputE.substring(outputE.indexOf("```")+3, outputE.lastIndexOf("```")).trim();
                } else {
                    outputE = outputE.substring(outputE.indexOf("```out")+6, outputE.lastIndexOf("```")).trim();
                }
                InputExample inputExample = new InputExample();
                inputExample.setQuestionId(question.getQuestionId());
                inputExample.setInputExample(inputE);
                inputExampleService.insertInputExample(inputExample);
                OutputExample outputExample = new OutputExample();
                outputExample.setQuestionId(question.getQuestionId());
                outputExample.setOutputExample(outputE);
                outputExampleService.insertOutputExample(outputExample);
            }
        }
    }

    @Test
//    @Transactional
    public void test2() {
        for (long i = 117; i < 133; i++) {
            Question question = questionService.getQuestionByOrder(i);
            InputExample inputExample = new InputExample();
            inputExample.setQuestionId(question.getQuestionId());
            inputExample.setInputExample(question.getInputDescription());
            inputExampleService.insertInputExample(inputExample);
            OutputExample outputExample = new OutputExample();
            outputExample.setQuestionId(question.getQuestionId());
            outputExample.setOutputExample(question.getOutputDescription());
            outputExampleService.insertOutputExample(outputExample);
        }
    }


    @Test
    public void test() {
        List<String> strings = ReadFile.toListByFileReader("E:\\final\\coding-online\\student\\src\\main\\java\\com\\chen\\student\\data\\data_basic.txt");
        for (String string : strings) {
            JSONObject jsonObject = JSON.parseObject(string);
            Object problemSetProblem = jsonObject.get("problemSetProblem");
            JSONObject problemSetProblemMap = JSON.parseObject(problemSetProblem.toString());
            Question question = new Question();
            question.setQuestionOrder(QuestionUtils.questionOrder++);
            question.setTitle(problemSetProblemMap.get("title").toString());
            question.setQuestionTypeId(1L);
            int score = Integer.parseInt(problemSetProblemMap.get("score").toString());
            if (score <= 10) {
                question.setQuestionDifficulty("简单");
            } else if (score <= 15) {
                question.setQuestionDifficulty("中等");
            } else {
                question.setQuestionDifficulty("困难");
            }
            String description = problemSetProblemMap.get("description").toString();
            String input = description.substring(description.indexOf("输入样例"), description.indexOf("输出样例"));
            input = input.substring(input.indexOf("```in") + 5, input.lastIndexOf("```")).trim();
            String output = description.substring(description.lastIndexOf("输出样例"));
            if (!output.contains("```out")) {
                output = output.substring(output.indexOf("```") + 3, output.lastIndexOf("```")).trim();
            } else {
                output = output.substring(output.indexOf("```out") + 6, output.lastIndexOf("```")).trim();
            }
            question.setQuestionDescription(description);
            question.setInputDescription(input);
            question.setOutputDescription(output);
            Object problemConfig = problemSetProblemMap.get("problemConfig");
            JSONObject problemConfigJson = JSON.parseObject(problemConfig.toString());
            Object codeCompletionProblemConfig = problemConfigJson.get("codeCompletionProblemConfig");
            if (codeCompletionProblemConfig == null) {
                codeCompletionProblemConfig = problemConfigJson.get("programmingProblemConfig");
            }
            JSONObject codeCompletionProblemConfigJson = JSON.parseObject(codeCompletionProblemConfig.toString());

            question.setMemoryLimit(Integer.parseInt(codeCompletionProblemConfigJson.get("memoryLimit").toString()));
            question.setTimeLimit(Integer.parseInt(codeCompletionProblemConfigJson.get("timeLimit").toString()));

            questionService.insertQuestion(question);
        }
    }


    @Test
    @Transactional
    public void test4() {
        List<String> strings = ReadFile.toListByFileReader("E:\\final\\coding-online\\student\\src\\main\\java\\com\\chen\\student\\data\\data_data_c.txt");
        for (String string : strings) {
            JSONObject jsonObject = JSON.parseObject(string);
            Object problemSetProblem = jsonObject.get("problemSetProblem");
            JSONObject problemSetProblemMap = JSON.parseObject(problemSetProblem.toString());
            Question question = new Question();
            question.setQuestionOrder(QuestionUtils.questionOrder++);
            question.setTitle(problemSetProblemMap.get("title").toString());
            question.setQuestionTypeId(1L);
            int score = Integer.parseInt(problemSetProblemMap.get("score").toString());
            if (score <= 10) {
                question.setQuestionDifficulty("简单");
            } else if (score <= 15) {
                question.setQuestionDifficulty("中等");
            } else {
                question.setQuestionDifficulty("困难");
            }
            String description = problemSetProblemMap.get("description").toString();
            String input = description.substring(description.indexOf("输入样例"), description.indexOf("输出样例"));
            if (input.contains("```in")) {
                input = input.substring(input.indexOf("```in") + 5, input.lastIndexOf("```")).trim();
            }
            if (input.contains("```")) {
                input = input.substring(input.indexOf("```") + 3, input.lastIndexOf("```")).trim();
            }
            String output = description.substring(description.lastIndexOf("输出样例"));
            if (!output.contains("```out")) {
                output = output.substring(output.indexOf("```") + 3, output.lastIndexOf("```")).trim();
            } else {
                output = output.substring(output.indexOf("```out") + 6, output.lastIndexOf("```")).trim();
            }
            question.setQuestionDescription(description);
            question.setInputDescription(input);
            question.setOutputDescription(output);
            Object problemConfig = problemSetProblemMap.get("problemConfig");
            JSONObject problemConfigJson = JSON.parseObject(problemConfig.toString());
            Object codeCompletionProblemConfig = problemConfigJson.get("codeCompletionProblemConfig");
            if (codeCompletionProblemConfig == null) {
                codeCompletionProblemConfig = problemConfigJson.get("programmingProblemConfig");
            }
            JSONObject codeCompletionProblemConfigJson = JSON.parseObject(codeCompletionProblemConfig.toString());

            question.setMemoryLimit(Integer.parseInt(codeCompletionProblemConfigJson.get("memoryLimit").toString()));
            question.setTimeLimit(Integer.parseInt(codeCompletionProblemConfigJson.get("timeLimit").toString()));
            System.out.println("========================");
            System.out.println(question.getInputDescription());
            System.out.println("==========o================");
            System.out.println(question.getOutputDescription());
            questionService.insertQuestion(question);

        }
    }
}
