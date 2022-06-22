package com.Karchat.service.impl;

import com.Karchat.service.BackgroundService;
import com.Karchat.service.ViewServer;
import com.Karchat.util.ComponentUtil.CompositeComponent.Menu;
import com.Karchat.util.Constant;
import com.Karchat.util.Controller.Controller;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static com.Karchat.util.ComponentUtil.CompositeComponent.MenuContent.*;
import static com.Karchat.util.Constant.*;

@Slf4j
@Service
public class BackgroundServiceImpl implements BackgroundService {

    @Resource
    ViewServer viewServer;


    @Override
    public void RefreshListOfFriendsToBeApprovedAndSent() {
        //执行一次刷新好友列表以及获取历史记录
        new Thread() {
            @Override
            public void run() {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (!Menu.isClick1_1[0] && !isCheckingHistory && !isSending && !isAddingFriends && !isFlashing && whetherBackgroundCanEnabled && initFinishAndCanFlashChatHistory && !isRefreshFriendsList&&!isAgreeFriends&&!isDisAgreeFriends&&!isRefreshingStates) {  //需要不在加好友界面，并且不在进行查找历史记录
                            log.info("--正在刷新好友邀请列表--");
                            isFlashing = true;
                            isGetFinished = true;
                            isGetIconsFinished = true;
                            isPostFinished = true;
                            isPostIconsFinished = true;
                            //刷新get画布
                            for (int i = 0; i < iconLength; i++) {  //清空画布,为下一次刷新做准备
                                contextGet.remove(labels[i]);
                            }
                            height = 0;  //刷新高度
                            contextGet.repaint();  //刷新面板
                            Constant.get = true;  //获取请求
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < iconLength; i++) {  //刷新画布
                                labels[i].repaint();
                            }

                            //刷新post画布
                            for (int i = 0; i < iconLengthGet; i++) {  //清空画布,为下一次刷新做准备
                                contextPost.remove(labelsGet[i]);
                            }
                            heightGet = 0;  //刷新高度
                            contextPost.repaint();  //刷新面板
                            Constant.post = true;  //获取发送
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < iconLengthGet; i++) {  //刷新画布
                                labelsGet[i].repaint();
                            }
                            labelWhile:
                            {
                                while (true) {
                                    if (!isGetIconsFinished && !isGetFinished && !isPostFinished && !isPostIconsFinished) {
                                        log.info("--好友列表刷新成功--");
                                        isFlashing = false;  //执行完刷新
                                        break labelWhile;
                                    }
                                }
                            }
                        }
                    }
                }, 18000, 8000);


            }
        }.start();
    }

    @Override
    public void RefreshChatHistory() {

        //*开启更新聊天线程:
        // 先获取好友数量，分别看每个好友是否有新消息
        // */
        new Thread() {
            @Override
            public void run() {
                Timer timer = context.getBean(Timer.class);
                timer.schedule(new TimerTask() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        if (!isFlashing && !isSending && !isAddingFriends && !isCheckingHistory && whetherBackgroundCanEnabled && initFinishAndCanFlashChatHistory && !isRefreshFriendsList&&!isAgreeFriends&&!isDisAgreeFriends&&!isRefreshingStates) {  //需要在不刷新加好友页面时执行,并不能进行发送
                            isCheckingHistory = true;  //正在查询记录
                            isGetFriendAmount = true;
                            log.info("--正在检查聊天记录--");

                            checkFriendsNameOnly = true;  //获取请求

                            label:
                            {
                                while (true) {
                                    Thread.sleep(1000);
                                    if (!isGetFriendAmount && whetherFriendsToTableIndex) {
                                        if (friendsAmount < iconLengthChat) {  //如果好友数量变多了，要先刷新好友聊天列表
                                            isRefreshFriendsList = true;
                                            RefreshFriendsList();  //更新好友聊天列表
                                            friendsAmount = iconLengthChat;
                                        }
                                        while (isRefreshFriendsList) {
                                            //正在更新好友列表，请等待....
                                            ;
                                        }
                                        for (int i = 0; i < iconLengthChat; i++) {
                                            try {
                                                context.getBean(Controller.class).getChatHistoryAmount(iconNameChat[i]);  //统计聊天记录内容
                                            } catch (IOException e) {
                                                viewServer.ServerClosed();  //服务器关闭方法
                                                e.printStackTrace();
                                            }
                                        }
                                        while (true) {
                                            if (iconNameChat.length == 0) {
                                                isCheckingHistory = false;  //查询完成
                                                log.info("--查询聊天记录完成--");
                                                getFriendStatesSuccess = false;  //刷新好友状态是否获得
                                                break label;
                                            }
                                            if (isSomeBodyFinished.get(iconNameChat[iconLengthChat - 1])) {
                                                RefreshFriendsState();  //刷新好友在线状态
                                                isCheckingHistory = false;  //查询完成
                                                log.info("--查询聊天记录完成--");
                                                getFriendStatesSuccess = false;  //刷新好友状态是否获得
                                                break label;
                                            }
                                        }
                                    }

                                }

                            }
                        }
                    }
                }, 20000, 5000);

            }
        }.start();
    }

    @Override
    public void RefreshFriendsList() {
        //执行一次刷新好友聊天列表标签
        log.info("--正在刷新好友聊天列表--");

        getFriendIconNew = true;
        isCreateNewInnerLabel = true;


        labelWhile:
        {
            while (true) {
                if (!isCreateNewInnerLabel && getFriendStatesSuccess) {
                    getFriendStatesSuccess = false;  //刷新好友头像获取
                    log.info("--好友列表刷新成功--");

                    isRefreshFriendsList = false;  //执行完刷新
                    break labelWhile;
                }
            }
        }
    }

    @Override
    public void RefreshFriendsState() {
        log.info("--正在更新好友在线状态--");
        isRefreshingStates = true;  //正在刷新好友在线状态
        getUserStateIsFinish = false;  //保证更新好友完成判断的标志为否，加一层保证
        getUserState = true;  //在controller中更新好友在线状态
        while (!getUserStateIsFinish) {
            //等待更新好友在线状态完成中....
        }
        isRefreshingStates = false;  //刷新好友在线状态还曾共
        log.info("--正在更新好友在线状态成功!--");


    }
}
