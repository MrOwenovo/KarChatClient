package com.Karchat.service.impl;

import com.Karchat.dao.InitHomePage;
import com.Karchat.entity.Friends;
import com.Karchat.entity.History;
import com.Karchat.entity.Post;
import com.Karchat.service.InitHomePageService;
import com.Karchat.service.MusicService;
import com.Karchat.util.ComponentUtil.CompositeComponent.MenuContent;
import com.Karchat.util.ComponentUtil.Label.InnerLabel;
import com.Karchat.util.Constant;
import com.Karchat.util.PictureUtil.GetPicture;
import com.Karchat.view.Home;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import static com.Karchat.util.ComponentUtil.CompositeComponent.MenuContent.*;
import static com.Karchat.util.Constant.*;

@Slf4j
@Service
public class InitHomePageServiceImpl implements InitHomePageService {

    @Resource
    InitHomePage initHomePage;
    @Resource
    MusicService musicService;

    @Override
    public boolean GetMyIcon(PrintStream out, BufferedReader buf) {
        String iconString = initHomePage.GetMyIconFromDataSource(out, buf);
        BufferedImage icon = GetPicture.stringToImage(iconString);  //转成图片
        assert icon != null;
        Home.setIcon(icon);
        log.info("获取用户头像....");
        return true;
    }

    @Override
    public boolean GetFriendInvitationDidNotAgree(PrintStream out, BufferedReader buf) {
        log.info("获取待同意的好友邀请....");
        Post[] posts = initHomePage.GetFriendInvitationDidNotAgreeFromDataSource(out, buf);
        MenuContent.getPosts(posts);  //发送所有请求
        return true;
    }

    @Override
    public boolean GetFriendIconDidNotAgree(PrintStream out, BufferedReader buf) {
        log.info("获得待同意邀请的好友头像....");
        initHomePage.GetFriendIconDidNotAgreeFromDataSource(out, buf);
        MenuContent.setContext();
        return true;
    }

    @Override
    public boolean GetFriendInvitationHadSent(PrintStream out, BufferedReader buf) {
        log.info("获取已发送的好友邀请记录....");
        Post[] posts = initHomePage.GetFriendInvitationHadSentFromDataSource(out, buf);
        MenuContent.getGets(posts);  //发送所有请求
        return true;
    }

    @Override
    public boolean GetFriendIconHadSent(PrintStream out, BufferedReader buf) {
        log.info("获得已发送邀请的好友头像....");
        initHomePage.GetFriendIconHadSentFromDataSource(out,buf);
        MenuContent.setContextGet();
        return true;
    }

    @SneakyThrows
    @Override
    public synchronized boolean GetListOfFriends(PrintStream out, BufferedReader buf) {
        new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                log.info("正在获取好友列表....");
                Constant.isGettingListOfFriends = true;
                Friends[] friends = initHomePage.GetListOfFriendsFromDataSource(out, buf);
                getChat(friends, true);  //把得到的全部好友（姓名+聊天表位置）传给getChat

                log.info("获取好友列表成功!");


            }
        }.start();
        Thread.sleep(1000);
        return true;

    }

    @SneakyThrows
    @Override
    public boolean GetListOfFriendsAmountOnly(PrintStream out, BufferedReader buf) {

        new Thread() {
            @Override
            public void run() {
                Friends[] friends = initHomePage.GetListOfFriendsFromDataSource(out, buf);
                getChat(friends, false);  //把得到的全部好友（姓名+聊天表位置）传给getChat
            }
        }.start();
        Thread.sleep(1000);
        return true;
    }

    @SneakyThrows
    @Override
    public synchronized boolean GetFriendsIcon(PrintStream out, BufferedReader buf) {
        new Thread() {
            @Override
            public void run() {
                log.info("获取好友的头像....");
                BufferedImage[] icons = initHomePage.GetFriendsIconFromDataSource(out, buf);
                MenuContent.setContextChat(icons);
            }
        }.start();
        Thread.sleep(1000);
        return true;
    }

    @SneakyThrows
    @Override
    public boolean GetFriendsState(PrintStream out, BufferedReader buf) {
        log.info("获取好友的在线状态....");
        new Thread() {
            @Override
            public void run() {
                int[] state = initHomePage.GetFriendsStateFromDataSource(out, buf);
                MenuContent.setStateIcon(state);  //传入状态
            }
        }.start();
        Thread.sleep(1000);
        return true;
    }

    @SneakyThrows
    @Override
    public synchronized void GetChatHistoryAndHistoryAmount(String friend, Socket clien) {
        ArrayList<History> oldHistroys = historysFactory.get(friend);  //上一次的历史记录
        ArrayList<History> histories=null;    //新的历史记录

        PrintStream out = new PrintStream(
                clien.getOutputStream());  //向服务器端输出信息
        BufferedReader buf = new BufferedReader(new InputStreamReader(clien.getInputStream())); //接收服务器返回的信息
        //获取是在跟谁聊天

        int index = userContent.get(friend);//获取表下标

        InnerLabel friendContext = Home.chatContent[index];  //获取聊天界面

        histories = initHomePage.GetChatHistoryAndHistoryAmountFromDataSource(out, buf, friend,index);
        String message;

        //判断如果记录是否比之前数量多
        if (histories.size() > historyAmount.get(friend)) {  //数量确实多

            //将对方发送来的消息显示在聊天窗口上
            for (int j = historyAmount.get(friend); j < histories.size(); j++) {
                if ("get".equals(histories.get(j).type)) {  //我获取信息
                    message = histories.get(j).message;  //获取别人发送的信息

                    friendContext.send(InnerLabel.Type.LEFT, message, friendContext.friend);
                    if (index == OPENINDEX[0]) {  //如果当前在聊天界面
                        musicService.playAcceptMessageMP3();
                    } else {
                        musicService.playNoticeMP3();
                    }
                }
            }

            //将状态红点标红：
            messageIcon[userContent.get(friend)].setColor(new Color(227, 34, 34));

        }

        historyAmount.put(friend, histories.size());  //记录该用户的历史记录数量
        historysFactory.put(friend, histories);  //将新历史记录保存


    }

    @SneakyThrows
    @Override
    public void GetChatHistory(String friend, Socket clien) {
        ArrayList<History> histroys = null;  //记录所有历史记录
        PrintStream out = new PrintStream(
                clien.getOutputStream());  //向服务器端输出信息
        BufferedReader buf = new BufferedReader(new InputStreamReader(clien.getInputStream())); //接收服务器返回的信息

        log.info("正在查询-" + friend + "-的聊天记录....");
        histroys=initHomePage.GetChatHistoryFromDataSource(out, buf, friend);
        historyAmount.put(friend, histroys.size());  //记录该用户的历史记录数量
        historysFactory.put(friend, histroys);  //存储聊天记录全部--对应某个好友
    }
}
