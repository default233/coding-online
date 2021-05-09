package com.chen.biz.service.impl;

import com.chen.biz.mapper.CommentMapper;
import com.chen.biz.pojo.Comment;
import com.chen.biz.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @author danger
 * @date 2021/5/9
 */
@Service
@Slf4j
public class CommentServiceImpl  extends BaseServiceImpl<Comment, CommentMapper> implements CommentService {
}
