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
public class ChatServiceAop {
    @Before("execution(* com.Karchat.service.ChatService.AddFriend())")
    public void beforeAddFriend(JoinPoint joinPoint) {
        log.info("-->添加好友进入任务队列");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.ChatService.AddFriend())",returning = "returnVal")
    public void afterAddFriend(Object returnVal) {
        log.info("-->添加好友进入任务队列成功<--");
    }

    @Before("execution(* com.Karchat.service.ChatService.AcceptFriendInvitation())")
    public void beforeAcceptFriendInvitation(JoinPoint joinPoint) {
        log.info("加好友成功，正在修改好友状态....");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.ChatService.AcceptFriendInvitation())",returning = "returnVal")
    public void afterAcceptFriendInvitation(Object returnVal) {
        log.info("正在添加....");
    }

    @Before("execution(* com.Karchat.service.ChatService.RefuseFriendInvitation())")
    public void beforeRefuseFriendInvitation(JoinPoint joinPoint) {
        log.info("加已拒绝好友邀请，正在删除邀请信息....");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.ChatService.RefuseFriendInvitation())",returning = "returnVal")
    public void afterRefuseFriendInvitation(Object returnVal) {
        log.info("正在删除....");
    }

    @Before("execution(* com.Karchat.service.ChatService.Send())")
    public void beforeSend(JoinPoint joinPoint) {
        log.info("-->发送消息进入任务队列");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.ChatService.Send())",returning = "returnVal")
    public void afterSend(Object returnVal) {
        log.info("-->添加好友进入任务队列成功<--");
    }
}
