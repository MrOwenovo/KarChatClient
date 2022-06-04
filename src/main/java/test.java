import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URLDecoder;

/***
 - 音乐播放器类
 - @author lt
 - time 2016-7-5
 */
public class test {
    static Player player;

    //播放方法
    @SneakyThrows
    public void play(String filePath) throws FileNotFoundException, JavaLayerException {

//        BufferedInputStream buffer = new BufferedInputStream(Resources.getResourceAsStream(filePath));
//        InputStream in = this.getClass().getClassLoader().getResourceAsStream("/"+filePath);
//        String newPath = URLDecoder.decode(myPath, "UTF-8");//如果路径中带有中文会被URLEncoder,因此这里需要解码
//        System.out.println(newPath);
//        InputStream inputStream = new FileInputStream(new File("src/main/resources/sound/error.mp3"));
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        InputStream inputStream = classPathResource.getInputStream();
        player = new Player(inputStream);
        player.play();
    }

    public static void main(String[] args) throws FileNotFoundException, JavaLayerException {
        new test().play("sound/error.mp3");
    }
}