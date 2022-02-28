package KarChat.Chat.Login;

import KarChat.Chat.Login.Button.Shakeable;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;

/**
 * 继承抖动动作接口的可抖动按钮
 */
public class ShakeLabel extends JLabel implements Shakeable {
    public ShakeLabel(ImageIcon icon) {
        super(icon);
    }
    public ShakeLabel(String message) {
        super(message);
    }
    @Override
    public void shake() {  //开始抖动，抖动0.3秒
        //设置窗口状态
        Point p = this.getLocation();  //当前坐标
        new Thread() {
            //开始时间
            long begin = System.currentTimeMillis();
            long end = System.currentTimeMillis();

            @SneakyThrows
            @Override
            public void run() {
                while ((end - begin)  < 400) {  //震动0.3秒
                    ShakeLabel.this.setLocation(new Point((int) p.getX() - 5, (int) p.getY() + 5));  //重设位置
                    ShakeLabel.this.setLocation(p);  //还原位置
                    end = System.currentTimeMillis(); //设置当前end时间
                }
            }
        }.start();
    }
}
