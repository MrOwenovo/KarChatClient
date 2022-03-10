package KarChat.Chat.HomePage.Label;

import KarChat.Chat.Login.DynamicJLabel;
import KarChat.Chat.Login.RadioJLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 聊天发送信息的标签，自动和文字对其大小，可以加入头像，分左边和右边两种
 */
public class messageLabel extends RadioJLabel {

    public messageLabel(int type, BufferedImage image,String messages) {
        //动态信息标签
        DynamicJLabel message = new DynamicJLabel(messages, new Font("Serif", Font.BOLD, 18), image.getHeight() * 3 / 2);
        JLabel iconLabels = new JLabel();
        iconLabels.setIcon(new ImageIcon(image));
        if (type == 1) {  //右方消息
            iconLabels.setBounds(this.getWidth() - image.getWidth(), 0, image.getWidth(), image.getHeight());
            message.setCenter(this.getWidth());
        } else {  //左方消息
            iconLabels.setBounds( 0, 0, image.getWidth(), image.getHeight());
            message.setCenter(this.getWidth());
        }
        this.setSize(image.getWidth()+iconLabels.getWidth()+10,image.getHeight()+iconLabels.getHeight()+10);
        this.add(message);  //添加头像和消息
        this.add(iconLabels);
    }

    static class Type {
        static int LEFT = 0;  //左方的消息
        static int RIGHT = 1;  //右方的消息
    }


}
