package com.Karchat.service;

import org.springframework.stereotype.Component;

@Component
public interface ViewServer {

    /**
     * 展示未连接的客户端登录画面
     */
    public void DisplayNotConnectedLogin();

    /**
     * 打开连接好的客户端登录界面
     */
    public void DisplayConnectedLogin();

    /**
     * 打开连接好的客户端主界面
     */
    public void DisplayNotConnectedClient();
}
