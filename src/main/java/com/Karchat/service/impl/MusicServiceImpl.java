package com.Karchat.service.impl;

import com.Karchat.service.MusicService;
import com.Karchat.util.SoundUtil.PlaySound;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MusicServiceImpl implements MusicService {
    @Override
    public void playErrorMP3() {
        new Thread(){
            @SneakyThrows
            @Override
            public void run() {
                PlaySound.play("sound/error.mp3");
            }
        }.start();
    }

    @Override
    public void playSuccessMP3() {
        new Thread(){
            @SneakyThrows
            @Override
            public void run() {
                PlaySound.play("sound/loginsuccess.mp3");
            }
        }.start();
    }

    @Override
    public void playSendMessageMP3() {
        new Thread(){
            @SneakyThrows
            @Override
            public void run() {
                PlaySound.play("sound/sendmsg.mp3");
            }
        }.start();
    }

    @Override
    public void playAcceptMessageMP3() {
        new Thread(){
            @SneakyThrows
            @Override
            public void run() {
                PlaySound.play("sound/msgsound.mp3");
            }
        }.start();
    }

    @Override
    public void playNoticeMP3() {
        new Thread(){
            @SneakyThrows
            @Override
            public void run() {
                PlaySound.play("sound/notice.mp3");
            }
        }.start();
    }
}
