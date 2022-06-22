package com.Karchat.service.impl;

import com.Karchat.dao.AddFriend;
import com.Karchat.dao.Chat;
import com.Karchat.service.ChatService;
import com.Karchat.service.MusicService;
import com.Karchat.util.ComponentUtil.CompositeComponent.MenuContent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import static com.Karchat.util.Constant.*;

@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    Chat chat;  //自动注入mapper

    @Resource
    AddFriend addFriend;

    @Resource
    MusicService music;

    //    @Transactional(propagation = Propagation.MANDATORY)  //回滚到错误记录点


    @Override
    public synchronized boolean AddFriend(PrintStream out, BufferedReader buf) {
        new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                label:
                {
                    while (true) {
                        if (!isSending && !isCheckingHistory && !isFlashing && !isGetFriendAmount&&!isAddingFriends&&!isRefreshFriendsList&&!isDisAgreeFriends&&!isAgreeFriends&&!isRefreshingStates) {
                            log.info("正在添加好友....");
                            isAddingFriends = true;
                            String bool = addFriend.addFriendWithDataSource(out, buf);
//
                            if ("true".equals(bool)) {
                                log.info("邀请已经发送!");
                                MenuContent.searchText.setText("已发送好友邀请");
                                MenuContent.searchText.setForeground(new Color(62, 171, 159));
                                music.playSuccessMP3();
                            } else if ("false".equals(bool)) {
                                log.info("发送失败!用户名输入错误");
                                MenuContent.searchText.setText("用户名输入错误");
                                MenuContent.searchText.setForeground(new Color(161, 19, 19));
                                music.playErrorMP3();
                            } else if ("already".equals(bool)) {  //已经存在了邀请
                                log.info("发送失败!已经发送过该邀请");
                                MenuContent.searchText.setText("已经发送过该邀请");
                                MenuContent.searchText.setForeground(new Color(161, 19, 19));
                                music.playErrorMP3();
                            }
                            String isFinish = buf.readLine();  //读取是否结束
                            if (isFinish.equals("finish")) {
                                isAddingFriends = false;
                                break label;
                            }
                        }
                    }
                }
            }
        }.start();
        return true;
    }

    @SneakyThrows
    @Override
    public synchronized boolean AcceptFriendInvitation(PrintStream out, BufferedReader buf) {
        log.info("--修改好友状态进入任务队列--");

        new Thread(){
            @SneakyThrows
            @Override
            public void run() {
                label:
                {
                    while (true) {
                        Thread.sleep(1000);
                        if (!isFlashing && !isSending && !isAddingFriends && !isCheckingHistory && whetherBackgroundCanEnabled && initFinishAndCanFlashChatHistory && !isRefreshFriendsList && !isAgreeFriends && !isDisAgreeFriends&&!isRefreshingStates) {  //需要在不刷新加好友页面时执行,并不能进行发送
                            log.info("加好友成功，正在修改好友状态....");
                            isAgreeFriends = true;  //正在修改好友状态
                            new Thread() {
                                @Override
                                public void run() {
                                    String BooleanFlag = addFriend.AcceptFriendInvitationWithDataSource(out, buf);
                                    if ("true".equals(BooleanFlag)) {
                                        MenuContent.searchText.setText("已同意好友邀请");
                                        log.info("修改好友状态成功!");
                                        isAgreeFriends = false;  //修改好友状态结束
                                        MenuContent.searchText.setForeground(new Color(62, 171, 159));
                                        music.playSuccessMP3();
                                    }
                                }
                            }.start();
                            while (isAgreeFriends) {

                            }
                            break label;
                        }

                    }
                }
            }
        }.start();

        return true;
    }

    @SneakyThrows
    @Override
    public boolean RefuseFriendInvitation(PrintStream out, BufferedReader buf) {
        log.info("--拒绝好友邀请，删除邀请信息进入任务队列--");

        new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                label2:
                {
                    while (true) {
                        Thread.sleep(1000);
                        if (!isFlashing && !isSending && !isAddingFriends && !isCheckingHistory && whetherBackgroundCanEnabled && initFinishAndCanFlashChatHistory && !isRefreshFriendsList && !isAgreeFriends && !isDisAgreeFriends&&!isRefreshingStates) {  //需要在不刷新加好友页面时执行,并不能进行发送
                            new Thread() {
                                @Override
                                public void run() {
                                    log.info("已拒绝好友邀请，正在删除邀请信息....");
                                    isDisAgreeFriends = true;  //正在拒绝
                                    addFriend.RefuseFriendInvitationWithDataSource(out, buf);
                                    MenuContent.searchText.setText("已拒绝好友邀请");
                                    MenuContent.searchText.setForeground(new Color(102, 48, 180));
                                    music.playSuccessMP3();
                                }
                            }.start();
                            while (isDisAgreeFriends) {

                            }
                            break label2;
                        }
                    }
                }
            }
        }.start();

        return true;
    }
    @Override
    public synchronized void Send(String message, String geter, Socket clien) {
        new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                PrintStream out = new PrintStream(
                        clien.getOutputStream());  //向服务器端输出信息
                BufferedReader buf = new BufferedReader(new InputStreamReader(clien.getInputStream())); //接收服务器返回的信息

                label:
                {
                    while (true) {
                        if (!isFlashing && !isCheckingHistory && !isAddingFriends&&!isSending&& !isRefreshFriendsList&&!isAgreeFriends&&!isDisAgreeFriends&&!isRefreshingStates) {  //找到其他刷新任务不在的间隙进行
                            log.info("正在发送给-" + geter + "-: " + message);
                            isSending = true;  //正在发送
                            String flag = chat.SendToDataSource(out,buf,message,geter);
                            if (flag.equals("true")) {
                                log.info("发送成功!");
                                isSending = false;  //发送完成
                                break label;  //退出while循环
                            }
                        }
                    }
                }
            }
        }.start();
    }



}
