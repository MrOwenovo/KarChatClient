package KarChat.Client;

import KarChat.Chat.Helper.GetPicture;
import KarChat.Chat.HomePage.Home;
import KarChat.Chat.HomePage.Label.InnerLabel;
import KarChat.Chat.HomePage.Menu;
import KarChat.Chat.HomePage.MenuContent;
import KarChat.Chat.Login.LoginHome;
import KarChat.Chat.Login.RadioJLabel;
import KarChat.Chat.Sound.PlaySound;
import KarChat.Server.DataBase.Entry.Friends;
import KarChat.Server.DataBase.Entry.Post;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.*;

import static KarChat.Chat.HomePage.MenuContent.*;
import static KarChat.Chat.Login.LoginHome.*;

/**
 * 客户端程序,向服务器发送请求，并接收客户端的反馈，服务器在EchoThread类里处理信息
 */
@Log
public class EchoClient{

    public static boolean login =false;  //判断是否登录
    public static boolean check =false;  //判断是否存在用户
    public static boolean getIcon =false;  //判断是否更换头像
    public static boolean get =false;  //获取所有请求
    public static boolean post =false;  //获取所有发送
    public static boolean getSbIcon =false;  //获取某人头像
    public static boolean getSbIconGet =false;  //获取某人头像
    public static boolean addFriend =false;  //加好友
    public static boolean remember =false;  //是否记住密码
    public static boolean addState =false;  //是否修改addFriend中的state
    public static String addStateName ;  //是否修改addFriend中的被邀请者
    public static String deleteAddFriendName ;  //删除好友邀请的好友姓名
    public static boolean deleteAddFriend=false ;  //是否删除好友邀请
    public static boolean checkFriends=false ;  //查看用户的所有好友
    public static boolean getFriendIcon=false ;  //获得好友的头像
    public static boolean getUserState=false ;  //获得好友在线状态
    public static boolean getChatHistory=false ;  //获得聊天历史记录
    private static Friends[] friends;
    private static Socket clien;
    public static String[] usernameAll=new String[1];
    public static String[] passwordAll=new String[1];
    static boolean historyIsFlash=true;  //是否开始刷新聊天记录

