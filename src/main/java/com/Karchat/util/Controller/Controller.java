package com.Karchat.util.Controller;

import com.Karchat.service.*;
import com.Karchat.util.Constant;
import com.Karchat.view.LoginHome;
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
import java.util.Timer;
import java.util.TimerTask;

import static com.Karchat.util.Constant.*;
import static com.Karchat.view.LoginHome.*;


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
    public static Timer firstTimer;

    public void start() throws InterruptedException {

        //指定连接主机及端口
        try {
            clien = new Socket("localhost", 8888);
//            clien = new Socket("103.46.128.46", 59614);
        } catch (IOException e) {
            e.printStackTrace();
            viewService.DisplayNotConnectedLogin();  //打开错误客户端
            //获取定时调度
            firstTimer = Constant.context.getBean(Timer.class);
            firstTimer.schedule(new TimerTask() {
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
            if (!isStart) {  //未打开再打开
                viewService.DisplayConnectedLogin();  //打开客户端登录窗口
            }
            loginService.CheckAccountRecords();  //查看本地是否有保存的账号密码

            Timer timer = Constant.context.getBean(Timer.class);
            final boolean[] endLogin = {false}; //是否结束登录阶段
            timer.schedule(new TimerTask() {  //加入多次点击延迟,防止卡服
                @SneakyThrows
                @Override
                public void run() {
                    try {
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
                    } catch (Exception e) {
//                            viewService.ServerClosed();  //服务器关闭方法
                        log.info("服务器已经关闭！");
                        loadIn.show();
                        loadIn.setColor(Color.red);
                        wrongMessage.setForeground(Color.RED);
                        LoginHome.loginLabel.shake();  //错误后让按钮抖动
                        LoginHome.wrongMessage.setTextDynamic("服务器未连接");
                        sign.setBounds(-10, 10, 155, 155);
                        login = false;
                        label3:
                        {
                            while (true) {
                                Thread.sleep(2000);
                                try {
                                    clien = new Socket("localhost", 8888);
                                    if (clien != null) {
                                        LoginHome.isAlive = true;
                                        isStart = true;
//                                        firstTimer.cancel();
                                        log.info("服务器已重新连接");
                                        new Thread() {
                                            @SneakyThrows
                                            @Override
                                            public void run() {
                                                wrongMessage.setForeground(new Color(115, 175, 197));

                                                LoginHome.wrongMessage.setTextDynamic("服务器已经正在重新连接.");
                                                Thread.sleep(500);
                                                LoginHome.wrongMessage.setTextDynamic("服务器已经正在重新连接..");
                                                Thread.sleep(500);
                                                LoginHome.wrongMessage.setTextDynamic("服务器已经正在重新连接...");
                                                Thread.sleep(1000);
                                                LoginHome.wrongMessage.setTextDynamic("服务器已经重新连接√");
                                                Thread.sleep(2000);
                                                LoginHome.wrongMessage.setTextDynamic("");
                                            }
                                        }.start();
                                        loadIn.setColor(new Color(115, 175, 197,0));
                                        loadIn.stopShow();
                                        start();
                                        break label3;
                                    }
                                } catch (IOException q) {
                                    //
                                }
                            }
                        }
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
                        if (Constant.addState) {  //同意加好友
                            Constant.addState = false;
                            boolean flag = chatService.AcceptFriendInvitation(out, buf);
                            while (!canGoOn[0]) {
                                if (flag)
                                    canGoOn[0] = true;
                            }
                            canGoOn[0] = false;
                        }
                        if (deleteAddFriend) {   //拒绝加好友
                            deleteAddFriend = false;
                            boolean flag = chatService.RefuseFriendInvitation(out, buf);
                            while (!canGoOn[0]) {
                                if (flag)
                                    canGoOn[0] = true;
                            }
                            canGoOn[0] = false;
                        }
                        if (checkFriends) {    //获取好友列表
                            checkFriends = false;
                            boolean flag = initHomePageService.GetListOfFriends(out, buf);
                            while (!canGoOn[0]) {
                                if (flag)
                                    canGoOn[0] = true;
                            }
                            canGoOn[0] = false;
                        }
                        if (checkFriendsNameOnly) {  //只获取好友数量
                            checkFriendsNameOnly = false;
                            boolean flag = initHomePageService.GetListOfFriendsAmountOnly(out, buf);
                            while (!canGoOn[0]) {
                                if (flag)
                                    canGoOn[0] = true;
                            }
                            canGoOn[0] = false;
                        }
                        if (getFriendIcon) {  //获取所有好友的头像
                            getFriendIcon = false;
                            boolean flag = initHomePageService.GetFriendsIcon(out, buf);
                            while (!canGoOn[0]) {
                                if (flag)
                                    canGoOn[0] = true;
                            }
                            canGoOn[0] = false;
                        }
                        if (getFriendIconNew) {  //更新好友列表标签
                            getFriendIconNew = false;
                            boolean flag = initHomePageService.GetFriendsIconNew(out, buf);
                            while (!canGoOn[0]) {
                                if (flag)
                                    canGoOn[0] = true;
                            }
                            canGoOn[0] = false;
                        }
                        if (getUserState) {  //获取好友的状态
                            getUserState = false;
                            getUserStateIsFinish = false;
                            initHomePageService.GetFriendsState(out, buf);
                            while (!canGoOn[0]) {
                                if (getUserStateIsFinish)
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
            label2:
            {
                while (true) {
                    Thread.sleep(2000);
                    try {
                        clien = new Socket("localhost", 8888);
                        if (clien != null) {

                            LoginHome.isAlive = true;
                            isStart = true;
                            firstTimer.cancel();
                            log.info("服务器已重新连接");
                            new Thread() {
                                @SneakyThrows
                                @Override
                                public void run() {
                                    wrongMessage.setForeground(new Color(115, 175, 197));

                                    LoginHome.wrongMessage.setTextDynamic("服务器已经正在重新连接.");
                                    Thread.sleep(500);
                                    LoginHome.wrongMessage.setTextDynamic("服务器已经正在重新连接..");
                                    Thread.sleep(500);
                                    LoginHome.wrongMessage.setTextDynamic("服务器已经正在重新连接...");
                                    Thread.sleep(1000);
                                    LoginHome.wrongMessage.setTextDynamic("服务器已经重新连接√");
                                    Thread.sleep(2000);
                                    LoginHome.wrongMessage.setTextDynamic("");
                                }
                            }.start();
                            load.stopShow();
                            load.setColor(new Color(255, 255, 255, 0));
                            start();
                            break label2;
                        }
                    } catch (IOException e) {
                        //
                    }
                }
            }
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
     * @param geter   接收的人
     */
    @SneakyThrows
    public void send(String message, String geter) {
        chatService.Send(message, geter, clien);

    }


    /**
     * 定时获取该好友的所有历史记录，以及记录历史数量
     *
     * @param friend 要查询的好友
     */
    public void getChatHistoryAmount(String friend) throws IOException {
        initHomePageService.GetChatHistoryAndHistoryAmount(friend, clien);
    }

    /**
     * 查询聊天历史记录
     *
     * @param friend 要查询的好友
     */
    @SneakyThrows
    public void getChatHistory(String friend) {
        initHomePageService.GetChatHistory(friend, clien);

    }


}
