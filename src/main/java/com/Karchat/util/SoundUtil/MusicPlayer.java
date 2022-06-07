/**
 *
 * @Author: hyacinth
 * @Title: MusicPlayer.java
 * @Package com.xu.test
 * @Description: TODO: 
 * @Date: 2019年8月25日 下午10:40:47   
 * @Version V-1.0 
 * @Copyright: 2019 hyacinth
 *
 */
package com.Karchat.util.SoundUtil;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * @Author: hyacinth
 * @ClassName: MusicPlayer
 * @Description: TODO
 * @Date: 2019年8月25日 下午10:40:47   
 * @Copyright: hyacinth
 */
@Slf4j
public class MusicPlayer {

    public static void play(String path) throws Exception {
        switch (path) {
            case "sound/error.mp3":
                checkMusic("error.mp3");
                break;
            case "sound/loginsuccess.mp3":
                checkMusic("loginsuccess.mp3");
                break;
            case "sound/msgsound.mp3":
                checkMusic("msgsound.mp3");
                break;
            case "sound/notice.mp3":
                checkMusic("notice.mp3");
                break;
            case "sound/sendmsg.mp3":
                checkMusic("sendmsg.mp3");
                break;
        }
    }

    public static void main(String[] args) throws Exception {
        MusicPlayer.play("sound/error.mp3");
    }

    public static void checkMusic(String path) {
        try (InputStream inputStream= new FileInputStream(new File("downloadMusic/"+path))) {
            Player player = new Player(inputStream);
            player.play();

        } catch (IOException e) {  //如果没有音乐文件
            try {
                File dirFile = new File("downloadMusic");
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }
                File newFile = new File(path);
                InputStream fileInputStream = Resources.getResourceAsStream("sound/" + path);
                InputStreamToFile.copyInputStreamToFile(fileInputStream,newFile);
                CopyMP3FileTool.copyFile(newFile,"downloadMusic/"+path);

//
//                OutputStream os = new FileOutPutStream(f2);
//                    byte[] byte =new byte[4096];
//                    int n = 0;
//                    while (-1 != (n = is.read( byte))){
//                        os.write( byte,0, n);
//                    }
//                    is.close();




                InputStream inputStream = new FileInputStream(new File("downloadMusic/" + path));
                    Player player = new Player(inputStream);
                    player.play();

//                fileInputStream.close();
//                fileOutputStream.close();
                inputStream.close();
                log.info("EchoClient:没有找到音乐文件，立刻进行下载");
            } catch (IOException | JavaLayerException ex) {
                ex.printStackTrace();
            }
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }

    }
}
