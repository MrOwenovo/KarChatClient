package com.Karchat.dao;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.PrintStream;

/**
 * 与数据库进行交互的聊天相关动作
 */
@Component
public interface Chat {


    /**
     * 发送信息给好友,并传输给数据库
     */
    @SneakyThrows
    String SendToDataSource(PrintStream out, BufferedReader buf, String message, String geter);
}
