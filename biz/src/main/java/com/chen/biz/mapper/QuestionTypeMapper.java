package com.chen.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.biz.pojo.QuestionType;
import com.chen.biz.vo.TypeInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author danger
 * @date 2021/4/16
 */
@Mapper
public interface QuestionTypeMapper extends BaseMapper<QuestionType> {
    List<TypeInformation> getTypeInformation();
    int updateQuestionTypeByName(@Param("oldType") String oldType,@Param("newType") String newType);
}
