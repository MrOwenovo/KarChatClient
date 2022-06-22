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
public class BackgroundServiceAop {

    @Before("execution(* com.Karchat.service.BackgroundService.RefreshListOfFriendsToBeApprovedAndSent())")
    public void beforeRefreshListOfFriendsToBeApprovedAndSent(JoinPoint joinPoint) {
        log.info("-->刷新好友邀请列表进入任务队列");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.BackgroundService.RefreshListOfFriendsToBeApprovedAndSent())",returning = "returnVal")
    public void afterRefreshListOfFriendsToBeApprovedAndSent(Object returnVal) {
        log.info("-->刷新好友邀请列表进入任务队列成功<--");
    }

    @Before("execution(* com.Karchat.service.BackgroundService.RefreshChatHistory())")
    public void beforeRefreshChatHistory(JoinPoint joinPoint) {
        log.info("-->检查聊天记录进入任务队列");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.BackgroundService.RefreshChatHistory())",returning = "returnVal")
    public void afterRefreshChatHistory(Object returnVal) {
        log.info("-->检查聊天记录进入任务队列成功<--");
    }

    @Before("execution(* com.Karchat.service.BackgroundService.RefreshFriendsState())")
    public void beforeRefreshFriendsState(JoinPoint joinPoint) {
        log.info("-->更新好友在线状态进入队列");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.BackgroundService.RefreshFriendsState())",returning = "returnVal")
    public void afterRefreshFriendsState(Object returnVal) {
        log.info("-->正在更新好友在线状态进入队列成功<--");
    }
}
