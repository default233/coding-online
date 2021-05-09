package com.chen.biz.service;

import com.chen.biz.mapper.QuestionTypeMapper;
import com.chen.biz.pojo.QuestionType;

import java.util.List;

/**
 * @author danger
 * @date 2021/4/16
 */
public interface QuestionTypeService extends BaseService<QuestionType, QuestionTypeMapper> {
    List<String> getAllType();

    Long selectIdByTypeName(String type);
}
