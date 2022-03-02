package KarChat.Client;

import KarChat.Chat.Helper.GetPicture;
import KarChat.Chat.HomePage.Home;
import KarChat.Chat.Login.*;
import KarChat.Chat.Login.Button.RadioButton;
import KarChat.Chat.Login.Button.RoundButton;
import KarChat.Chat.Login.Button.ThreeDimensionalBorder;
import KarChat.Chat.Sound.PlaySound;
import lombok.SneakyThrows;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Objects;

import static KarChat.Chat.Login.LoginHome.*;

/**
 * 客户端程序,向服务器发送请求，并接收客户端的反馈，服务器在EchoThread类里处理信息
 */
public class EchoClient {

    public static boolean login =false;  //判断是否登录
    public static boolean check =false;  //判断是否存在用户
    public static boolean getIcon =false;  //判断是否更换头像

    public static void main(String[] args) throws Exception{
        try (
                Socket clien = new Socket("localhost", 8888);  //指定连接主机及端口
                PrintStream out = new PrintStream(
                        clien.getOutputStream());  //向服务器端输出信息
                BufferedReader buf = new BufferedReader(new InputStreamReader(clien.getInputStream())); //接收服务器返回的信息
        ) {
            new LoginHome();  //打开客户端登录窗口

            final String[] username;
            final String[] password;
            label:
            {
                username = new String[1];  //账号
                password = new String[1];  //密码
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
                        });
                        String message = buf.readLine();
                        if ("true".equals(message)) {
                            System.out.println("登录成功");
                            LoginHome.background.dispose();  //登录成功关闭页面
                            //进入新的界面
                            break label; //退出登录功能
                        } else if ("already".equals(message)) {
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
                        LoginHome.checkLogin(message -> {  //发送到服务器，并判断是否存在该用户
                            out.println("login");  //输出给服务器要进行的功能
                            out.println(message[0]);  //向服务器发送账号
                            out.println(message[1]);  //向服务器发送密码
                            username[0] = message[0];
                            password[0] = message[1];
                        });
                        if ("true".equals(buf.readLine())) {
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
                            out.println(username[0]);
                            out.println(password[0]);
                            out.println(LoginHome.iconString);
                            if (Objects.equals(buf.readLine(), "true")) {
                                LoginHome.registerFinish(username[0], password[0]);  //做出注册完反馈
                            }
                            check = false;  //登录失败标志位重置为false
                        }
                    }
                }

            }
            label2:
            {
                new Home();  //打开客户端登录窗口

                while (true) {
                    if (getIcon) {
                        out.println("getIcon");
                        String iconString = buf.readLine();
                        BufferedImage icon = GetPicture.stringToImage(iconString);  //转成图片
                        Home.setIcon(icon);
                        getIcon = false;  //修改标签
                    }
                }
            }

        } catch (ConnectException e) {
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
        }
    }
