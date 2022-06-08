package com.Karchat.dao.impl;

import com.Karchat.dao.AddFriend;
import com.Karchat.service.ViewServer;
import com.Karchat.util.ComponentUtil.CompositeComponent.MenuContent;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import static com.Karchat.util.Constant.*;

@Service
public class AddFriendImpl implements AddFriend {

    @Resource
    ViewServer viewServer;

    @SneakyThrows
    @Override
    public synchronized String addFriendWithDataSource(PrintStream out, BufferedReader buf) {
        out.println("addFriend");
        out.println(MenuContent.friendName);
        return buf.readLine();
    }

    @Override
    public synchronized String AcceptFriendInvitationWithDataSource(PrintStream out, BufferedReader buf) {  //加入线程锁，防止连续同意好友抢对象
        out.println("addState");
        out.println(addStateName);
        addState = false;
        String BooleanFlag = null;
        try {
            BooleanFlag = buf.readLine();
        } catch (IOException e) {
            viewServer.ServerClosed();  //服务器关闭方法
            e.printStackTrace();
        }
        return BooleanFlag;
    }

    @Override
    public synchronized void RefuseFriendInvitationWithDataSource(PrintStream out, BufferedReader buf) {
        out.println("deleteAddFriend");
        out.println(deleteAddFriendName);
    }
}
