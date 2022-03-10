package KarChat.Server;

import KarChat.Chat.HomePage.MenuContent;
import KarChat.Chat.Login.LoginHome;
import KarChat.Server.DataBase.Entry.Friends;
import KarChat.Server.DataBase.Entry.Icon;
import KarChat.Server.DataBase.Entry.Post;
import KarChat.Server.DataBase.Entry.User;
import KarChat.Server.DataBase.MybatisUnit;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

import static KarChat.Chat.HomePage.MenuContent.iconName;
import static KarChat.Server.EchoThreadServer.table;

/**
 * 服务器处理类,加入多线程机制,在此类中处理客户端发来的数据，并返回给客户端
 */
public class EchoThread implements Runnable {  //实现Runnable接口
    private Socket client = null;  //接收客户端
    public static boolean exit = false;  //判断是否退出
    String username = null;
    private Post[] posts;//好友列表
    private Friends[] friends;

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
                        MybatisUnit.doSqlWork(mapper -> {
                            User user = mapper.checkLogin(finalUsername, password);
                            if (user != null) {
                                //判断该用户是否已经登录
                                if (user.getState() != 1) {  //若未登录
                                    System.out.println(client.getInetAddress()+": "+client.getPort()+" 登录成功");
                                    out.println("true");
                                    MybatisUnit.doSqlWork(mapper2 -> {
                                        mapper2.updateState(1, finalUsername);
                                    });
                                    new Thread() {  //新建加入用户的线程，防止影响登录时间
                                        @Override
                                        public void run() {
                                            table.setAdd(client.getInetAddress().toString(), String.valueOf(client.getPort()), username, "在线");
                                            new Thread(table).start();  //新建添加用户的子线程
                                        }
                                    }.start();

                                }else{
                                    out.println("already");
                                }
                            } else {
                                out.println("false");
                            }
                        });
                        break;
                    case "register":
                        String username2 = buf.readLine();  //读取用户名
                        String password2 = buf.readLine();  //读取密码
                        String iconString = buf.readLine();  //读取用户头像

                        MybatisUnit.doSqlWork(mapper->{
                            int i=mapper.register(username2, password2,iconString);
                            if (i == 1) {
                                out.println("true");
                            }
                        });
                        break;
                    case "getIcon":
                        MybatisUnit.doSqlWork(mapper->{
                            Icon icon=mapper.getIcon(username);
                            out.println(icon.getIconString());
                        });
                        break;
                        //识别要加谁为好友,判断是否已经发送
                    case "addFriend":
                        String friendName = buf.readLine();
                        final Post[] post = new Post[1];
                        //先查询一下加好友列表里是否已经存在了这一对组合
                        MybatisUnit.doChatWork(mapper->{
                            post[0] = mapper.checkPostIsExist(username, friendName);
                        });
                        if (post[0]==null) {  //加好友列表中不存在该邀请
                            //加好友
                            MybatisUnit.doChatWork(mapper -> {
                                int i = mapper.addFriend(username, friendName);
                                if (i == 1) {
                                    out.println("true");
                                } else {
                                    out.println("false");

                                }
                            });
                            break;
                        }{  //加好友列表中已经存在了该邀请
                            out.println("already");
                    }
                        break;

