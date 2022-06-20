package com.Karchat.dao.impl;

import com.Karchat.dao.Login;
import com.Karchat.dao.mapper.LoginMapper;
import com.Karchat.util.Constant;
import com.Karchat.view.LoginHome;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.PrintStream;


@Service
public class LoginImpl implements Login {



    @SneakyThrows
    @Override
    public String LoginFromDataSource(PrintStream out, BufferedReader buf) {
        LoginHome.checkLogin(message -> {  //发送到服务器，并判断是否存在该用户
            out.println("login");  //输出给服务器要进行的功能
            out.println(message[0]);  //向服务器发送账号
            out.println(message[1]);  //向服务器发送密码
            Constant.usernameAll = message[0];
            Constant.passwordAll = message[1];
        });
        return buf.readLine();
    }

    @SneakyThrows
    @Override
    public String RegisterWithDataSource(PrintStream out, BufferedReader buf) {
        out.println("register");
        out.println(Constant.usernameAll);
        out.println(Constant.passwordAll);
        out.println(LoginHome.iconString);
        return buf.readLine();
    }

    @Override
    public void CreateFriendsTable(PrintStream out, BufferedReader buf) {
        //创建好友表，以该用户名为表名
        out.println("createFriendsTable");
    }


}
