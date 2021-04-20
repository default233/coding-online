package com.chen.biz.service;

import java.util.List;

/**
 * @author danger
 * @date 2021/4/16
 */
public interface QuestionTypeService {
    List<String> getAllType();

    Long selectIdByTypeName(String type);
}
