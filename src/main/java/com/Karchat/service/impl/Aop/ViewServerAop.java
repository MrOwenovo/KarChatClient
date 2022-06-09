package com.Karchat.service.impl.Aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class ViewServerAop {

    @AfterReturning(value = "execution(* com.Karchat.service.ViewServer.DisplayNotConnectedLogin())", returning = "returnVal")
    public void afterDisplayNotConnectedLogin(Object returnVal) {
        log.info("服务器未连接!");
    }

    @Before("execution(* com.Karchat.service.ViewServer.DisplayConnectedLogin())")
    public void beforeDisplayConnectedLogin(JoinPoint joinPoint) {
        log.info("登录界面打开中.....");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.ViewServer.DisplayConnectedLogin())", returning = "returnVal")
    public void afterDisplayConnectedLogin(Object returnVal) {
        log.info("登录界面打开成功!");
    }

    @Before("execution(* com.Karchat.service.ViewServer.DisplayConnectedClient())")
    public void beforeDisplayConnectedClient(JoinPoint joinPoint) {
        log.info("客户端主界面打开中.....");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.ViewServer.DisplayConnectedClient())", returning = "returnVal")
    public void afterDisplayConnectedClient(Object returnVal) {
        log.info("客户端主界面打开成功!");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.ViewServer.ServerClosed())", returning = "returnVal")
    public void afterServerClosed(Object returnVal) {
        log.info("服务器已经关闭!请重启客户端");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.ViewServer.ClientError())", returning = "returnVal")
    public void afterClientError(Object returnVal) {
        log.info("客户端出错!请重启客户端");
    }
}
