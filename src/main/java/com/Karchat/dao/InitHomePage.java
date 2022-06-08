package com.Karchat.dao;

import com.Karchat.entity.Friends;
import com.Karchat.entity.History;
import com.Karchat.entity.Post;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.ArrayList;

@Component
public interface InitHomePage {

    /**
     * 从数据库获取自己的用户头像
     */
    String GetMyIconFromDataSource(PrintStream out, BufferedReader buf);

    /**
     * 从数据库获取待同意的好友邀请
     */
    @SneakyThrows
    Post[] GetFriendInvitationDidNotAgreeFromDataSource(PrintStream out, BufferedReader buf);

    /**
     * 从数据库获得待同意邀请的好友头像
     */
    void GetFriendIconDidNotAgreeFromDataSource(PrintStream out, BufferedReader buf);

    /**
     * 从数据库获得已发送邀请的好友头像
     */
    @SneakyThrows
    void GetFriendIconHadSentFromDataSource(PrintStream out, BufferedReader buf);

    /**
     * 获取已发送的好友邀请记录
     */
    @SneakyThrows
    Post[] GetFriendInvitationHadSentFromDataSource(PrintStream out, BufferedReader buf);



    /**
     * 从数据库获取好友列表
     */
    Friends[] GetListOfFriendsFromDataSource(PrintStream out, BufferedReader buf);

    /**
     * 从数据库获取好友头像
     */
    BufferedImage[] GetFriendsIconFromDataSource(PrintStream out, BufferedReader buf);

    /**
     * 从数据库获取好友在线状态
     */
    int[] GetFriendsStateFromDataSource(PrintStream out, BufferedReader buf);

    /**
     * 从数据库获取某个好友的历史记录以及历史记录数量
     */
    @SneakyThrows
    ArrayList<History> GetChatHistoryAndHistoryAmountFromDataSource(PrintStream out, BufferedReader buf, String friend, int index);

    /**
     * 从数据库获取某个好友的聊天历史记录
     */
    @SneakyThrows
    ArrayList<History> GetChatHistoryFromDataSource(PrintStream out, BufferedReader buf, String friend);
}
