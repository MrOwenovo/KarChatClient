package com.Karchat.entity;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 聊天信息
 */
@Data
@Component
@Scope("prototype")
public class Message {
    String user1;  //用户1
    String user2;  //用户2
    String message;  //发送的信息
}
