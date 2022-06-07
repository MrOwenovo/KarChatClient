package com.Karchat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 发送的请求信息
 */
@Data
@AllArgsConstructor
@Component
@Scope("prototype")
public class Post {
    String post;  //请求人
    String geter; //被请求人
    String state;  //状态
}
