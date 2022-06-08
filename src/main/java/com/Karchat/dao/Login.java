package com.Karchat.dao;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.PrintStream;

/**
 * 与数据库进行交互的登录相关动作
 */
@Component
public interface Login {

    /**
     * 从数据库中获取登录信息
     */
    String LoginFromDataSource(PrintStream out, BufferedReader buf);

    /**
     * 与数据库进行注册信息交互
     */
    @SneakyThrows
    String RegisterWithDataSource(PrintStream out, BufferedReader buf);

    /**
     * 注册成功，在数据库中建立存储好友的表
     */
    void CreateFriendsTable(PrintStream out, BufferedReader buf);
}
