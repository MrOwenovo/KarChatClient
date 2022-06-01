package KarChat.Chat.Sound;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;

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

        BufferedInputStream buffer = new BufferedInputStream(Resources.getResourceAsStream(filePath));
        player = new Player(buffer);
        player.play();
    }
}