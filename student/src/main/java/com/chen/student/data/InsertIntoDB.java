package com.chen.student.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chen.biz.pojo.Question;
import com.chen.student.utils.QuestionUtils;

import java.util.List;
import java.util.Random;

/**
 * @author danger
 * @date 2021/4/18
 */
public class InsertIntoDB {
    public static void test(String[] args) {
        List<String> strings = ReadFile.toListByFileReader("E:\\final\\coding-online\\student\\src\\main\\java\\com\\chen\\student\\data\\data_data.txt");
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

            System.out.println(question);

//            if (description.contains("输入样例")) {
//                String inputE = description.substring(description.indexOf("输入样例"), description.indexOf("输出样例"));
//                inputE = inputE.substring(inputE.indexOf("```in")+5, inputE.lastIndexOf("```")).trim();
//                String outputE = description.substring(description.lastIndexOf("输出样例"));
//                if (!outputE.contains("```out")) {
//                    outputE = outputE.substring(outputE.indexOf("```")+3, outputE.lastIndexOf("```")).trim();
//                } else {
//                    outputE = outputE.substring(outputE.indexOf("```out")+6, outputE.lastIndexOf("```")).trim();
//                }
//                System.out.println("====================================");
//                System.out.println(inputE);
//                System.out.println("=========");
//                System.out.println(outputE);
//                System.out.println("====================================");
//            }
        }
    }
}
