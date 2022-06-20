package com.Karchat.dao.impl;

import com.Karchat.dao.InitHomePage;
import com.Karchat.entity.Friends;
import com.Karchat.entity.History;
import com.Karchat.entity.Post;
import com.Karchat.service.ViewServer;
import com.Karchat.util.ComponentUtil.CompositeComponent.MenuContent;
import com.Karchat.util.ComponentUtil.Label.InnerLabel;
import com.Karchat.util.Constant;
import com.Karchat.util.PictureUtil.GetPicture;
import com.Karchat.view.Home;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import static com.Karchat.util.ComponentUtil.CompositeComponent.MenuContent.*;
import static com.Karchat.util.Constant.*;

@Slf4j
@Service
public class InitHomePageImpl implements InitHomePage {

    @Resource
    ViewServer viewServer;

    @SneakyThrows
    @Override
    public String GetMyIconFromDataSource(PrintStream out, BufferedReader buf) {
        out.println("getIcon");
        return buf.readLine();
    }

    @SneakyThrows
    @Override
    public Post[] GetFriendInvitationDidNotAgreeFromDataSource(PrintStream out, BufferedReader buf) {
        out.println("get");
        out.println(Constant.usernameAll);
        int length = Integer.parseInt(buf.readLine());
        Post[] posts = new Post[length];
        for (int i = 0; i < length; i++) {
            String post = buf.readLine();
            String get = buf.readLine();
            posts[i] = new Post(post, get, null);  //获取每一个请求
        }
        String flag = buf.readLine();
        if (flag.equals("true")) {
            Constant.isGetFinished = false;
        }
        return posts;
    }

    @SneakyThrows
    @Override
    public void GetFriendIconDidNotAgreeFromDataSource(PrintStream out, BufferedReader buf) {
        out.println("getSbIcon");
        out.println(iconLength);
        for (int i = 0; i < iconLength; i++) {
            out.println(iconName[i]);
            BufferedImage icon = GetPicture.stringToImage(buf.readLine());  //转成图片
            MenuContent.images[i] = icon;
        }
        String flag = buf.readLine();
        if (flag.equals("true")) {
            isGetIconsFinished = false;
        }
    }

    @SneakyThrows
    @Override
    public Post[] GetFriendInvitationHadSentFromDataSource(PrintStream out, BufferedReader buf) {
        out.println("post");
        out.println(Constant.usernameAll);
        int length = Integer.parseInt(buf.readLine());
        Post[] posts = new Post[length];
        for (int i = 0; i < length; i++) {
            String post = buf.readLine();
            String get = buf.readLine();
            String state = buf.readLine();
            posts[i] = new Post(post, get, state);  //获取每一个请求
        }
        String flag = buf.readLine();
        if (flag.equals("true")) {
            Constant.isPostFinished = false;
        }
        return posts;
    }

    @SneakyThrows
    @Override
    public void GetFriendIconHadSentFromDataSource(PrintStream out, BufferedReader buf) {
        out.println("getSbIconGet");
        out.println(MenuContent.iconLengthGet);
        for (int i = 0; i < MenuContent.iconLengthGet; i++) {
            out.println(MenuContent.iconNameGet[i]);
            BufferedImage icon = GetPicture.stringToImage(buf.readLine());  //转成图片
            MenuContent.imagesGet[i] = icon;
        }
        String flag = buf.readLine();
        if (flag.equals("true")) {
            Constant.isPostIconsFinished = false;
        }
    }

    @SneakyThrows
    @Override
    public synchronized Friends[] GetListOfFriendsFromDataSource(PrintStream out, BufferedReader buf) {
        out.println("checkFriends");
        try {
            int length = Integer.parseInt(buf.readLine());  //好友个数
            friends = new Friends[length];
            for (int i = 0; i < length; i++) {
                String friend = buf.readLine();
                String getChatLocation = buf.readLine();
                friends[i] = new Friends(0, friend, getChatLocation);
            }
            String flag = buf.readLine();
            labelWhile:
            {
                while (true) {
                    if (flag.equals("true")) {
                        isGetFriendAmount = false;
                        Constant.isGettingListOfFriends = false;
                        break labelWhile;
                    }
                }
            }
        } catch (
                IOException e) {
            viewServer.ServerClosed();  //服务器关闭方法
            e.printStackTrace();
        }
        return friends;
    }

