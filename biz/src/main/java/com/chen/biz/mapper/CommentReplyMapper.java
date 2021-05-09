package com.chen.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.biz.pojo.Comment;
import com.chen.biz.pojo.CommentReply;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author danger
 * @date 2021/5/9
 */
@Mapper
public interface CommentReplyMapper extends BaseMapper<CommentReply> {
}
