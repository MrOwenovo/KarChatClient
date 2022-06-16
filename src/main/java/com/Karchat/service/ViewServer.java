package com.Karchat.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.net.Socket;

@Component
public interface ViewServer {

    /**
     * 展示未连接的客户端登录画面
     */
    void DisplayNotConnectedLogin();

    /**
     * 打开连接好的客户端登录界面
     */
    void DisplayConnectedLogin();

    /**
     * 打开连接好的客户端主界面
     */
    void DisplayConnectedClient();


    /**
     * 显示服务器关闭，以及加载条，更新错误信息
     */
    void ServerClosed();

    /**
     * 客户端出问题时调用的方法
     */
    void ClientError();

    /**
     * 让主界面左菜单收缩的功能，用于后台检测情况后主动收缩
     */
    void MenuShrink();
}
