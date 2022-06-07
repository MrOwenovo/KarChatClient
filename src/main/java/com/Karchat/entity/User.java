package com.Karchat.entity;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 用户实体类
 */
@Data
@Component
@Scope("prototype")
public class User {
    String username;  //用户名
    String password;  //密码
    int state ; //状态
}
