package com.Karchat.service.impl;

import com.Karchat.util.Controller.Controller;
import com.Karchat.service.BackgroundService;
import com.Karchat.service.ViewServer;
import com.Karchat.util.ComponentUtil.CompositeComponent.Menu;
import com.Karchat.util.Constant;
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
                        if (!Menu.isClick1_1[0] && !isCheckingHistory && !isSending && !isAddingFriends&&!isFlashing&&whetherBackgroundCanEnabled&&initFinishAndCanFlashChatHistory) {  //需要不在加好友界面，并且不在进行查找历史记录
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
                                    if (!isGetIconsFinished&&!isGetFinished && !isPostFinished&&!isPostIconsFinished) {
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
                        if (!isFlashing && !isSending && !isAddingFriends&&!isCheckingHistory&&whetherBackgroundCanEnabled&&initFinishAndCanFlashChatHistory) {  //需要在不刷新加好友页面时执行,并不能进行发送
                            isCheckingHistory = true;  //正在查询记录
                            isGetFriendAmount = true;
                            log.info("--正在检查聊天记录--");

                            Constant.checkFriendsNameOnly = true;  //获取请求

                            label:
                            {
                                while (true) {
                                    Thread.sleep(1000);
                                    if (!isGetFriendAmount&&whetherFriendsToTableIndex) {
                                        for (int i = 0; i < iconLengthChat; i++) {
                                            try {
                                                context.getBean(Controller.class).getChatHistoryAmount(iconNameChat[i]);  //统计聊天记录内容
                                            } catch (IOException e) {
                                                viewServer.ServerClosed();  //服务器关闭方法
                                                e.printStackTrace();
                                            }
                                        }
                                        while (true) {
                                            if (isSomeBodyFinished.get(iconNameChat[iconLengthChat - 1])) {
                                                isCheckingHistory = false;  //查询完成
                                                log.info("--查询聊天记录完成--");
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

}
