package com.Karchat.service;

import org.springframework.stereotype.Component;

@Component
public interface MusicService {

    /**
     * 播放失败音效
     */
    public void playErrorMP3();

    /**
     * 播放成功音效
     */
    public void playSuccessMP3();

    /**
     * 播放发送信息音效
     */
    public void playSendMessageMP3();

    /**
     * 播放接收消息音效
     */
    public void playAcceptMessageMP3();

    /**
     * 播放消息提醒音效
     */
    public void playNoticeMP3();






}
