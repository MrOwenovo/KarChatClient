package com.Karchat.util.Controller;

import com.Karchat.entity.History;
import com.Karchat.service.*;
import com.Karchat.util.ComponentUtil.Label.InnerLabel;
import com.Karchat.util.Constant;
import com.Karchat.view.Home;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.Karchat.util.ComponentUtil.CompositeComponent.MenuContent.*;
import static com.Karchat.util.Constant.*;


/**
 * 控制层：
 * 客户端主要控制程序,向服务器发送请求，并接收客户端的反馈，服务器在EchoThread类里处理信息
 */
@Slf4j
@Component
public class Controller {

    //客户端相关操作判断
    private static Socket clien;


    @Resource
    public ViewServer viewService;
    @Resource
    LoginService loginService;
    @Resource
    InitHomePageService initHomePageService;
    @Resource
    ChatService chatService;
    @Resource
    BackgroundService backgroundService;

    public void start() throws InterruptedException {

        //指定连接主机及端口
        try {
            clien = new Socket("localhost", 8888);
        } catch (IOException e) {
            viewService.DisplayNotConnectedLogin();  //打开错误客户端
            Timer timer = Constant.context.getBean(Timer.class);  //获取定时调度
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (Constant.login) {
                        loginService.LoginWhenClientDisConnect();  //登录
                    }
                    if (Constant.register) {
                        loginService.RegisterWhenClientDisConnect();  //注册
                    }
                }
            }, 0, 100);
        }
        try (
                PrintStream out = new PrintStream(
                        clien.getOutputStream());  //向服务器端输出信息
                BufferedReader buf = new BufferedReader(new InputStreamReader(clien.getInputStream())); //接收服务器返回的信息
        ) {
            viewService.DisplayConnectedLogin();  //打开客户端登录窗口
            loginService.CheckAccountRecords();  //查看本地是否有保存的账号密码

            Timer timer = Constant.context.getBean(Timer.class);
            final boolean[] endLogin = {false}; //是否结束登录阶段
            timer.schedule(new TimerTask() {  //加入多次点击延迟,防止卡服
                @Override
                public void run() {
                    if (Constant.login) {  //判断提交事件是否发生
                        loginService.Login(out, buf);  //登录
                        if (Constant.loginSuccess) {
                            endLogin[0] = true; //
                            timer.cancel();
                        }
                        Constant.login = false;
                    }
                    if (Constant.register) {  //判断提交事件是否发生
                        loginService.Register(out, buf);
                        Constant.register = false;

                    }
                }
            }, 0, 100);

            while (!endLogin[0]) {

            }
            label2:
            {
                final boolean[] canGoOn = {false};
                viewService.DisplayConnectedClient();  //打开客户端主界面
                Timer timer1 = Constant.context.getBean(Timer.class);
                timer1.schedule(new TimerTask() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        if (Constant.getMyIcon) {  //获得我的头像
                            Constant.getMyIcon = false;  //修改标签
                            boolean flag = initHomePageService.GetMyIcon(out, buf);
                            while (!canGoOn[0]) {
                                if (flag)
                                    canGoOn[0] = true;
                            }
                            canGoOn[0] = false;
                        }
                        if (Constant.get) {  //获取待同意的好友邀请
                            Constant.get = false;
                            boolean flag = initHomePageService.GetFriendInvitationDidNotAgree(out, buf);
                            while (!canGoOn[0]) {
                                if (flag)
                                    canGoOn[0] = true;
                            }
                            canGoOn[0] = false;
                        }
                        if (Constant.getSbIcon) {  //获得待同意邀请的好友头像
                            Constant.getSbIcon = false;
                            boolean flag = initHomePageService.GetFriendIconDidNotAgree(out, buf);
                            while (!canGoOn[0]) {
                                if (flag)
                                    canGoOn[0] = true;
                            }
                            canGoOn[0] = false;
                        }
                        if (Constant.post) { //获取已发送的好友邀请记录
                            post = false;
                            boolean flag = initHomePageService.GetFriendInvitationHadSent(out, buf);
                            while (!canGoOn[0]) {
                                if (flag)
                                    canGoOn[0] = true;
                            }
                            canGoOn[0] = false;
                        }
                        if (Constant.postSbIcon) { //获得已发送邀请的好友头像
                            Constant.postSbIcon = false;
                            boolean flag = initHomePageService.GetFriendIconHadSent(out, buf);
                            while (!canGoOn[0]) {
                                if (flag)
                                    canGoOn[0] = true;
                            }
                            canGoOn[0] = false;
                        }
                        whetherBackgroundCanEnabled = true;  //可以开启后台线程
                        if (Constant.addFriend) {  //多线程加好友，会进入线程队列（后续优化：正在加好友时直接拒绝，不进入队列）
                            Constant.addFriend = false;
                            boolean flag = chatService.AddFriend(out, buf);
                            while (!canGoOn[0]) {
                                if (flag)
                                    canGoOn[0] = true;
                            }
                            canGoOn[0] = false;
                        }
                        if (Constant.addState) {  //修改addFriend中的状态
                            Constant.addState = false;
                            boolean flag = chatService.AcceptFriendInvitation(out, buf);
                            while (!canGoOn[0]) {
                                if (flag)
                                    canGoOn[0] = true;
                            }
                            canGoOn[0] = false;
                        }
                        if (deleteAddFriend) {
                            deleteAddFriend = false;
                            boolean flag = chatService.RefuseFriendInvitation(out, buf);
                            while (!canGoOn[0]) {
                                if (flag)
                                    canGoOn[0] = true;
                            }
                            canGoOn[0] = false;
                        }
                        if (checkFriends) {
                            checkFriends = false;
                            boolean flag = initHomePageService.GetListOfFriends(out, buf);
                            while (!canGoOn[0]) {
                                if (flag)
                                    canGoOn[0] = true;
                            }
                            canGoOn[0] = false;
                        }
                        if (checkFriendsNameOnly) {
                            checkFriendsNameOnly = false;
                            boolean flag = initHomePageService.GetListOfFriendsAmountOnly(out, buf);
                            while (!canGoOn[0]) {
                                if (flag)
                                    canGoOn[0] = true;
                            }
                            canGoOn[0] = false;
                        }
                        if (getFriendIcon) {
                            getFriendIcon = false;
                            boolean flag = initHomePageService.GetFriendsIcon(out, buf);
                            while (!canGoOn[0]) {
                                if (flag)
                                    canGoOn[0] = true;
                            }
                            canGoOn[0] = false;
                        }
                        if (getUserState) {
                            getUserState = false;
                            boolean flag = initHomePageService.GetFriendsState(out, buf);
                            while (!canGoOn[0]) {
                                if (flag)
                                    canGoOn[0] = true;
                            }
                            canGoOn[0] = false;
                        }

                    }
                }, 0, 2000);

                Thread.sleep(5000);
                //开启后台线程，执行一次刷新好友列表以及获取历史记录
                backgroundService.RefreshListOfFriendsToBeApprovedAndSent();


                //*开启更新聊天线程:
                // 先获取好友数量，分别看每个好友是否有新消息
                // */
                backgroundService.RefreshChatHistory();
            }
            while (true) {

            }


        } catch (IOException ex) {
            viewService.ServerClosed();  //服务器关闭方法
            log.info("服务器已经关闭！");
        } catch (NullPointerException ex) {
            log.info("服务器未连接，无法开打主界面");
        }
