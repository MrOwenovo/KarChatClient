package com.Karchat.service.impl;

import com.Karchat.service.ViewServer;
import com.Karchat.util.Constant;
import com.Karchat.util.SoundUtil.PlaySound;
import com.Karchat.view.Home;
import com.Karchat.view.LoginHome;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static com.Karchat.util.ComponentUtil.CompositeComponent.Menu.*;
import static com.Karchat.util.ComponentUtil.CompositeComponent.Menu.newMenuIcon;
import static com.Karchat.view.Home.*;
import static com.Karchat.view.LoginHome.load;
import static com.Karchat.view.LoginHome.sign;

@Slf4j
@Service
public class ViewServerImpl implements ViewServer {
    @Override
    public void DisplayNotConnectedLogin() {
        LoginHome.isAlive = false;
        this.DisplayConnectedLogin();
        log.info("登录界面打开成功");
        log.info("服务器未连接");
        LoginHome.wrongMessage.setTextDynamic("服务器未连接");
        load.add(sign);
        sign.setBounds(-10,10,155,155);
        load.show();

    }

    @Override
    public void DisplayConnectedLogin() {
        new LoginHome();  //打开客户端登录窗口
    }

    @Override
    public void DisplayConnectedClient() {
        //打开客户端登录窗口
        new Home();
    }

    @Override
    public void ServerClosed() {
        log.info("服务器已经关闭!请重启客户端");
        serverClosedMessage.setForeground(new Color(250, 38, 38));

        ServerCloseLoad.show();
    }

    @Override
    public void ClientError() {
        log.info("客户端出错!请重启客户端");
        serverClosedMessage.setForeground(new Color(250, 38, 38));
        serverClosedMessage.setTextDynamic("客户端出错");

        ServerCloseLoad.show();
    }

    @Override
    public void MenuShrink() {
        new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                int WIDTH = WIDTHNOW[0];
                int MENUWIDTH = 140;
                keepFlag[0] = false;
                menuBack.setBounds(110, 20, newMenuIcon.getIconWidth(), newMenuIcon.getIconHeight());

                label2:
                {
                    isShrink = true;  //正在收缩
                    while (WIDTH > -20 - 180 - (newMenuIcon.getIconWidth() - menuIcon.getIconWidth())) {
                        Thread.sleep(1);
                        menuTop.setBounds(WIDTH, 0, newMenuIcon.getIconWidth(), menuIcon.getIconHeight());
                        if (menuFlag[0] && MENUWIDTH > 12)
                            menuHomeUser.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth() + 350, menuIcon.getIconHeight());
                        if (menuFlag1[0] && MENUWIDTH > 12)
                            menuHomeUser1.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth() + 350, menuIcon.getIconHeight());
                        if (menuFlag2[0] && MENUWIDTH > 12)
                            menuHomeUser2.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth() + 350, menuIcon.getIconHeight());
                        if (menuFlag3[0] && MENUWIDTH > 12)
                            menuHomeUser3.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth() + 300, menuIcon.getIconHeight());
                        if (menuFlag4[0] && MENUWIDTH > 12)
                            menuHomeUser4.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth() + 350, menuIcon.getIconHeight());
                        if (menuFlag5[0] && MENUWIDTH > 12)
                            menuHomeUser5.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth() + 350, menuIcon.getIconHeight());
                        if (menuFlag6[0] && MENUWIDTH > 12)
                            menuHomeUser6.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth() + 350, menuIcon.getIconHeight());

                        WIDTH -= 3;
                        if (menuFlag[0] || menuFlag1[0] || menuFlag2[0] || menuFlag3[0] || menuFlag4[0] || menuFlag5[0] || menuFlag6[0])
                            MENUWIDTH -= 3;

                        if (keepFlag[0]) {
                            WIDTHNOW[0] = WIDTH;
                            break label2;
                        }
                    }
                    isShrink = false;  //收缩完成
                    menuBack.setBounds(110, 20, 0, 0);
                    isOpen[0] = false;
                    WIDTHNOW[0] = -(newMenuIcon.getIconWidth() - menuIcon.getIconWidth());  //清零
                }
            }
        }.start();
    }
}