                        //获取所有好友请求
                    case "get":
                        MybatisUnit.doChatWork(mapper->{
                            try {
                                posts = mapper.checkGet(buf.readLine());
                                if (posts != null) {
                                    out.println(posts.length);
                                    for (int i = 0; i < posts.length; i++) {
                                        out.println(posts[i].getPost());
                                        out.println(posts[i].getGeter());
                                    }
                                    System.out.println("获得请求");
                                }else {
                                    System.out.println("请求为空");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        break;
                    case "post":
                        MybatisUnit.doChatWork(mapper->{
                            try {
                                Post[] posts = mapper.checkPost(buf.readLine());
                                if (posts != null) {
                                    out.println(posts.length);
                                    for (int i = 0; i < posts.length; i++) {
                                        out.println(posts[i].getPost());
                                        out.println(posts[i].getGeter());
                                        out.println(posts[i].getState());
                                    }
                                    System.out.println("获得请求");
                                }else {
                                    System.out.println("请求为空");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        break;
                    case "getSbIcon":
                        int length = Integer.parseInt(buf.readLine());  //读取用户的数量
                        for (int i = 0; i < length; i++) {
                            String iconName = buf.readLine();
                            MybatisUnit.doSqlWork(mapper->{
                                Icon icon=mapper.getIcon(iconName);
                                out.println(icon.getIconString());
                            });
                        }
                        break;
                    case "getSbIconGet":
                        int lengthGet = Integer.parseInt(buf.readLine());  //读取已发送用户的数量
                        for (int j = 0; j < lengthGet; j++) {
                            String iconNameGet = buf.readLine();
                            MybatisUnit.doSqlWork(mapper->{
                                Icon icon=mapper.getIcon(iconNameGet);
                                out.println(icon.getIconString());
                            });
                        }
                        break;
                    case "createFriendsTable":
                        MybatisUnit.doChatWork(mapper->{
                            //这里由于使用${},所以账号不能是纯数字，加入判断，如果是纯数字就加个D在前面
                            if (username.matches("^[0-9]*$")){  //如果账号是纯数字
                                mapper.createFriendsTable("_"+username);  //创建以自己为表名的好友表，用来存放好友
                            }else {
                                mapper.createFriendsTable(username);  //创建以自己为表名的好友表，用来存放好友
                            }
                        });
                        break;
                    case "addState":
                        new Thread(){
                            @SneakyThrows
                            @Override
                            public void run() {
                                String addStateName = buf.readLine();
                                MybatisUnit.doChatWork(mapper->{
                                    int i=mapper.updateAddState(addStateName, username);
                                    if (i == 1) {
                                        out.println("true");
                                    }
                                });

                                //在两个人的好友表中添加对方
                                MybatisUnit.doChatWork(mapper->{
                                    if (username.matches("^[0-9]*$")) {  //如果自己账号是纯数字
                                        mapper.addFriendName("_"+username, addStateName);  //给自己的好友表中添加人名
                                    } else {
                                        mapper.addFriendName(username, addStateName);  //给自己的好友表中添加人名
                                    }
                                    if (addStateName.matches("^[0-9]*$")) {  //如果对方账号是纯数字
                                        mapper.addFriendName("_"+addStateName, username);  //给对方的好友表中添加自己
                                    } else {
                                        mapper.addFriendName(addStateName, username);  //给对方的好友表中添加自己
                                    }
                                });

                                //创建聊天表，并把表名存储到两个人的好友表中
                                MybatisUnit.doChatWork(mapper->{
                                    String chatLocation = username + "_" + addStateName;  //聊天表名
                                    mapper.createChatTable(chatLocation);
                                    //加入两个人的表
                                    if (username.matches("^[0-9]*$")) {  //如果自己账号是纯数字
                                        mapper.updateChatLocation("_" + username, chatLocation, addStateName);
                                    } else {
                                        mapper.updateChatLocation(username, chatLocation, addStateName);
                                    }
                                    if (addStateName.matches("^[0-9]*$")) {  //如果好友账号是纯数字
                                        mapper.updateChatLocation("_" + addStateName, chatLocation, username);
                                    } else {
                                        mapper.updateChatLocation(addStateName, chatLocation, username);
                                    }
                                });


                            }
                        }.start();
                        break;
                    case "deleteAddFriend":
                        new Thread(){
                            @SneakyThrows
                            @Override
                            public void run() {
                                String deleteAddFriendName=buf.readLine();
                                MybatisUnit.doChatWork(mapper->{
                                    mapper.deleteFriendName(deleteAddFriendName, username);
                                });
                            }
                        }.start();
                        break;
                    case "checkFriends":
                        new Thread(){
                            @Override
                            public void run() {
                                MybatisUnit.doChatWork(mapper->{
                                    if (username.matches("^[0-9]*$")) {  //如果好友账号是纯数字
                                        friends =mapper.checkFriends("_" + username);
                                    } else {
                                        friends =mapper.checkFriends(username);
                                    }
                                    out.println(friends.length);  //发送长度
                                    for (int i = 0; i < friends.length; i++) {
                                        out.println(friends[i].getFriends());
                                        out.println(friends[i].getChatLocation());
                                    }

                                });
                            }
                        }.start();
                        break;
                    case "getFriendIcon":
                        int ChatLength = Integer.parseInt(buf.readLine());  //读取用户的数量
                        for (int i = 0; i < ChatLength; i++) {
                            String iconName = buf.readLine();
                            MybatisUnit.doSqlWork(mapper->{
                                Icon icon=mapper.getIcon(iconName);
                                out.println(icon.getIconString());
                            });
                        }
                        break;
                    case "getUserState":
                        MybatisUnit.doChatWork(mapper->{
                            for (int i = 0; i < friends.length; i++) {
                                String friendsName= null;
                                try {
                                    friendsName = buf.readLine();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                User user = mapper.getUserState(friendsName);
                                            out.println(user.getState());
                            }
                });

                break;


                }

            }

        } catch (SocketException | NullPointerException e) {
            System.out.println(this.client.getInetAddress() + "连接异常");

        }finally{
            String finalUsername1 = username;
            MybatisUnit.doSqlWork(mapper->{
                mapper.updateState(0, finalUsername1);
            });
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
        try (PrintStream out = new PrintStream(
                client.getOutputStream());  //实例化客户端的输出流
        ) {
            out.println(message);
        }
    }

}
