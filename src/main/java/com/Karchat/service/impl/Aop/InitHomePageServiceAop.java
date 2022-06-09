package com.Karchat.service.impl.Aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class InitHomePageServiceAop {
    @Before("execution(* com.Karchat.service.InitHomePageService.GetMyIcon())")
    public void beforeGetMyIcon(JoinPoint joinPoint) {
        log.info("获取用户头像....");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.InitHomePageService.GetMyIcon())",returning = "returnVal")
    public void afterGetMyIcon(Object returnVal) {
        log.info("获取用户头像成功!");
    }

    @Before("execution(* com.Karchat.service.InitHomePageService.GetFriendInvitationDidNotAgree())")
    public void beforeGetFriendInvitationDidNotAgree(JoinPoint joinPoint) {
        log.info("获取待同意的好友邀请....");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.InitHomePageService.GetFriendInvitationDidNotAgree())",returning = "returnVal")
    public void afterGetFriendInvitationDidNotAgree(Object returnVal) {
        log.info("获取待同意的好友邀请成功!");

    }

    @Before("execution(* com.Karchat.service.InitHomePageService.GetFriendInvitationHadSent())")
    public void beforeGetFriendInvitationHadSent(JoinPoint joinPoint) {
        log.info("获取已发送的好友邀请记录....");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.InitHomePageService.GetFriendInvitationHadSent())",returning = "returnVal")
    public void afterGetFriendInvitationHadSent(Object returnVal) {
        log.info("获取已发送的好友邀请记录成功!");
    }

    @Before("execution(* com.Karchat.service.InitHomePageService.GetFriendIconHadSent())")
    public void beforeGetFriendIconHadSent(JoinPoint joinPoint) {
        log.info("获得已发送邀请的好友头像....");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.InitHomePageService.GetFriendIconHadSent())",returning = "returnVal")
    public void afterGetFriendIconHadSent(Object returnVal) {
        log.info("获得已发送邀请的好友头像成功!");
    }

    @Before("execution(* com.Karchat.service.InitHomePageService.GetListOfFriends())")
    public void beforeGetListOfFriends(JoinPoint joinPoint) {
        log.info("正在获取好友列表....");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.InitHomePageService.GetListOfFriends())",returning = "returnVal")
    public void afterGetListOfFriends(Object returnVal) {
        log.info("正在获取好友列表成功!");
    }

    @Before("execution(* com.Karchat.service.InitHomePageService.GetFriendsIcon())")
    public void beforeGetFriendsIcon(JoinPoint joinPoint) {
        log.info("获取好友的头像....");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.InitHomePageService.GetFriendsIcon())",returning = "returnVal")
    public void afterGetFriendsIcon(Object returnVal) {
        log.info("获取好友的头像成功!");
    }

    @Before("execution(* com.Karchat.service.InitHomePageService.GetFriendsState())")
    public void beforeGetFriendsState(JoinPoint joinPoint) {
        log.info("获取好友的在线状态....");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.InitHomePageService.GetFriendsState())",returning = "returnVal")
    public void afterGetFriendsState(Object returnVal) {
        log.info("获取好友的在线状态成功!");
    }

    @Before("execution(* com.Karchat.service.InitHomePageService.GetChatHistory())")
    public void beforeGetChatHistory(JoinPoint joinPoint) {
        log.info("-->查询- "+joinPoint.getArgs()[0]+" -进入任务队列");
    }

    @AfterReturning(value = "execution(* com.Karchat.service.InitHomePageService.GetChatHistory())",returning = "returnVal")
    public void afterGetChatHistory(JoinPoint joinPoint,Object returnVal) {
        log.info("-->查询- "+joinPoint.getArgs()[0]+" -进入任务队列成功");
    }
}
