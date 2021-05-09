package com.chen.biz.service.impl;

import com.chen.biz.mapper.UserPassMapper;
import com.chen.biz.pojo.UserPass;
import com.chen.biz.service.UserPassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author danger
 * @date 2021/5/8
 */
@Service
@Slf4j
public class UserPassServiceImpl extends BaseServiceImpl<UserPass, UserPassMapper> implements UserPassService {
}
