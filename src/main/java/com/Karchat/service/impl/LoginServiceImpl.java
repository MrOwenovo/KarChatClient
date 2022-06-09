package com.Karchat.service.impl;

import com.Karchat.dao.Login;
import com.Karchat.dao.mapper.LoginMapper;
import com.Karchat.service.LoginService;
import com.Karchat.service.MusicService;
import com.Karchat.util.Constant;
import com.Karchat.view.LoginHome;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;

import static com.Karchat.view.LoginHome.*;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    LoginMapper mapper;
    @Resource
    MusicService music;
    @Resource
    Login login;

    @Override
    public boolean CheckAccountRecords() {
            try (Scanner sc = new Scanner(new FileReader("userMessage"))) {
                while (sc.hasNextLine()) {
                    String user = sc.nextLine();
                    String pass = sc.nextLine();
                    username.setText(user);
                    password.setText(pass);
                    passwordMessage.setText("");
                    log.info("读取了保存的账号密码");
                }
                return true;
            } catch (FileNotFoundException e) {
                log.info("EchoClient:没有保存的账号密码文件");
                return false;
            }
    }

    @SneakyThrows
    @Override
    public void Login(PrintStream out, BufferedReader buf) {
        log.info("正在登陆中.....");
        loadIn.add(sign);  //加入加载条
        sign.setBounds(-10, 10, 155, 155);
        loadIn.setColor(new Color(115, 175, 197));
        LoginHome.wrongMessage.setTextDynamic("登陆中");
        wrongMessage.setForeground(new Color(115, 175, 197));
        loadIn.show();
        Thread.sleep(1000);  //让登录效果转一下

        String message = login.LoginFromDataSource(out, buf);
        if ("true".equals(message)) {
            log.info("登录成功!");
            music.playSuccessMP3();
              //判断是否保存密码，保存了则储存密码
                if (Constant.remember) {  //保存密码
                    log.info("保存了账号: "+Constant.usernameAll);
                    log.info("保存了密码: "+Constant.passwordAll);
                    PrintStream userMessage = new PrintStream(new FileOutputStream("userMessage"));
                    userMessage.println(Constant.usernameAll);
                    userMessage.println(Constant.passwordAll);
                }
            Constant.loginSuccess = true; //登录成功
            LoginHome.background.dispose();  //登录成功关闭页面
            //进入新的界面
        } else if ("already".equals(message)) {
            loadIn.setColor(new Color(0, 0, 0, 0));
            wrongMessage.setForeground(new Color(215, 27, 71, 205));
            LoginHome.loginLabel.shake();  //错误后让按钮抖动
            LoginHome.wrongMessage.setTextDynamic("账号已经登录");
            LoginHome.wrongMessage.shake();
            log.info("账号已经登录,登录失败");
            music.playErrorMP3();
        } else {
            loadIn.setColor(new Color(0, 0, 0, 0));
            wrongMessage.setForeground(new Color(215, 27, 71, 205));
            LoginHome.loginLabel.shake();  //错误后让按钮抖动
            LoginHome.wrongMessage.setTextDynamic("账号不存在或密码错误");
            LoginHome.wrongMessage.shake();
            log.info("账号不存在或密码错误");
            music.playErrorMP3();
        }
    }

    @SneakyThrows
    @Override
    public void Register(PrintStream out, BufferedReader buf) {
        log.info("注册中....");
        loadIn.add(sign);  //加入加载条
        sign.setBounds(-10, 10, 155, 155);
        loadIn.setColor(new Color(115, 175, 197));
        LoginHome.wrongMessage.setTextDynamic("注册中");
        wrongMessage.setForeground(new Color(115, 175, 197));
        loadIn.show();

        String message=login.LoginFromDataSource(out, buf);
        if ("true".equals(message)) {
            loadIn.setColor(new Color(0, 0, 0, 0));
            wrongMessage.setForeground(new Color(215, 27, 71, 205));

            LoginHome.registerAlready();
            log.info("该用户已经注册过，注册失败");
            music.playErrorMP3();
        } else {
            String messageFromRegister=login.RegisterWithDataSource(out, buf);
            if (Objects.equals(messageFromRegister, "true")) {
                Thread.sleep(1000);
                log.info("注册成功！");
                LoginHome.registerFinish(Constant.usernameAll, Constant.passwordAll);  //做出注册完反馈
               music.playSuccessMP3();
            }
            login.CreateFriendsTable(out,buf);  //注册存放好友的表
        }
    }

    @Override
    public void LoginWhenClientDisConnect() {
        LoginHome.wrongMessage.shake();
        LoginHome.loginLabel.shake();
        music.playErrorMP3();
    }

    @Override
    public void RegisterWhenClientDisConnect() {
        LoginHome.wrongMessage.shake();
        LoginHome.registerLabel5.shake();
        music.playErrorMP3();
    }
}
