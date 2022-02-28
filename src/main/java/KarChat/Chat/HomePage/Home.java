package KarChat.Chat.HomePage;

import KarChat.Chat.Helper.RemoveBackground;
import KarChat.Chat.Helper.RemoveBlack;
import KarChat.Chat.Helper.ToBufferedImage;
import KarChat.Chat.Login.Button.RoundButton;
import KarChat.Chat.Login.DynamicJLabel;
import KarChat.Chat.Login.Frameless;
import KarChat.Chat.Login.RadioJLabel;
import KarChat.Chat.Login.ShakeLabel;
import KarChat.Client.EchoClient;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Home implements ActionListener {

    private static JLabel menu;
    private final ImageIcon menuIcon;
    private static JLabel iconLabel ; //头像标签
    private static BufferedImage icon;  //头像图片
    private ImageIcon newMenuIcon;
    private JLabel menuBack=new JLabel();
    private JLabel menuTop=new JLabel();


    @SneakyThrows
    public Home() {
        System.setProperty("sun.java2d.noddraw", "true");  //防止输入法输入时白屏，禁用DirectDraw
        Frameless back = new Frameless(1300,843,false);
        back.setUndecorated(true);  //不要边框
        back.setIconImage(ImageIO.read(Resources.getResourceAsStream("login/sign.png")));

        RadioJLabelNew home = new RadioJLabelNew("");
        //创建右上角圆按钮，并添加监听器
        RoundButton Rbut1 = new RoundButton("", new Color(58, 124, 243, 190), new Color(92, 143, 236, 221), new Color(132, 171, 243, 181)) {
            @Override
            public void mouseClicked(MouseEvent e) {
                back.setExtendedState(JFrame.ICONIFIED);
            }

        };
        RoundButton Rbut2 = new RoundButton("", new Color(243, 58, 101, 192), new Color(238, 70, 109, 189), new Color(252, 108, 141, 189)) {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(1);
            }

        };


        new Thread() {  //开启窗口动画
            @SneakyThrows
            @Override
            public void run() {
                int MAXTRANS=1;  //透明度
                while (MAXTRANS <= 255) {
                    Thread.sleep(6);
                    home.setColor(new Color(254, 250, 250,MAXTRANS));
                    home.repaint();
                    MAXTRANS += 5;
                }
            }
        }.start();

        {  //菜单加入图片
            menuIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/menubar2.png")));
            menu = new JLabel(menuIcon);
            final boolean[] keepFlag = {true};
            final int[] WIDTHNOW = {0};  //加入状态判断，防止来回试探卡bug
            menu.addMouseListener(new MouseAdapter() {
                @SneakyThrows
                @Override
                public void mouseEntered(MouseEvent e) {
                    newMenuIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/menubar3.png")));
                    new Thread(){  //菜单展开
                        @SneakyThrows
                        @Override
                        public void run() {
                            int WIDTH;
                            if (WIDTHNOW[0]==0) {  //加入判断
                                WIDTH = -(newMenuIcon.getIconWidth() - menuIcon.getIconWidth());
                            }else {
                                WIDTH = WIDTHNOW[0];
                            }
                            keepFlag[0] = true;
                            menuTop.setIcon(newMenuIcon);
                            menuBack.setBounds(70, 20, newMenuIcon.getIconWidth(), newMenuIcon.getIconHeight());
                                label:
                                {
                                    while (WIDTH < 0) {
                                        Thread.sleep(1);
                                        menuTop.setBounds(WIDTH, 0, newMenuIcon.getIconWidth(), menuIcon.getIconHeight());
                                        WIDTH += 3;
                                        if (!keepFlag[0]) {
                                            WIDTHNOW[0] = WIDTH;
                                            keepFlag[0] = false;
                                            break label;
                                        }
                                    }
                                    WIDTHNOW[0] = 0;  //清零
                                }
                            if (WIDTH < 0) {
                                mouseExited(e);
                            }
                            }
                    }.start();

                }


            });
            menuBack.addMouseListener(new MouseAdapter() {  //菜单收缩
                @Override
                public void mouseExited(MouseEvent e) {
                    new Thread(){
                        @SneakyThrows
                        @Override
                        public void run() {
                            int WIDTH = WIDTHNOW[0];
                            keepFlag[0] = false;
                            menuBack.setBounds(70, 20, newMenuIcon.getIconWidth(), newMenuIcon.getIconHeight());
                            label2:
                            {
                                while (WIDTH > -(newMenuIcon.getIconWidth() - menuIcon.getIconWidth())) {
                                    Thread.sleep(1);
                                    menuTop.setBounds(WIDTH, 0, newMenuIcon.getIconWidth(), menuIcon.getIconHeight());
                                    WIDTH -= 3;
                                    if (keepFlag[0]) {
                                        WIDTHNOW[0] = WIDTH;
                                        keepFlag[0] = true;
                                        break label2;
                                    }
                                }
                                WIDTHNOW[0] = -(newMenuIcon.getIconWidth() - menuIcon.getIconWidth());  //清零
                            }
                        }
                    }.start();
                }
            });
        }
        {  //加入头像
            EchoClient.getIcon = true;  //修改标志位
        }

        back.setLocation(310,120);
        Rbut1.setArc(25, 25);   //修改小按钮的圆角弧度
        Rbut2.setArc(25, 25);

        home.setBounds(0,50,1300,743);



        back.add(menu);  //加入菜单
        back.add(menuTop);  //加入背景
        menuBack.add(menuTop);
        back.add(menuBack);  //加入背景
        back.add(Rbut1);  //加入右上角按钮
        back.add(Rbut2);
        back.add(home);  //加入主页面

        menu.setBounds(50, 20, menuIcon.getIconWidth(), menuIcon.getIconHeight());
        Rbut1.setBounds(back.getWidth() + 415, 60, 25, 25);  //设置小按钮位置
        Rbut2.setBounds(back.getWidth() + 450, 60, 25, 25);

        back.setVisible(true);  //窗口可视化

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * 设置头像
     */
    @SneakyThrows
    public static void setIcon(BufferedImage Icon) {
        icon = Icon;  //保存图片
        ImageIcon defaultIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/icon.png")));
        //修改一下图像大小
        BufferedImage realIcon= ToBufferedImage.toBufferedImage(Icon.getScaledInstance(defaultIcon.getIconWidth(),defaultIcon.getIconHeight(),0));  //将图片改为合适的大小，并转化为BufferedImage
        //去除黑色背景
        BufferedImage transparencyIcon = RemoveBackground.ByteToBufferedImage(RemoveBackground.transferAlpha(realIcon));
        iconLabel = new JLabel(defaultIcon);  //默认头像
        menu.setLayout(null);
        menu.add(iconLabel); //加入头像标签

        iconLabel.addMouseListener(new MouseAdapter() {
            boolean flag = false;
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!flag) {  //展开
                    flag = true;//修改标记位


                }

            }
        });
        iconLabel.setBounds(-20, -10, icon.getWidth(), icon.getHeight());
        iconLabel.setIcon(new ImageIcon(transparencyIcon));  //设置用户自己的头像

    }

    public static void main(String[] args) {
        new Home();
    }

}