//            i  f (Objects.equals(buf.readLine(), "getMessage")) {  //若接收到getMessage,则是有人发消息
//                        String message = buf.readLine();  //获取发送内容
//                        String sendName = buf.readLine();  //获取发送人姓名
//                        int index = MenuContent.userContent.get(sendName);  //获取位于哪一页内容,下标
//                        InnerLabel friend = Home.chatContent[index];  //获取聊天内容界面
//                        friend.send(InnerLabel.Type.LEFT,message,friend.friend);  //发送信息
//
//                        //加一个消息的红点，提示已经有新消息发送
//                        messageIcon[index].setSize(8,8);  //设置红点信息
//                        messageIcon[index].repaint();
//
//                        //将最新消息显示在主界面
//                        latestMessages[index].setTextDynamic(message);
//                        latestMessages[index].repaint();
//                    }
    }


    /**
     * 向服务器发送信息
     *
     * @param message 发送的信息
     * @param geter 接收的人
     */
    @SneakyThrows
    public void send(String message, String geter) {
        chatService.Send(message, geter,clien);

    }


    /**
     * 定时获取该好友的所有历史记录，以及记录历史数量
     *
     * @param friend 要查询的好友
     */
    public void getChatHistoryAmount(String friend) throws IOException {
       initHomePageService.GetChatHistoryAndHistoryAmount(friend,clien);
    }

    /**
     * 查询聊天历史记录
     * @param friend 要查询的好友
     */
    @SneakyThrows
    public void getChatHistory(String friend) {
        initHomePageService.GetChatHistory(friend,clien);

    }


}
