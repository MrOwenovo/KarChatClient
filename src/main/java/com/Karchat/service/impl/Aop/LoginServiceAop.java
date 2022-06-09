package com.Karchat.service.impl.Aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoginServiceAop {

    @Before("execution(* com.Karchat.service.LoginService.CheckAccountRecords())")
    public void beforeCheckAccountRecords(JoinPoint joinPoint) {
        log.info("正在寻找本地存储的账户密码....");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.LoginService.CheckAccountRecords())", returning = "returnVal")
    public void afterCheckAccountRecords(Object returnVal) {
        if ((Boolean) returnVal) {
            log.info("寻找本地存储的账户密码成功,已读取!");
        } else {
            log.info("寻找本地存储的账户密码成功,未发现存储文件!");
        }
    }

    @Before("execution(* com.Karchat.service.LoginService.Login())")
    public void beforeLogin(JoinPoint joinPoint) {
        log.info("正在登陆中.....");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.LoginService.Login())", returning = "returnVal")
    public void afterLogin(Object returnVal) {
        log.info("正在打开主界面....");
    }

    @Before("execution(* com.Karchat.service.LoginService.Register())")
    public void beforeRegister(JoinPoint joinPoint) {
        log.info("注册中.....");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.LoginService.Register())", returning = "returnVal")
    public void afterRegister(Object returnVal) {
        log.info("-->注册任务完成<--");
    }

    @Before("execution(* com.Karchat.service.LoginService.LoginWhenClientDisConnect())")
    public void beforeLoginWhenClientDisConnect(JoinPoint joinPoint) {
        log.info("登陆中....");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.LoginService.LoginWhenClientDisConnect())", returning = "returnVal")
    public void afterLoginWhenClientDisConnect(Object returnVal) {
        log.info("!!服务器未连接，无法登录!!");
    }

    @Before("execution(* com.Karchat.service.LoginService.RegisterWhenClientDisConnect())")
    public void beforeRegisterWhenClientDisConnect(JoinPoint joinPoint) {
        log.info("注册中....");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.LoginService.RegisterWhenClientDisConnect())", returning = "returnVal")
    public void afterRegisterWhenClientDisConnect(Object returnVal) {
        log.info("!!服务器未连接，无法注册!!");
    }
}
