package com.Karchat.server;

import com.Karchat.dao.mapper.AddFriendMapper;
import com.Karchat.dao.mapper.ChatMapper;
import com.Karchat.dao.mapper.LoginMapper;
import com.Karchat.entity.*;
import com.Karchat.util.BeansUtil.KarConfiguration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Objects;

import static com.Karchat.server.EchoThreadServer.table;


/**
 * 服务器处理类,加入多线程机制,在此类中处理客户端发来的数据，并返回给客户端
 */
@Slf4j
public class EchoThread implements Runnable {  //实现Runnable接口
    public static ApplicationContext context = new AnnotationConfigApplicationContext(KarConfiguration.class);

    private Socket client = null;  //接收客户端
    public static boolean exit = false;  //判断是否退出
    public static String username = null;  //我的姓名
    private Post[] posts;//好友列表
    private Friends[] friends;
    public int index;  //判断是服务器的第几个客户端

    public EchoThread(Socket client) {  //通过构造方法设置Socket
        this.client = client;
    }

    @SneakyThrows
    @Override
    public void run() {
        //用try-with-resource处理
        try (PrintStream out = new PrintStream(
                client.getOutputStream());  //实例化客户端的输出流
             //用于接收客户端发来的信息
             BufferedReader buf = new BufferedReader(  //得到客户端的输入信息
                     new InputStreamReader(client.getInputStream()));) {

            boolean flag = true;  //标志位，表示一个客户端是否操作完毕
            while (flag) {
                String str = buf.readLine();  //在此处不断的接收信息
                switch (str) {
                    case "login":
                        username = buf.readLine();  //读取用户名
                        String password = buf.readLine();  //读取密码

                        String finalUsername = username;
                        LoginMapper mapper = context.getBean(LoginMapper.class);
                            User user = mapper.checkLogin(finalUsername, password);
                            if (user != null) {
                                //判断该用户是否已经登录
                                if (user.getState() != 1) {  //若未登录
                                    log.info(client.getInetAddress() + ": " + client.getPort() + " 登录成功");
                                    out.println("true");

                                        mapper.updateState(1, finalUsername);
                                        //在数据库中记录用户名和客户端下标的关系
                                        mapper.insertIndex(username, index);
                                    new Thread() {  //新建加入用户的线程，防止影响登录时间
                                        @Override
                                        public void run() {
                                            table.setAdd(client.getInetAddress().toString(), String.valueOf(client.getPort()), username, "在线");
                                            new Thread(table).start();  //新建添加用户的子线程
                                        }
                                    }.start();


                                } else {
                                    out.println("already");
                                }
                            } else {
                                out.println("false");
                            }
                        break;
                    case "register":
                        String username2 = buf.readLine();  //读取用户名
                        String password2 = buf.readLine();  //读取密码
                        String iconString = buf.readLine();  //读取用户头像
                        LoginMapper mapper1 = context.getBean(LoginMapper.class);
                        int i = mapper1.register(username2, password2, iconString);
                            if (i == 1) {
                                out.println("true");
                            }
                        break;
                    case "getIcon":
                        LoginMapper mapper2 = context.getBean(LoginMapper.class);
                            Icon icon = mapper2.getIcon(username);
                            out.println(icon.getIconString());
                        break;
                    //识别要加谁为好友,判断是否已经发送
                    case "addFriend":
                        String friendName = buf.readLine();
                        final Post[] post = new Post[1];
                        //先查询一下加好友列表里是否已经存在了这一对组合
                        AddFriendMapper addFriendMapper = context.getBean(AddFriendMapper.class);


                            post[0] = addFriendMapper.checkPostIsExist(username, friendName);

                        if (post[0] == null) {  //加好友列表中不存在该邀请
                            //加好友
                            AddFriendMapper addFriendMapper1 = context.getBean(AddFriendMapper.class);

                                int i1 = addFriendMapper1.addFriend(username, friendName);
                                if (i1 == 1) {
                                    out.println("true");
                                } else {
                                    out.println("false");

                                }
                            out.println("finish");
                            break;
                        }
                    {  //加好友列表中已经存在了该邀请
                        out.println("already");
                        out.println("finish");

                    }
                    break;

                    //获取所有好友请求
                    case "get":
                        AddFriendMapper addFriendMapper1 = context.getBean(AddFriendMapper.class);

                            try {
                                posts = addFriendMapper1.checkGet(buf.readLine());
                                if (posts != null) {
                                    out.println(posts.length);
                                    for (int i2 = 0; i2 < posts.length; i2++) {
                                        out.println(posts[i2].getPost());
                                        out.println(posts[i2].getGeter());
                                    }
                                }
                                out.println("true");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        break;
                    case "post":
                        AddFriendMapper addFriendMapper2 = context.getBean(AddFriendMapper.class);

                            try {
                                Post[] posts = addFriendMapper2.checkPost(buf.readLine());
                                if (posts != null) {
                                    out.println(posts.length);
                                    for (int i3 = 0; i3 < posts.length; i3++) {
                                        out.println(posts[i3].getPost());
                                        out.println(posts[i3].getGeter());
                                        out.println(posts[i3].getState());
                                    }
                                }
                                out.println("true");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        break;
                    case "getSbIcon":
                        int length = Integer.parseInt(buf.readLine());  //读取用户的数量
                        LoginMapper mapper3 = context.getBean(LoginMapper.class);
                        for (int q = 0; q < length; q++) {
                            String iconName = buf.readLine();
                                Icon icon2 = mapper3.getIcon(iconName);
                                out.println(icon2.getIconString());
                        }
                        out.println("true");
                        break;
                    case "getSbIconGet":
                        int lengthGet = Integer.parseInt(buf.readLine());  //读取已发送用户的数量
                        for (int j = 0; j < lengthGet; j++) {
                            String iconNameGet = buf.readLine();
                            LoginMapper mapper4 = context.getBean(LoginMapper.class);
                                Icon icon3 = mapper4.getIcon(iconNameGet);
                                out.println(icon3.getIconString());
                        }
                        out.println("true");
                        break;
                    case "createFriendsTable":
                        AddFriendMapper addFriendMapper3 = context.getBean(AddFriendMapper.class);

                            //这里由于使用${},所以账号不能是纯数字，加入判断，如果是纯数字就加个D在前面
                            if (username.matches("^[0-9]*$")) {  //如果账号是纯数字
                                addFriendMapper3.createFriendsTable("_" + username);  //创建以自己为表名的好友表，用来存放好友
                            } else {
                                addFriendMapper3.createFriendsTable(username);  //创建以自己为表名的好友表，用来存放好友
                            }
                        break;
                    case "addState":
                        new Thread() {
                            @SneakyThrows
                            @Override
                            public void run() {
                                String addStateName = buf.readLine();
                                AddFriendMapper addFriendMapper4 = context.getBean(AddFriendMapper.class);

                                    int i = addFriendMapper4.updateAddState(addStateName, username);
                                    if (i == 1) {
                                        out.println("true");
                                    }

                                //在两个人的好友表中添加对方
                                    if (username.matches("^[0-9]*$")) {  //如果自己账号是纯数字
                                        addFriendMapper4.addFriendName("_" + username, addStateName);  //给自己的好友表中添加人名
                                    } else {
                                        addFriendMapper4.addFriendName(username, addStateName);  //给自己的好友表中添加人名
                                    }
                                    if (addStateName.matches("^[0-9]*$")) {  //如果对方账号是纯数字
                                        addFriendMapper4.addFriendName("_" + addStateName, username);  //给对方的好友表中添加自己
                                    } else {
                                        addFriendMapper4.addFriendName(addStateName, username);  //给对方的好友表中添加自己
                                    }

                                //创建聊天表，并把表名存储到两个人的好友表中
                                    String chatLocation = username + "_" + addStateName;  //聊天表名
                                    addFriendMapper4.createChatTable(chatLocation);
                                    //加入两个人的表
                                    if (username.matches("^[0-9]*$")) {  //如果自己账号是纯数字
                                        addFriendMapper4.updateChatLocation("_" + username, chatLocation, addStateName);
                                    } else {
                                        addFriendMapper4.updateChatLocation(username, chatLocation, addStateName);
                                    }
                                    if (addStateName.matches("^[0-9]*$")) {  //如果好友账号是纯数字
                                        addFriendMapper4.updateChatLocation("_" + addStateName, chatLocation, username);
                                    } else {
                                        addFriendMapper4.updateChatLocation(addStateName, chatLocation, username);
                                    }


                            }
                        }.start();
                        break;
                    case "deleteAddFriend":
                        new Thread() {
                            @SneakyThrows
                            @Override
                            public void run() {
                                String deleteAddFriendName = buf.readLine();
                                AddFriendMapper addFriendMapper4 = context.getBean(AddFriendMapper.class);

                                    addFriendMapper4.deleteFriendName(deleteAddFriendName, username);
                            }
                        }.start();
                        break;
                    case "checkFriends":
                        new Thread() {
                            @Override
                            public void run() {
                                AddFriendMapper addFriendMapper4 = context.getBean(AddFriendMapper.class);

                                    if (username.matches("^[0-9]*$")) {  //如果好友账号是纯数字
                                        friends = addFriendMapper4.checkFriends("_" + username);
                                    } else {
                                        friends = addFriendMapper4.checkFriends(username);
                                    }
                                    out.println(friends.length);  //发送长度
                                    for (int i = 0; i < friends.length; i++) {
                                        out.println(friends[i].getFriends());
                                        out.println(friends[i].getChatLocation());
                                    }

                                    out.println("true");

                            }
                        }.start();
                        break;
                    case "getFriendIcon":
                        int ChatLength = Integer.parseInt(buf.readLine());  //读取用户的数量
                        for (int w = 0; w < ChatLength; w++) {
                            String iconName = buf.readLine();
                            LoginMapper mapper5 = context.getBean(LoginMapper.class);
                                Icon icon4 = mapper5.getIcon(iconName);
                                out.println(icon4.getIconString());
                        }
                        break;
                    case "getUserState":
                        AddFriendMapper addFriendMapper4 = context.getBean(AddFriendMapper.class);

                            for (int r = 0; r < friends.length; r++) {
                                String friendsName = null;
                                try {
                                    friendsName = buf.readLine();
                                } catch (IOException a) {
                                    a.printStackTrace();
                                }
                                User user2 = addFriendMapper4.getUserState(friendsName);
                                out.println(user2.getState());
                            }

                        break;
                    case "send":
//
                        Thread.sleep(100);
                        String sendMessage = buf.readLine();  //要发送的信息
                        String sendToName = buf.readLine();  //发送给的人
                        String myName = buf.readLine();  //发送的人
                        log.info("-"+myName+"-发送: "+sendMessage+" 给-"+sendToName+"-");
                        //先查聊天表名
                        final String[] mychatLocation = new String[1];  //聊天表位置
                        final Friends[] myfriend = new Friends[1];
                        ChatMapper chatMapper = context.getBean(ChatMapper.class);
                            if (myName.matches("^[0-9]*$")) {
                                myfriend[0] = chatMapper.getChatLocation("_" + myName, sendToName);
                            } else {
                                myfriend[0] = chatMapper.getChatLocation(myName, sendToName);
                            }
                            mychatLocation[0] = myfriend[0].getChatLocation();
                        final int[] q = new int[1];
                        //存到聊天表里
                            q[0] = chatMapper.insertMessage(mychatLocation[0], myName, sendToName, sendMessage);  //添加信息

                        out.println("true");


                        break;
                    case "getMessage":  //接收服务器发来的信息
                        new Thread() {
                            @SneakyThrows
                            @Override
                            public void run() {
                                String message = buf.readLine();  //接收发送的信息
                                String sendName = buf.readLine();  //接收发送人姓名
                                //发送给这个好友，调用inner里的send显示
                                out.println("getMessage");
                                out.println(message);  //发送信息
                                out.println(sendName);  //发送发送人姓名
                            }
                        }.start();
                        break;
                    case "getChatHistory":  //获取聊天历史内容
                        //先查聊天表名
                        final String[] chatLocation = new String[1];  //聊天表的位置
                        String me = buf.readLine();  //我的姓名
                        String friend = buf.readLine();  //好友的姓名
                        ChatMapper chatMapper1=context.getBean(ChatMapper.class);
                            if (me.matches("^[0-9]*$")) {
                                chatLocation[0] = chatMapper1.getChatLocation("_" + me, friend).getChatLocation();
                            } else {
                                chatLocation[0] = chatMapper1.getChatLocation(me, friend).getChatLocation();
                            }

                        //获取聊天表中的全部内容
                        try {
                                Message[] chatHistory = chatMapper1.getChatHistory(chatLocation[0]);//获取全部聊天内容
                                //发送给客户端
                                out.println(chatHistory.length);  //先发送长度

                                for (int s = 0; s < chatHistory.length; s++) {
                                    //先判断是我发送的还是我接收的
                                    if (Objects.equals(chatHistory[s].getUser1(), me)) {  //是我发送的
                                        out.println("post");
                                        out.println(chatHistory[s].getMessage());  //发送信息

                                    } else {  //是我接收的
                                        out.println("get");
                                        out.println(chatHistory[s].getMessage());  //发送信息

                                    }
                                }

                            out.println("true");
                        } catch (Exception e) {
                            log.info("聊天表" + chatLocation[0] + "不存在");
                        }

                        break;


                }

            }

        } catch (SocketException | NullPointerException e) {
            log.info(this.client.getInetAddress() + "连接异常");

        } finally {
            String finalUsername1 = username;
            LoginMapper mapper1 = context.getBean(LoginMapper.class);
            mapper1.updateState(0, finalUsername1);
            mapper1.deleteIndex(username);  //移除客户端下标
            new Thread() {  //新建删除用户的线程
                @Override
                public void run() {
                    table.setExit(username);
                    new Thread(table).start();  //新建删除用户的子线程
                }
            }.start();
        }

    }

    /**
     * 从服务器获取信息
     */
    @SneakyThrows
    public void getMessage(String message) {
        PrintStream out = new PrintStream(
                client.getOutputStream());  //实例化客户端的输出流
    }

}
