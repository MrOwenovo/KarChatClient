package com.Karchat.service;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.net.Socket;

@Component
public interface ChatService {


    /**
     * 加好友操作
     * @return
     */
    boolean AddFriend(PrintStream out, BufferedReader buf);

    /**
     * 同意好友邀请
     * @return
     */
    boolean AcceptFriendInvitation(PrintStream out, BufferedReader buf);

    /**
     * 拒绝好友邀请
     * @return
     */
    boolean RefuseFriendInvitation(PrintStream out, BufferedReader buf);

    /**
     * 发送信息给好友
     */
    void Send(String message, String geter, Socket clien);
}
