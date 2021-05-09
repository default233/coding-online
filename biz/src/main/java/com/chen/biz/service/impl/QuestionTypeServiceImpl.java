package com.chen.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.biz.mapper.QuestionTypeMapper;
import com.chen.biz.mapper.SysUserMapper;
import com.chen.biz.pojo.QuestionType;
import com.chen.biz.service.QuestionTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danger
 * @date 2021/4/16
 */
@Service
@Slf4j
public class QuestionTypeServiceImpl extends BaseServiceImpl<QuestionType, QuestionTypeMapper> implements QuestionTypeService {
    @Autowired
    private QuestionTypeMapper questionTypeMapper;

    @Override
    public List<String> getAllType() {
        List<String> types = new ArrayList<>();
        List<QuestionType> questionTypes = questionTypeMapper.selectList(null);
        for (QuestionType questionType : questionTypes) {
            types.add(questionType.getQuestionType());
        }
        return types;
    }

    @Override
    public Long selectIdByTypeName(String type) {
        QuestionType filter = new QuestionType();
        filter.setQuestionType(type);
        QueryWrapper<QuestionType> typeQueryWrapper = new QueryWrapper<>(filter);
        QuestionType questionType = questionTypeMapper.selectOne(typeQueryWrapper);
        if (questionType != null)
            return questionType.getQuestionTypeId();
        return null;
    }
}
