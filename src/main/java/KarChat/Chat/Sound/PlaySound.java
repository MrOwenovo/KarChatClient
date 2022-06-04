package KarChat.Chat.Sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;

/***
 - 音乐播放器类
 - @author lt
 - time 2016-7-5
 */
public class PlaySound{
    static Player player;

    //播放方法
    @SneakyThrows
    public static void play(String filePath) throws FileNotFoundException, JavaLayerException {

        FileInputStream buffer = new FileInputStream("src/main/resources/"+filePath);
        player = new Player(buffer);
        player.play();
    }

    public static void main(String[] args) throws FileNotFoundException, JavaLayerException {
        PlaySound.play("sound/error.mp3");
    }
}