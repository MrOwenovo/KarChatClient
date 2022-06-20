package com.Karchat.util.ComponentUtil.Label;

import com.Karchat.util.ComponentUtil.Button.RoundButton;
import com.Karchat.util.Controller.Controller;
import com.Karchat.util.ComponentUtil.CompositeComponent.MenuContent;
import com.Karchat.util.Constant;
import com.Karchat.util.SoundUtil.PlaySound;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.Karchat.util.ComponentUtil.CompositeComponent.MenuContent.latestMessages;


public class InnerLabel extends RadioJLabel {

    private int xOld;
    private int yOld;
    private JTextField chatText;
    public int fameColor1=237;  //背景颜色
    public int fameColor2=236;
    public int fameColor3=236;
    public int topColor1=218;  //顶部颜色
    public int topColor2=217;
    public int topColor3=217;
    public int bottomColor1 = 250;  //底部颜色
    public int bottomColor2 = 249;
    public int bottomColor3 = 249;
    public int chatColor1 = 229;  //输入框颜色
    public int chatColor2 = 227;
    public int chatColor3 = 227;
    public int buttonBackground1=235;  //按钮背景
    public int buttonBackground2=234;
    public int buttonBackground3=234;
    public BufferedImage mine;  //我方头像
    public BufferedImage friend;  //好友头像
    public String  friendName;  //好友姓名
    public RadioJLabel bottomBack;
    public RadioJLabel topLabel;


    @SneakyThrows
    public InnerLabel(int width, int height) {  //创造聊天窗口
        super("");
        this.setBounds(50,50,width,height);
        this.setColor(new Color(fameColor1, fameColor2, fameColor3));
        //添加顶部信息
        topLabel = new RadioJLabel("");
        topLabel.setBounds(0, 0, this.getWidth(), this.getHeight() / 9);
        topLabel.setColor(new Color(topColor1, topColor2, topColor3));
        topLabel.setArc(5,5);
        this.add(topLabel);

        //添加底部输入框
        //创建底部背景
        bottomBack = new RadioJLabel("");
        bottomBack.setBounds(0, this.getHeight()*8/9, this.getWidth(), this.getHeight() / 9);
        bottomBack.setColor(new Color(bottomColor1, bottomColor2, bottomColor3));
        bottomBack.setArc(5,5);
        this.add(bottomBack);

        addUpAndDown(this);

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
            @SneakyThrows
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {  //搜索
                    //发送事件
                    String message=chatText.getText();
                    if (message.length()>=1) {
                        send(Type.RIGHT, message, mine);  //发送信息
                        Constant.context.getBean(Controller.class).send(message, friendName);  //发送信息给service
                        //将最新消息显示在主界面
                        int index = MenuContent.userContent.get(friendName);
                        latestMessages.get(index).setTextDynamic(message);

                        //清空输入栏
                        chatText.setText("");

                        //播放发送音效
                        new Thread() {
                            @SneakyThrows
                            @Override
                            public void run() {
                                PlaySound.play("sound/sendmsg.mp3");
                            }
                        }.start();
                    }
                }
            }
        });

        //发送图像加入点击监听
        send.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //发送事件
                String message = chatText.getText();
                if (message.length() >= 1) {
                    send(Type.RIGHT, message, mine);  //发送信息
                    Constant.context.getBean(Controller.class).send(message, friendName);  //发送信息给service
                    //将最新消息显示在主界面
                    int index = MenuContent.userContent.get(friendName);
                    latestMessages.get(index).setTextDynamic(message);

                    //清空输入栏
                    chatText.setText("");

                    //播放发送音效
                    new Thread() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            PlaySound.play("sound/sendmsg.mp3");
                        }
                    }.start();

                }
            }
        });

        emoji.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {


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

    public ArrayList<messageLabel> messages = new ArrayList<>();
    int index = 0;
    /**
     * 发送信息
     */
    public void send(int type, String message, BufferedImage image) {
        if (type == Type.RIGHT) {  //若我方发消息
            messages.add(new messageLabel(messageLabel.Type.RIGHT, image, message));  //加入新消息
            messages.get(index).setColor(new Color(fameColor1,fameColor2,fameColor3));
            this.add(messages.get(index));  //加入消息
            //判断放置位置
            messages.get(index).setLocation(700-messages.get(index).getWidth()-10,700 - bottomBack.getHeight()-messages.get(index).myHeight-20);
            for (int i = messages.size() - 2; i >=0; i--) {  //所有信息标签上移
                messages.get(i).setLocation(messages.get(i).getX(),messages.get(i).getY()-messages.get(messages.size()-1).getHeight()-10);
            }
            this.repaint();
            index++;
        } else {  //对方消息
            messages.add(new messageLabel(messageLabel.Type.LEFT, image, message));  //加入新消息
            messages.get(index).setColor(new Color(fameColor1,fameColor2,fameColor3));
            this.add(messages.get(index));  //加入消息
            //判断放置位置
            messages.get(index).setLocation(0,700 - bottomBack.getHeight()-messages.get(index).myHeight-20);
            for (int i = messages.size() - 2; i >=0; i--) {  //所有信息标签上移
                messages.get(i).setLocation(messages.get(i).getX(),messages.get(i).getY()-messages.get(messages.size()-1).getHeight()-10);
            }
            this.repaint();
            index++;
        }
    }

    /**
     * 添加上下移动事件
     */
    @SneakyThrows
    private void addUpAndDown(RadioJLabel frame) {
        //上下拖动条
        ImageIcon downIconChat = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/down.png")));
        ImageIcon upIconChar = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/up.png")));

        JLabel downIconLabelChar = new JLabel();
        downIconLabelChar.setIcon(downIconChat);
        downIconLabelChar.setBounds(frame.getWidth() - 30, topLabel.getHeight()+10, 30, 30);
        frame.add(downIconLabelChar);

        JLabel upIconLabelChar = new JLabel();
        upIconLabelChar.setIcon(upIconChar);
        upIconLabelChar.setBounds(frame.getWidth() - 30, frame.getHeight() - 30-bottomBack.getHeight(), 30, 30);
        frame.add(upIconLabelChar);


        //上升下降添加点击事件
        frame.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() == 1) {  //向前
                    for (int i = 0; i < messages.size(); i++) {
                        messages.get(i).setLocation(messages.get(i).getX(), messages.get(i).getY()-10 );
                    }
                }
                if (e.getWheelRotation() == -1) {
                    for (int i = 0; i < messages.size(); i++) {
                        messages.get(i).setLocation(messages.get(i).getX(),  messages.get(i).getY()+10);
                    }
                }
            }
        });
        downIconLabelChar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < messages.size(); i++) {
                    messages.get(i).setLocation(messages.get(i).getX(),messages.get(i).getY()-1);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                for (int j = 0; j < 10; j++) {
                    for (int i = 0; i < messages.size(); i++) {
                        messages.get(i).setLocation(messages.get(i).getX(),messages.get(i).getY()-1);
                    }
                }
            }


        });

        upIconLabelChar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < messages.size(); i++) {
                    messages.get(i).setLocation(messages.get(i).getX(),messages.get(i).getY()+1);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                for (int j = 0; j < 10; j++) {
                    for (int i = 0; i < messages.size(); i++) {
                        messages.get(i).setLocation(messages.get(i).getX(),messages.get(i).getY()+1);
                    }
                }
            }


        });
    }


    public static class Type {
        public static int LEFT = 0;  //对方消息
        public static int RIGHT = 1;  //我方消息
    }
}
