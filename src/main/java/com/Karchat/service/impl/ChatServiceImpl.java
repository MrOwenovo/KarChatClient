package com.Karchat.service.impl;

import com.Karchat.dao.mapper.ChatMapper;
import com.Karchat.service.ChatService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ChatServiceImpl implements ChatService {

    @Resource
    ChatMapper mapper;  //自动注入mapper

//    @Transactional(propagation = Propagation.MANDATORY)  //回滚到错误记录点


}
