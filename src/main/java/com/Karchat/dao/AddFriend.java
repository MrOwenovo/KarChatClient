package com.Karchat.dao;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.PrintStream;

/**
 * 与数据库进行交互的加好友相关动作
 */
@Component
public interface AddFriend {

    /**
     * 加好友与数据库的交互
     */
    String addFriendWithDataSource(PrintStream out, BufferedReader buf);

    /**
     * 同意好友邀请后,在数据库中修改好友数据
     */
    String AcceptFriendInvitationWithDataSource(PrintStream out, BufferedReader buf);

    /**
     * 拒绝好友邀请,在数据库中删除请求
     */
    void RefuseFriendInvitationWithDataSource(PrintStream out, BufferedReader buf);

}
