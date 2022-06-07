package com.Karchat.service;

import org.springframework.stereotype.Component;

@Component
public interface LoginService {

    /**
     * 检查本地是否记录了账号密码
     */
    public void CheckAccountRecords();

    /**
     * 进行登录
     */
    public void Login();
}
