package KarChat.Chat.HomePage.Label;

import KarChat.Chat.Login.Button.RoundButton;
import KarChat.Chat.Login.Frameless;
import KarChat.Chat.Login.RadioJLabel;
import KarChat.Chat.Sound.PlaySound;
import KarChat.Client.EchoClient;
import com.sun.awt.AWTUtilities;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

import static KarChat.Chat.HomePage.Home.homeBack;
import static KarChat.Chat.HomePage.Home.menuHomeUser2;

public class InnerLabel extends RadioJLabel {

    private int xOld;
    private int yOld;
    private JTextField chatText;
    int fameColor1=237;  //背景颜色
    int fameColor2=236;
    int fameColor3=236;
    int topColor1=218;  //顶部颜色
    int topColor2=217;
    int topColor3=217;
    int bottomColor1 = 250;  //底部颜色
    int bottomColor2 = 249;
    int bottomColor3 = 249;
    int chatColor1 = 229;  //输入框颜色
    int chatColor2 = 227;
    int chatColor3 = 227;
    int buttonBackground1=235;  //按钮背景
    int buttonBackground2=234;
    int buttonBackground3=234;


    @SneakyThrows
    public InnerLabel(int width, int height) {  //创造聊天窗口
        super("");
        this.setBounds(50,50,width,height);
        this.setColor(new Color(fameColor1, fameColor2, fameColor3));
        //添加顶部信息
        RadioJLabel topLabel = new RadioJLabel("");
        topLabel.setBounds(0, 0, this.getWidth(), this.getHeight() / 9);
        topLabel.setColor(new Color(topColor1, topColor2, topColor3));
        topLabel.setArc(5,5);
        this.add(topLabel);

        //添加底部输入框
        //创建底部背景
        RadioJLabel bottomBack = new RadioJLabel("");
        bottomBack.setBounds(0, this.getHeight()*8/9, this.getWidth(), this.getHeight() / 9);
        bottomBack.setColor(new Color(bottomColor1, bottomColor2, bottomColor3));
        bottomBack.setArc(5,5);
        this.add(bottomBack);

        //底部背景加入输入框

        //搜索框
        RadioJLabel chat = new RadioJLabel("");
        chat.setColor(new Color(chatColor1, chatColor2, chatColor3));
        chat.setBounds(5, 5, bottomBack.getWidth()/2+150+40, bottomBack.getHeight()-10);
        chat.setArc(5, 5);
        bottomBack.add(chat);

        chatText = new JTextField();
        chat.add(chatText);  //加入搜索框
        chatText.setBounds(35, 5, chat.getWidth() - 30-95, chat.getHeight() - 10);
        chatText.setFont(new Font("Serif", Font.BOLD, 20));
        chatText.setForeground(new Color(79, 78, 78));
        chatText.setText("");
        chatText.setOpaque(false);
        chatText.setBorder(null);

        //发送按钮
        ImageIcon sendIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("chat/send2.png")));
        RadioJLabel send = new RadioJLabel("");
        JLabel sendTop = new JLabel();
        send.setArc(5,5);
        send.setColor(new Color(buttonBackground1, buttonBackground2, buttonBackground3));
        sendTop.setIcon(sendIcon);
        chat.add(send);
        send.add(sendTop);
        sendTop.setBounds(10, 10, 40, 40);
        send.setBounds(chat.getWidth() - 65, 3, 62, chat.getHeight()-6);

        //表情按钮
        //背景
        RadioJLabel emojiBack = new RadioJLabel("");

        ImageIcon emojiIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("chat/emoji/icon12.png")));
        RadioJLabel emoji = new RadioJLabel("");
        JLabel emojiTop = new JLabel();
        emoji.setArc(5,5);
        emoji.setColor(new Color(buttonBackground1, buttonBackground2, buttonBackground3));
        emojiTop.setIcon(emojiIcon);
        emojiBack.add(emoji);
        emoji.add(emojiTop);
        emojiTop.setBounds(10, 10, 40, 40);
        emoji.setBounds(3, 3, 62, chat.getHeight()-6);

        //发送文件按钮
        ImageIcon fileIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("chat/upload.png")));
        RadioJLabel file = new RadioJLabel("");
        JLabel fileTop = new JLabel();
        file.setArc(5,5);
        file.setColor(new Color(buttonBackground1, buttonBackground2, buttonBackground3));
        fileTop.setIcon(fileIcon);
        emojiBack.add(file);
        file.add(fileTop);
        fileTop.setBounds(10, 10, 40, 40);
        file.setBounds(emoji.getWidth()+3+3, 3, 62, chat.getHeight()-6);

        //背景添加
        emojiBack.setColor(new Color(chatColor1, chatColor2, chatColor3));
        emojiBack.setBounds(5+chat.getWidth()+8, 5,emoji.getWidth()+file.getWidth()+9 , bottomBack.getHeight()-10);
        emojiBack.setArc(5, 5);
        bottomBack.add(emojiBack);


        //搜索加入键盘监听
        chatText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {  //搜索
                    //发送事件


                }
            }
        });


        /*   处理拖动事件   */
        //处理鼠标点击事件
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //存储点击时的坐标
                xOld = e.getX();
                yOld = e.getY();
            }
        });
        //处理鼠标拖拽事件
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int xOnScreen = e.getXOnScreen(); //获取当前鼠标拖拽时x坐标
                int yOnScreen = e.getYOnScreen(); //获取当前鼠标拖拽时y坐标
                int xx = xOnScreen - xOld-310;  //移动差值
                int yy = yOnScreen - yOld-120;
                InnerLabel.this.setLocation(xx,yy);  //移动窗口
            }
        });

        //创建右上角圆按钮，并添加监听器
        RoundButton Rbut1 = new RoundButton("", new Color(58, 124, 243, 190), new Color(92, 143, 236, 221), new Color(132, 171, 243, 181)) {
            @Override
            public void mouseClicked(MouseEvent e) {
                InnerLabel.this.setSize(0,0);
            }

        };
        final int[] MAXTRANS = {255};
        RoundButton Rbut2 = new RoundButton("", new Color(243, 58, 101, 192), new Color(238, 70, 109, 189), new Color(252, 108, 141, 189)) {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                InnerLabel.this.setSize(0,0);
            }


        };
        topLabel.add(Rbut1);  //添加右上角按钮
        topLabel.add(Rbut2);

        Rbut1.setBounds(topLabel.getWidth()-80,20,25,25);
        Rbut2.setBounds(topLabel.getWidth()-40,20,25,25);

    }

    public InnerLabel() {
        this(700, 700);
    }

    /**
     * 发送信息
     */
    private void send() {

    }
}