    @Override
    public BufferedImage[] GetFriendsIconFromDataSource(PrintStream out, BufferedReader buf) {
        BufferedImage[] icons = new BufferedImage[friends.length];
        out.println("getFriendIcon");
        out.println(friends.length);
        for (int i = 0; i < friends.length; i++) {
            out.println(friends[i].getFriends());
            BufferedImage icon = null;  //转成图片
            try {
                icon = GetPicture.stringToImage(buf.readLine());
            } catch (IOException e) {
                viewServer.ServerClosed();  //服务器关闭方法
                e.printStackTrace();
            }
            icons[i] = icon;
        }
        return icons;
    }

    @Override
    public int[] GetFriendsStateFromDataSource(PrintStream out, BufferedReader buf) {
        out.println("getUserState");
        int[] state = new int[friends.length];
        for (int i = 0; i < friends.length; i++) {
            out.println(friends[i].getFriends());
            try {
                state[i] = Integer.parseInt(buf.readLine());
            } catch (IOException e) {
                viewServer.ServerClosed();  //服务器关闭方法
                e.printStackTrace();
            }
        }
        log.info("获取好友的在线状态成功!");
        return state;
    }

    @SneakyThrows
    @Override
    public synchronized ArrayList<History> GetChatHistoryAndHistoryAmountFromDataSource(PrintStream out, BufferedReader buf, String friend, int index) {
        ArrayList<History> histories = new ArrayList<>();    //新的历史记录
        out.println("getChatHistory");
        out.println(Constant.usernameAll); //发送我的姓名
        out.println(friend);  //发送朋友的姓名

        int length = 0;
        try {
            length = Integer.parseInt(buf.readLine());  //消息长度
        } catch (NumberFormatException e) {
            log.info(friend + "没有聊天记录");
        }

        String message = null; //接收的信息
        for (int i = 0; i < length; i++) {
            String type = buf.readLine();   //获取是发送信息还是获取信息
            switch (type) {
                case "post":  //我发送信息
                    message = buf.readLine();  //获取发送的信息
                    histories.add(new History("post", message));  //将历史记录加入数组

                    break;
                case "get":  //我获取信息
                    message = buf.readLine();  //获取别人发送的信息
                    histories.add(new History("get", message));  //将历史记录加入数组

                    break;
            }
            if (i == length - 1 && length != 0) {  //如果是最后一句，显示在主界面的最新消息
                latestMessages.get(index).setTextDynamic(message);
            }
        }

        String flag = buf.readLine();
        if (flag.equals("true")) {
            Constant.isSomeBodyFinished.put(friend, true);
        }

        return histories;
    }

    @SneakyThrows
    @Override
    public ArrayList<History> GetChatHistoryFromDataSource(PrintStream out, BufferedReader buf, String friend) {
        ArrayList<History> histories = new ArrayList<>();    //新的历史记录
        out.println("getChatHistory");
        out.println(Constant.usernameAll); //发送我的姓名
        out.println(friend);  //发送朋友的姓名

        //获取是在跟谁聊天
        int index = userContent.get(friend);//获取表下标

        InnerLabel friendContext = Home.chatContent.get(index);  //获取聊天界面
        int length = 0;
        try {
            length = Integer.parseInt(buf.readLine());  //消息长度
        } catch (NumberFormatException e) {
            log.info(friend + "没有聊天记录");
        }

        String message = null; //接收的信息
        for (int i = 0; i < length; i++) {
            String type = buf.readLine();   //获取是发送信息还是获取信息
            switch (type) {
                case "post":  //我发送信息
                    message = buf.readLine();  //获取发送的信息
                    histories.add(new History("post", message));  //将历史记录加入数组

                    friendContext.send(InnerLabel.Type.RIGHT, message, friendContext.mine);
                    break;
                case "get":  //我获取信息
                    message = buf.readLine();  //获取别人发送的信息
                    histories.add(new History("get", message));  //将历史记录加入数组

                    friendContext.send(InnerLabel.Type.LEFT, message, friendContext.friend);
                    break;
            }


            if (i == length - 1 && length != 0) {  //如果是最后一句，显示在主界面的最新消息
                latestMessages.get(index).setTextDynamic(message);
            }
        }

        String flag = buf.readLine();
        if (flag.equals("true")) {
            isSomeBodyFinishedFirstTime.put(friend, true);
        }
        return histories;
    }
}
