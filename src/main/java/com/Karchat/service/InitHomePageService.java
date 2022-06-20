package com.Karchat.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * 初始化客户端主界面相关操作
 */
@Component
public interface InitHomePageService {

    /**
     * 获取自己的用户头像
     * @return
     */
    boolean GetMyIcon(PrintStream out, BufferedReader buf);

    /**
     * 获取待同意的好友邀请
     * @return
     */
    boolean GetFriendInvitationDidNotAgree(PrintStream out, BufferedReader buf);

    /**
     * 获得待同意邀请的好友头像
     * @return
     */
    boolean GetFriendIconDidNotAgree(PrintStream out, BufferedReader buf);

    /**
     * 获取已发送的好友邀请记录
     * @return
     */
    boolean GetFriendInvitationHadSent(PrintStream out, BufferedReader buf);


    /**
     * 获得已发送邀请的好友头像
     * @return
     */
    boolean GetFriendIconHadSent(PrintStream out, BufferedReader buf);

    /**
     * 获取好友列表,并绘制好友列表画布
     */
    boolean GetListOfFriends(PrintStream out, BufferedReader buf);

    /**
     * 只获取好友数量，不绘制画布
     * @return
     */
    boolean GetListOfFriendsAmountOnly(PrintStream out, BufferedReader buf);

    /**
     * 获取好友头像
     * @return
     */
    boolean GetFriendsIcon(PrintStream out, BufferedReader buf);

    /**
     * 更新用户头像
     * @return
     */
    boolean GetFriendsIconNew(PrintStream out, BufferedReader buf);

    /**
     * 获取好友在线状态
     * @return
     */
    boolean GetFriendsState(PrintStream out, BufferedReader buf);

    /**
     * 获取某个好友的历史记录以及历史记录数量
     */
    void GetChatHistoryAndHistoryAmount(String friend, Socket clien);

    /**
     * 获取某个好友的聊天历史记录
     */
    @SneakyThrows
    void GetChatHistory(String friend, Socket clien);
}
