package com.Karchat.service;

import org.springframework.stereotype.Component;

/**
 * 后台操作类，主要是定时调度的操作
 */
@Component
public interface BackgroundService {

    /**
     * 后台刷新好友待同意以及已发送列表
     */
    public void RefreshListOfFriendsToBeApprovedAndSent();

    /**
     * 后台获取每个用户的聊天记录，并更新用户的聊天记录
     */
    public void RefreshChatHistory();

    /**
     * 后台刷新好友列表
     */
    public void RefreshFriendsList();

    /**
     * 后台线程刷新用户在线状态
     */
    public void RefreshFriendsState();
}
