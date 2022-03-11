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
    public int myHeight;  //当前的高度

    public messageLabel(int type, BufferedImage image,String messages) {
        int line = 1;
        if (messages.length()/13 > 0) {  //超过13个换行
            line = messages.length() / 13;  //一共有几行
            if (messages.length()%13!=0)
                line += 1;  //最后也算一行
        }
        DynamicJLabel[] MyMessages = new DynamicJLabel[line];  //分行的消息
        //处理信息长度，根据微信，一条信息最长13个，超过13个就换行
        int index = 0;
        for (int i = 0; i < line; i++) {
            if (line == 1) {  //只有一行
                MyMessages[i] = new DynamicJLabel(messages, new Font("Serif", Font.BOLD, 18), 11);
            } else {
                if(i!=line-1) {
                    MyMessages[i] = new DynamicJLabel(messages.substring(index, index + 13), new Font("Serif", Font.BOLD, 18), 7*i*3+3);
                    index += 13;
                }else{
                    MyMessages[i] = new DynamicJLabel(messages.substring(index, messages.length()), new Font("Serif", Font.BOLD, 18), 7*i*3+3);
                }
            }
        }
        //动态信息标签
        this.setSize(2*image.getWidth()+ MyMessages[0].getWidth()+20,image.getHeight()+10);
        JLabel iconLabels = new JLabel();  //头像
        iconLabels.setIcon(new ImageIcon(image));
        RadioJLabel back = new RadioJLabel();  //绿色背景
        int HEIGHT = this.getHeight()-15;  //储存第一条语句的高度，防止越加越多
        int height = HEIGHT - 4;  //背景高度
        if (type == 1) {  //右方消息  //循环拆分
            iconLabels.setBounds(this.getWidth() - image.getWidth(), 0, image.getWidth(), image.getHeight());
            for (int i = 0; i < line; i++) {  //把拆分的语句拼起来
                MyMessages[i].setCenter(this.getWidth()-image.getWidth());
                this.add(MyMessages[i]);  //添加头像和消息
                height += HEIGHT/2;
            }
            back.setColor(new Color(131, 204, 60));
            back.setBounds(this.getWidth() - MyMessages[0].getWidth() -18 - image.getWidth()*2, 2, MyMessages[0].getWidth() +15 + image.getWidth(), height-4);
            this.setSize(this.getWidth(),height);
            this.myHeight = height; //将最终聊天信息的高度保存

        } else {  //左方消息
            iconLabels.setBounds( 0, 0, image.getWidth(), image.getHeight());
            for (int i = 0; i < line; i++) {
                MyMessages[i].setCenter(this.getWidth()*2);
                this.add(MyMessages[i]);  //添加头像和消息
                height += this.getHeight();
            }
            back.setColor(new Color(253, 252, 252));
            back.setBounds(MyMessages[0].getWidth() + 10 + image.getWidth(), 0, MyMessages[0].getWidth() + 15 + image.getWidth(), height);

        }
        this.add(iconLabels);
        this.add(back);
    }

    static class Type {
        static int LEFT = 0;  //左方的消息
        static int RIGHT = 1;  //右方的消息
    }


}