    public static void main(String[] args) throws Exception{
        //指定连接主机及端口
        try {
            clien = new Socket("localhost", 8888);
        }catch (ConnectException e) {
            LoginHome.isAlive = false;
            new LoginHome();  //打开客户端登录窗口
            LoginHome.wrongMessage.setTextDynamic("服务器未连接");
            load.add(sign);
            sign.setBounds(-10,10,155,155);
            load.show();

            while (true) {
                Thread.sleep(100);
                if (login) {  //若登录则提示未连接服务器
                    LoginHome.wrongMessage.shake();
                    LoginHome.loginLabel.shake();
                    new Thread(){
                        @SneakyThrows
                        @Override
                        public void run() {
                            PlaySound.play("sound/error.mp3");
                        }
                    }.start();
                    login = false;
                }
                if (check) {  //若注册则显示未连接服务器
                    LoginHome.wrongMessage.shake();
                    LoginHome.registerLabel5.shake();
                    new Thread(){
                        @SneakyThrows
                        @Override
                        public void run() {
                            PlaySound.play("sound/error.mp3");
                        }
                    }.start();
                    check = false;
                }
            }
        }
        try (
                PrintStream out = new PrintStream(
                        clien.getOutputStream());  //向服务器端输出信息
                BufferedReader buf = new BufferedReader(new InputStreamReader(clien.getInputStream())); //接收服务器返回的信息
        ) {
            new LoginHome();  //打开客户端登录窗口
            {  //判断是否储存密码
                try (Scanner sc=new Scanner(new FileReader("userMessage"))) {
                    while (sc.hasNextLine()) {
                        String user = sc.nextLine();
                        String pass = sc.nextLine();
                        username.setText(user);
                        password.setText(pass);
                        passwordMessage.setText("");
                    }

                } catch (FileNotFoundException e) {
                    System.out.println("EchoClient:没有保存的账号密码文件");
                }

            }

            boolean ifStartFlash=true;  //是否是第一次开始刷新
            label:
            {
                usernameAll = new String[1];  //账号
                passwordAll = new String[1];  //密码
                while (true) {
                    Thread.sleep(100);  //加入多次点击延迟,防止卡服
                    if (login) {  //判断提交事件是否发生
                        loadIn.add(sign);  //加入加载条
                        sign.setBounds(-10,10,155,155);
                        loadIn.setColor(new Color(115, 175, 197));
                        LoginHome.wrongMessage.setTextDynamic("登陆中");
                        wrongMessage.setForeground(new Color(115, 175, 197));
                        loadIn.show();
                        Thread.sleep(1000);

                        LoginHome.checkLogin(message -> {  //发送到服务器，并判断是否存在该用户
                            out.println("login");  //输出给服务器要进行的功能
                            out.println(message[0]);  //向服务器发送账号
                            out.println(message[1]);  //向服务器发送密码
                            usernameAll[0] = message[0];
                            passwordAll[0] = message[0];
                        });
                        String message = buf.readLine();
                        if ("true".equals(message)) {
                            System.out.println("登录成功");
                            new Thread(){
                                @SneakyThrows
                                @Override
                                public void run() {
                                    PlaySound.play("sound/loginsuccess.mp3");
                                }
                            }.start();
                            {  //判断是否保存密码，保存了则储存密码
                                if (remember) {  //保存密码
                                    PrintStream userMessage = new PrintStream(new FileOutputStream("userMessage"));
                                    userMessage.println(usernameAll[0]);
                                    userMessage.println(passwordAll[0]);
                                }
                            }
                            LoginHome.background.dispose();  //登录成功关闭页面
                            //进入新的界面
                            break label; //退出登录功能
                        } else if ("already".equals(message)) {
                            loadIn.setColor(new Color(0,0,0,0));
                            wrongMessage.setForeground(new Color(215, 27, 71, 205));
                            LoginHome.loginLabel.shake();  //错误后让按钮抖动
                            LoginHome.wrongMessage.setTextDynamic("账号已经登录");
                            LoginHome.wrongMessage.shake();
                            new Thread(){
                                @SneakyThrows
                                @Override
                                public void run() {
                                    PlaySound.play("sound/error.mp3");
                                }
                            }.start();
                            login = false;  //登录失败标志位重置为false
                        } else {
                            loadIn.setColor(new Color(0,0,0,0));
                            wrongMessage.setForeground(new Color(215, 27, 71, 205));
                            LoginHome.loginLabel.shake();  //错误后让按钮抖动
                            LoginHome.wrongMessage.setTextDynamic("账号不存在或密码错误");
                            LoginHome.wrongMessage.shake();
                            new Thread(){
                                @SneakyThrows
                                @Override
                                public void run() {
                                    PlaySound.play("sound/error.mp3");
                                }
                            }.start();
                            login = false;  //登录失败标志位重置为false
                        }
                    }
                    if (check) {  //判断提交事件是否发生
                        loadIn.add(sign);  //加入加载条
                        sign.setBounds(-10,10,155,155);
                        loadIn.setColor(new Color(115, 175, 197));
                        LoginHome.wrongMessage.setTextDynamic("注册中");
                        wrongMessage.setForeground(new Color(115, 175, 197));
                        loadIn.show();

                        LoginHome.checkLogin(message -> {  //发送到服务器，并判断是否存在该用户
                            out.println("login");  //输出给服务器要进行的功能
                            out.println(message[0]);  //向服务器发送账号
                            out.println(message[1]);  //向服务器发送密码
                            usernameAll[0] = message[0];
                            passwordAll[0] = message[1];
                        });
                        if ("true".equals(buf.readLine())) {
                            loadIn.setColor(new Color(0,0,0,0));
                            wrongMessage.setForeground(new Color(215, 27, 71, 205));

                            LoginHome.registerAlready();
                            new Thread(){
                                @SneakyThrows
                                @Override
                                public void run() {
                                    PlaySound.play("sound/error.mp3");
                                }
                            }.start();
                            check = false;
                        } else {
                            out.println("register");
                            out.println(usernameAll[0]);
                            out.println(passwordAll[0]);
                            out.println(LoginHome.iconString);
                            if (Objects.equals(buf.readLine(), "true")) {
                                Thread.sleep(1000);
                                LoginHome.registerFinish(usernameAll[0], passwordAll[0]);  //做出注册完反馈
                                new Thread(){
                                    @SneakyThrows
                                    @Override
                                    public void run() {
                                        PlaySound.play("sound/loginsuccess.mp3");
                                    }
                                }.start();
                            }
                            //创建好友表，以该用户名为表名
                            out.println("createFriendsTable");
                            check = false;  //登录失败标志位重置为false
                        }
                    }
                }

            }
            label2:
            {
                //打开客户端登录窗口
                new Home();

                while (true) {
                    Thread.sleep(100);  //加入多次点击延迟,防止卡服

                    if (getIcon) {
                        out.println("getIcon");
                        String iconString = buf.readLine();
                        BufferedImage icon = GetPicture.stringToImage(iconString);  //转成图片
                        Home.setIcon(icon);
                        getIcon = false;  //修改标签
                    }
                    if (get) {
                        out.println("get");
                        out.println(usernameAll[0]);
                        int length = Integer.parseInt(buf.readLine());
                        Post[] posts = new Post[length];
                        for (int i = 0; i < length; i++) {
                            String post = buf.readLine();
                            String get = buf.readLine();
                            posts[i] = new Post(post, get,null);  //获取每一个请求
                        }
                        MenuContent.getPosts(posts);  //发送所有请求
                        get = false;
                    }
                    if (getSbIcon) {

                        out.println("getSbIcon");
                        out.println(iconLength);
                        for (int i = 0; i < iconLength; i++) {
                            out.println(iconName[i]);
                            BufferedImage icon = GetPicture.stringToImage(buf.readLine());  //转成图片
                            MenuContent.images[i] = icon;
                        }
                        getSbIcon = false;
                        MenuContent.setContext();
//                        break label2;
                    }
                    if (getSbIconGet) {

                        out.println("getSbIconGet");
                        out.println(MenuContent.iconLengthGet);
                        for (int i = 0; i < MenuContent.iconLengthGet; i++) {
                            out.println(MenuContent.iconNameGet[i]);
                            BufferedImage icon = GetPicture.stringToImage(buf.readLine());  //转成图片
                            MenuContent.imagesGet[i] = icon;
                        }
                        getSbIconGet = false;
                        MenuContent.setContextGet();
//                        break label2;
                    }
                    if (addFriend) {
                        out.println("addFriend");
                        out.println(MenuContent.friendName);
                        System.out.println(MenuContent.friendName);
                        String bool=buf.readLine();
                        System.out.println(bool);
//
                        if ("true".equals(bool)) {
                            MenuContent.searchText.setText("已发送好友邀请");
                            MenuContent.searchText.setForeground(new Color(62, 171, 159));
                            new Thread(){
                                @SneakyThrows
                                @Override
                                public void run() {
                                    PlaySound.play("sound/loginsuccess.mp3");
                                }
                            }.start();
                        }else if ("false".equals(bool)){
                            MenuContent.searchText.setText("用户名输入错误");
                            MenuContent.searchText.setForeground(new Color(161, 19, 19));
                            new Thread(){
                                @SneakyThrows
                                @Override
                                public void run() {
                                    PlaySound.play("sound/error.mp3");
                                }
                            }.start();
                        }else if ("already".equals(bool)){  //已经存在了邀请
                            MenuContent.searchText.setText("已经发送过该邀请");
                            MenuContent.searchText.setForeground(new Color(161, 19, 19));
                            new Thread(){
                                @SneakyThrows
                                @Override
                                public void run() {
                                    PlaySound.play("sound/error.mp3");
                                }
                            }.start();
                        }
                        addFriend = false;
                    }
                    if (post) {
                        out.println("post");
                        out.println(usernameAll[0]);
                        int length = Integer.parseInt(buf.readLine());
                        Post[] posts = new Post[length];
                        for (int i = 0; i < length; i++) {
                            String post = buf.readLine();
                            String get = buf.readLine();
                            String state = buf.readLine();
                            posts[i] = new Post(post, get,state);  //获取每一个请求
                        }
                        MenuContent.getGets(posts);  //发送所有请求
                        post = false;
                    }
                    if (addState) {  //修改addFriend中的状态
                        new Thread() {
                            @SneakyThrows
                            @Override
                            public void run() {
                                out.println("addState");
                                out.println(addStateName);
                                addState = false;
                                String BooleanFlag = buf.readLine();
                                if ("true".equals(BooleanFlag)) {
                                    MenuContent.searchText.setText("已同意好友邀请");
                                    MenuContent.searchText.setForeground(new Color(62, 171, 159));
                                    new Thread(){
                                        @SneakyThrows
                                        @Override
                                        public void run() {
                                            PlaySound.play("sound/loginsuccess.mp3");
                                        }
                                    }.start();
                                }


                            }
                        }.start();

                    }
                    if (deleteAddFriend) {
                        new Thread(){
                            @Override
                            public void run() {
                                out.println("deleteAddFriend");
                                out.println(deleteAddFriendName);
                                MenuContent.searchText.setText("已拒绝好友邀请");
                                MenuContent.searchText.setForeground(new Color(102, 48, 180));
                                new Thread(){
                                    @SneakyThrows
                                    @Override
                                    public void run() {
                                        PlaySound.play("sound/loginsuccess.mp3");
                                    }
                                }.start();
                                deleteAddFriend = false;
                            }
                        }.start();
                    }
                    if (checkFriends) {
                        new Thread(){
                            @SneakyThrows
                            @Override
                            public void run() {
                                out.println("checkFriends");
                                int length = Integer.parseInt(buf.readLine());  //好友个数
                                friends = new Friends[length];
                                for (int i = 0; i < length; i++) {
                                    String friend = buf.readLine();
                                    String getChatLocation = buf.readLine();
                                    friends[i] = new Friends(0,friend,getChatLocation);
                                }
                                System.out.println(friends.length);
                                MenuContent.getChat(friends);  //把得到的全部好友（姓名+聊天表位置）传给getChat

                            }
                        }.start();
                        checkFriends = false;
                    }
                    if (getFriendIcon) {
                        new Thread() {
                            @SneakyThrows
                            @Override
                            public void run() {
                                BufferedImage[] icons=new BufferedImage[friends.length];
                                out.println("getFriendIcon");
                                out.println(friends.length);
                                for (int i = 0; i < friends.length; i++) {
                                    out.println(friends[i].getFriends());
                                    BufferedImage icon = GetPicture.stringToImage(buf.readLine());  //转成图片
                                    icons[i] = icon;
                                }
                                MenuContent.setContextChat(icons);
                            }
                        }.start();
                        getFriendIcon = false;
                    }
                    if (getUserState) {
                        System.out.println("运行到1");
                        new Thread() {
                            @SneakyThrows
                            @Override
                            public void run() {
                                System.out.println("运行到5");
                                out.println("getUserState");
                                int state[] = new int[friends.length];
                                for (int i = 0; i < friends.length; i++) {
                                    out.println(friends[i].getFriends());
                                    state[i] = Integer.parseInt(buf.readLine());
                                }
                                System.out.println(state[0]);
                                System.out.println(Arrays.toString(state));
                                MenuContent.setStateIcon(state);  //传入状态
                            }
                        }.start();
                        getUserState = false;
                    }

                    {
                        if (ifStartFlash) {
                            //执行一次刷新好友列表以及获取历史记录
                            new Thread() {
                                @SneakyThrows
                                @Override
                                public void run() {
                                    Timer timer=new Timer();
                                    timer.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            if (!Menu.isClick1_1[0]) {
                                                System.out.println("刷新了一次");
                                                //刷新get画布
                                                for (int i = 0; i < iconLength; i++) {  //清空画布,为下一次刷新做准备
                                                    contextGet.remove(labels[i]);
                                                }
                                                height = 0;  //刷新高度
                                                contextGet.repaint();  //刷新面板
                                                EchoClient.get = true;  //获取请求
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
                                                EchoClient.post = true;  //获取发送
                                                try {
                                                    Thread.sleep(500);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                                for (int i = 0; i < iconLengthGet; i++) {  //刷新画布
                                                    labelsGet[i].repaint();
                                                }
                                            }
                                        }
                                    },8000,8000);




                                }
                            }.start();
                            ifStartFlash=false;
                        }

                    }
//                    i  f (Objects.equals(buf.readLine(), "getMessage")) {  //若接收到getMessage,则是有人发消息
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
            }

        }
        }

    /**
     * 向服务器发送信息
     * @param message
     * @param geter
     */
    @SneakyThrows
    public static void send(String message, String geter) {
        System.out.println("我发送了1");
         PrintStream out = new PrintStream(
                clien.getOutputStream());  //向服务器端输出信息
            out.println("send");
            out.println(message);
            out.println(geter);
            out.println(usernameAll[0]);
        }

    /**
     * 查询聊天历史记录
     */
    @SneakyThrows
    public static void getChatHistory(String friend) {
        System.out.println("获取历史记录1");
        PrintStream out = new PrintStream(
                clien.getOutputStream());  //向服务器端输出信息
              BufferedReader buf = new BufferedReader(new InputStreamReader(clien.getInputStream())); //接收服务器返回的信息

            out.println("getChatHistory");
            out.println(usernameAll[0]); //发送我的姓名
            out.println(friend);  //发送朋友的姓名

        //获取是在跟谁聊天
        System.out.println("找到聊天下标");
        int index = userContent.get(friend);//获取表下标
        System.out.println("找到聊天下标:"+index);

        InnerLabel friendContext = Home.chatContent[index];  //获取聊天界面
        int length = 0;
        try {
            length = Integer.parseInt(buf.readLine());  //消息长度
            System.out.println("长度:" + length);
        } catch (NumberFormatException e) {
            log.info(friend+"没有聊天记录");
        }

        String message = null; //接收的信息
        for (int i = 0; i < length; i++) {
            String type = buf.readLine();   //获取是发送信息还是获取信息
            System.out.println(type);
            switch (type) {
                case "post":  //我发送信息
                    message = buf.readLine();  //获取发送的信息
                    System.out.println("post:"+message);

                    friendContext.send(InnerLabel.Type.RIGHT,message,friendContext.mine);
                    break;
                case "get":  //我获取信息
                    message = buf.readLine();  //获取别人发送的信息
                    System.out.println("get:"+message);

                    friendContext.send(InnerLabel.Type.LEFT,message,friendContext.friend);
                    break;
            }
            if (i == length - 1&&length!=0) {  //如果是最后一句，显示在主界面的最新消息
                latestMessages[index].setTextDynamic(message);
            }
        }

        }

}
