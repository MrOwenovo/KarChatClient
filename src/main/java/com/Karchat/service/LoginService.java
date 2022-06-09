package com.Karchat.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.PrintStream;

@Component
public interface LoginService {

    /**
     * 检查本地是否记录了账号密码
     * @return
     */
    boolean CheckAccountRecords();

    /**
     * 进行登录
     */
    @SneakyThrows
    void Login(PrintStream out, BufferedReader buf);

    /**
     * 进行注册
     */
    void Register(PrintStream out, BufferedReader buf);

    /**
     * 服务器未连接时登录
     */
    void LoginWhenClientDisConnect();

    /**
     * 服务器未连接时注册
     */
    void RegisterWhenClientDisConnect();
}
