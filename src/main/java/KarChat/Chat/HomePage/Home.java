package KarChat.Chat.HomePage;

import KarChat.Chat.Action.Minimize;
import KarChat.Chat.Helper.RemoveBackground;
import KarChat.Chat.Helper.ToBufferedImage;
import KarChat.Chat.HomePage.Label.InnerLabel;
import KarChat.Chat.Login.Button.RoundButton;
import KarChat.Chat.Login.Frameless;
import KarChat.Chat.Login.RadioJLabel;
import KarChat.Chat.Sound.PlaySound;
import KarChat.Client.EchoClient;
import KarChat.Game.Panel.MainPanel;
import com.sun.awt.AWTUtilities;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Observable;

import static KarChat.Chat.HomePage.MenuContent.iconNameChat;

public class Home extends Observable implements ActionListener , Minimize {

    public static JLabel menu;
    public static ImageIcon menuIcon;
    public static JLabel iconLabel ; //头像标签
    public static BufferedImage icon;  //头像图片
    public static MouseAdapter menuOpen;  //打开菜单鼠标点击事件
    public static JLabel game1Back;
    public static JLabel game1Top;
    public static ImageIcon game1Icon;
    public static ImageIcon game2Icon;
    public static BufferedImage transparencyIcon;
    private final ImageIcon game2IconNew;
    public static ImageIcon game3Icon;
    private final ImageIcon game3IconNew;
    private final MouseAdapter game1Adapter;  //游戏一动效事件
    public static RadioJLabelNew menuHomeUser;  //点击菜单展开的内容背景
    public static RadioJLabelNew menuHomeUser1;
    public static RadioJLabelNew menuHomeUser2;
    public static RadioJLabelNew menuHomeUser3;
    public static RadioJLabelNew menuHomeUser4;
    public static RadioJLabelNew menuHomeUser5;
    public static RadioJLabelNew menuHomeUser6;
    public static MouseAdapter mouseAd;  //菜单伸缩事件
    public static JLabel menuHomeBack;    //点击菜单展开的内容背景
    public static Frameless back;
    public static RadioJLabelNew home;
    public static RadioJLabel homeBack;
    private ImageIcon game3IconOn;
    public static JLabel game3Back;
    public static JLabel game3Top;
    private ImageIcon game2IconOn;
    public static JLabel game2Back;
    public static JLabel game2Top;
    private ImageIcon game1IconOn;
    private ImageIcon newMenuIcon;
    public static JLabel menuBack=new JLabel();
    public static JLabel menuTop=new JLabel();
    public static boolean[] menuFlag = {false}; //是否打开菜单栏0
    public static boolean[] menuFlag1 = {false}; //是否打开菜单栏1
    public static boolean[] menuFlag2 = {false}; //是否打开菜单栏2
    public static boolean[] menuFlag3 = {false}; //是否打开菜单栏3
    public static boolean[] menuFlag4 = {false}; //是否打开菜单栏4
    public static boolean[] menuFlag5 = {false}; //是否打开菜单栏5
    public static boolean[] menuFlag6 = {false}; //是否打开菜单栏6
    public static int openMenuIndex = -1;  //是否打开菜单栏6
    public static boolean isMenuChild = false;  //判断是否是menu中的组件，防止一直弹
    public static JLabel game1;  //三个游戏标签
    public static JLabel game2;  //三个游戏标签
    public static JLabel game3;  //三个游戏标签
    public static  boolean inHome;
    private boolean iconified;
    private int xOld;
    private int yOld;
    public static InnerLabel[] chatContent;


    @SneakyThrows
    public Home() {
//        System.setProperty("sun.java2d.noddraw", "true");  //防止输入法输入时白屏，禁用DirectDraw
        back = new Frameless(1300,843,false);
        back.setUndecorated(true);  //不要边框
        back.setIconImage(ImageIO.read(Resources.getResourceAsStream("login/sign.png")));
        //背景阴影
        homeBack = new RadioJLabel("");
        homeBack.setColor(new Color(239, 238, 238));
        homeBack.setArc(40,40);

        //最大化最小化动画
        back.addWindowListener(new WindowAdapter() {
            @SneakyThrows
            @Override
            public void windowIconified(WindowEvent e) {
                AWTUtilities.setWindowOpacity(back, 0);  //半透明
                Home.super.setChanged();  //设置变化点
                Home.super.notifyObservers(true);
                //最小化状态
                iconified = true;
            }

            @SneakyThrows
            @Override
            public void windowDeiconified(WindowEvent e) {
                AWTUtilities.setWindowOpacity(back, 0);  //半透明
                iconified = false;
                maximize();
                Thread.sleep(1000);
            }

        });


        {  //初始化三个标签
            game1Icon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/game2.png")));
            game1 = new JLabel(game1Icon);
            game2Icon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/game7.png")));
            game2 = new JLabel(game2Icon);
            game3Icon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/game6.png")));
            game3 = new JLabel(game3Icon);
        }


        home = new RadioJLabelNew("");
        menuHomeBack = new JLabel("");   //创建菜单背景
        menuHomeUser = new RadioJLabelNew("");   //创建菜单内容
        menuHomeUser.setColor(new Color(239, 238, 238));
        menuHomeUser1 = new RadioJLabelNew("");
        menuHomeUser1.setColor(new Color(239, 238, 238));
        menuHomeUser2 = new RadioJLabelNew("");
        menuHomeUser2.setColor(new Color(239, 238, 238));
        menuHomeUser3 = new RadioJLabelNew("");
        menuHomeUser3.setColor(new Color(239, 238, 238));
        menuHomeUser4 = new RadioJLabelNew("");
        menuHomeUser4.setColor(new Color(239, 238, 238));
        menuHomeUser5 = new RadioJLabelNew("");
        menuHomeUser5.setColor(new Color(239, 238, 238));
        menuHomeUser6 = new RadioJLabelNew("");
        menuHomeUser6.setColor(new Color(239, 238, 238));



        //处理鼠标点击事件
        home.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xOld = e.getX();  //存储点击时的坐标
                yOld = e.getY();
            }
        });
        //处理鼠标拖拽事件
        home.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int xOnScreen = e.getXOnScreen(); //获取当前鼠标拖拽时x坐标
                int yOnScreen = e.getYOnScreen(); //获取当前鼠标拖拽时y坐标
                int xx = xOnScreen - xOld;  //移动差值
                int yy = yOnScreen - yOld;
                back.setLocation(xx,yy-50);  //移动窗口
            }
        });





        //创建右上角圆按钮，并添加监听器
        RoundButton Rbut1 = new RoundButton("", new Color(58, 124, 243, 190), new Color(92, 143, 236, 221), new Color(132, 171, 243, 181)) {
            @Override
            public void mouseClicked(MouseEvent e) {
                back.setExtendedState(JFrame.ICONIFIED);
            }

        };
        RoundButton Rbut2 = new RoundButton("", new Color(243, 58, 101, 192), new Color(238, 70, 109, 189), new Color(252, 108, 141, 189)) {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                float MAXTRANS=1;  //透明度
                while (MAXTRANS >= 0) {
                    Thread.sleep(2);
                    AWTUtilities.setWindowOpacity(back, MAXTRANS);  //半透明
                    MAXTRANS -= 0.03;
                }
                System.exit(1);
            }

        };

        home.setArc(30,30);

        menuHomeUser.setArc(30,30);
        menuHomeUser1.setArc(30,30);
        menuHomeUser2.setArc(30,30);
        menuHomeUser3.setArc(30,30);
        menuHomeUser4.setArc(30,30);
        menuHomeUser5.setArc(30,30);
        menuHomeUser6.setArc(30,30);
        home.setColor(new Color(166, 163, 163));
        home.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                inHome = true;  //进入了主页面
            }

            @Override
            public void mouseExited(MouseEvent e) {
                inHome = false;
            }
        });
//        home.setColor(new Color(239, 238, 238));


//

        {  //菜单加入图片
            menuIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/menubar2.png")));
            menu = new JLabel(menuIcon);
            //初始化菜单
            Menu.init();

        }
        {  //初始化菜单栏
            new MenuContent().InitAddFriends(menuHomeUser1);
            MenuContent.InitColor(menuHomeUser3);
            MenuContent.InitChat(menuHomeUser2);

            new Thread(){
                @SneakyThrows
                @Override
                public void run() {
                    Thread.sleep(2000);
                    chatContent = new InnerLabel[MenuContent.iconLengthChat];
                    for (int i = 0; i < MenuContent.iconLengthChat; i++) {
                        chatContent[i] = new InnerLabel();
                        chatContent[i].setSize(0, 0);
                        back.add(chatContent[i]);


                        //初始化用户聊天界面的存储信息
                        //修改一下图像大小
                        BufferedImage chatIcon = ToBufferedImage.toBufferedImage(Home.transparencyIcon.getScaledInstance(45, 45, 0));  //将图片改为合适的大小，并转化为BufferedImage
                        //去除黑色背景
                        BufferedImage newChatIcon = RemoveBackground.ByteToBufferedImage(RemoveBackground.transferAlpha(chatIcon));
                        chatContent[i].mine = newChatIcon;  //我的头像

                        //修改一下图像大小
                        BufferedImage friendIcon = ToBufferedImage.toBufferedImage(MenuContent.Chaticons[i].getScaledInstance(45, 45, 0));  //将图片改为合适的大小，并转化为BufferedImage
                        //去除黑色背景
                        BufferedImage newFriendIcon = RemoveBackground.ByteToBufferedImage(RemoveBackground.transferAlpha(friendIcon));

                        chatContent[i].friend = newFriendIcon;  //好友头像
                        chatContent[i].friendName = iconNameChat[i];  //储存好友姓名

                        MenuContent.initMessage(iconNameChat[i]);  //初始化聊天内容
                    }
                }
            }.start();
        }
        {  //加入头像
            EchoClient.getIcon = true;  //修改标志位
        }
        {  //三个游戏标签加入图像和动效
            final int[] GAME = {1};  //默认从第一个开始
            final boolean[] keepFlag3 = {true};  //判断标签3是否在进行，如果被打断就存下当前的位置
            final int[] WIDTHNOW3 = {0};  //加入状态判断，防止来回试探卡bug
            final boolean[] keepFlag2 = {true};
            final int[] WIDTHNOW2 = {0};  //加入状态判断，防止来回试探卡bug

            //第一个游戏
            game1IconOn = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/game1.png")));
            //背景
            game1Back = new JLabel();
            game1Top = new JLabel();
            final boolean[] keepFlag = {true};
            final int[] WIDTHNOW = {0};  //加入状态判断，防止来回试探卡bug
            boolean[] canGo = {true};  //判断能否继续进行，必须在一个动效执行完后再执行别的动效
            game1Top.addMouseListener(new MouseAdapter() {
                @SneakyThrows
                @Override
                public void mouseClicked(MouseEvent e) {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+"http://localhost:8080/KarCharWeb/home/select");
                    Home.back.setExtendedState(JFrame.ICONIFIED);
                }
            });
            game1Adapter = new MouseAdapter() {
                @SneakyThrows
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (canGo[0]) {
                        canGo[0] = false;  //禁止其他线程执行
                        game1IconOn = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/game1.png")));
                        new Thread() {  //菜单展开
                            @SneakyThrows
                            @Override
                            public void run() {
                                All:
                                {
                                    int WIDTH;
                                    int GAME2WIDTH;
                                    int GAME3WIDTH;
                                    if (WIDTHNOW[0] == 0) {  //加入判断
                                        WIDTH = -(game1IconOn.getIconWidth() - game1Icon.getIconWidth());
                                    } else {
                                        WIDTH = WIDTHNOW[0];
                                    }
                                    GAME2WIDTH = 484;  //开始game2,game3要移动的位置
                                    GAME3WIDTH = 624;
                                    keepFlag[0] = true;
                                    game1Top.setIcon(game1IconOn);
                                    label:
                                    {

                                        //判断当前打开的是哪个
                                        switch (GAME[0]) {
                                            case 2:
                                                new Thread() {
                                                    @SneakyThrows
                                                    @Override
                                                    public void run() {
                                                        int WIDTH = WIDTHNOW2[0];
                                                        int GAME3WIDTH = 624 + (game2IconOn.getIconWidth() - game2Icon.getIconWidth());
                                                        keepFlag2[0] = false;
                                                        game2Back.setBounds(480, 100, game2IconOn.getIconWidth(), game2IconOn.getIconHeight());
                                                        label2:
                                                        {
                                                            while (WIDTH > -55 - (game2IconOn.getIconWidth() - game2Icon.getIconWidth())) {
                                                                Thread.sleep(1);
                                                                game2Top.setBounds(WIDTH, 4, game2IconOn.getIconWidth(), game2Icon.getIconHeight());
                                                                game3.setBounds(GAME3WIDTH, 100, game3Icon.getIconWidth(), game3IconOn.getIconHeight());

                                                                if (WIDTH < 285 - (game2IconOn.getIconWidth() - game2Icon.getIconWidth()))
                                                                    game2.setIcon(game2Icon);
                                                                WIDTH -= 7;
                                                                GAME3WIDTH -= 7;
                                                                if (keepFlag2[0]) {
                                                                    WIDTHNOW2[0] = WIDTH;
                                                                    keepFlag2[0] = true;
                                                                    break label2;
                                                                }
                                                            }
                                                            WIDTHNOW2[0] = -(game2IconOn.getIconWidth() - game2Icon.getIconWidth());  //清零
                                                            game2Back.setSize(0, 0);
                                                        }
                                                    }
                                                }.start();
                                                GAME[0] = 1;
                                                break;


                                            case 3:
                                                new Thread() {
                                                    @SneakyThrows
                                                    @Override
                                                    public void run() {
                                                        int WIDTH = WIDTHNOW3[0];
                                                        keepFlag3[0] = false;
                                                        label2:
                                                        {
                                                            while (WIDTH > -55 - (game3IconOn.getIconWidth() - game3Icon.getIconWidth())) {
                                                                Thread.sleep(1);
                                                                game3Top.setBounds(WIDTH, 4, game3IconOn.getIconWidth(), game3Icon.getIconHeight());
                                                                if (WIDTH < 315 - (game3IconOn.getIconWidth() - game3Icon.getIconWidth()))
                                                                    game3.setIcon(game3Icon);
                                                                WIDTH -= 7;
                                                                if (keepFlag3[0]) {
                                                                    WIDTHNOW3[0] = WIDTH;
                                                                    keepFlag3[0] = true;
                                                                    break label2;
                                                                }
                                                            }
                                                            WIDTHNOW3[0] = -(game3IconOn.getIconWidth() - game3Icon.getIconWidth());  //清零
                                                            game3Back.setSize(0, 0);
                                                        }
                                                    }
                                                }.start();
                                                GAME[0] = 1;
                                                break;
                                                default:
                                                    canGo[0] = true;
                                                    break All;
                                        }

                                        Thread.sleep(900);
                                        game1Back.setBounds(340, 100, game1IconOn.getIconWidth(), game1IconOn.getIconHeight());

                                        while (WIDTH < -48) {
                                            Thread.sleep(1);
                                            game1Top.setBounds(WIDTH, 3, game1IconOn.getIconWidth(), game1Icon.getIconHeight());
                                            //移动game2,game3
                                            game2.setBounds(GAME2WIDTH, 100, game2Icon.getIconWidth(), game2IconOn.getIconHeight());
                                            game3.setBounds(GAME3WIDTH, 100, game3Icon.getIconWidth(), game3IconOn.getIconHeight());

                                            WIDTH += 7;
                                            GAME2WIDTH += 7;
                                            GAME3WIDTH += 7;
                                            if (!keepFlag[0]) {
                                                WIDTHNOW[0] = WIDTH;
                                                keepFlag[0] = false;
                                                canGo[0] = true;
                                                break label;
                                            }
                                        }
                                        WIDTHNOW[0] = 0;  //清零
                                        canGo[0] = true;
                                    }
//                            if (WIDTH < 0) {
//                                mouseExited(e);
//                            }
                                }
                            }
                        }.start();
                    }
                }
            };
            game1.addMouseListener(game1Adapter);


            //第二个游戏
            game2IconNew = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/game3.png")));
            game2IconOn = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/game4.png")));
            //背景
            game2Back = new JLabel();
            game2Top = new JLabel();
            game2Top.addMouseListener(new MouseAdapter() {
                @SneakyThrows
                @Override
                public void mouseClicked(MouseEvent e) {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+"https://f01-1309918226.file.myqcloud.com/13/2022/04/22/KarGoBang2/loading2.html?x-cos-traffic-limit=819200");
                    Home.back.setExtendedState(JFrame.ICONIFIED);
                }
            });
            game2.addMouseListener(new MouseAdapter() {
                @SneakyThrows
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (canGo[0]) {
                        canGo[0] = false;  //禁止其他线程执行
                        game2IconOn = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/game4.png")));
                        new Thread() {  //菜单展开
                            @SneakyThrows
                            @Override
                            public void run() {
                                All:
                                {
                                    int WIDTH;
                                    int GAME3WIDTH;
                                    if (WIDTHNOW2[0] == 0) {  //加入判断
                                        WIDTH = -(game2IconOn.getIconWidth() - game2Icon.getIconWidth());
                                    } else {
                                        WIDTH = WIDTHNOW2[0];
                                    }
                                    GAME3WIDTH = 624;  //比570多了54
                                    keepFlag2[0] = true;
                                    game2Top.setIcon(game2IconOn);
                                    game2.setIcon(game2IconNew);
                                    label:
                                    {

                                        //判断当前打开的是哪个
                                        switch (GAME[0]) {
                                            case 1:
                                                new Thread() {
                                                    @SneakyThrows
                                                    @Override
                                                    public void run() {
                                                        int WIDTH = WIDTHNOW[0];
                                                        int GAME2WIDTH = 484 + (game1IconOn.getIconWidth() - game1Icon.getIconWidth());
                                                        int GAME3WIDTH = 624 + (game1IconOn.getIconWidth() - game1Icon.getIconWidth());
                                                        keepFlag[0] = false;
                                                        game1Back.setBounds(340, 100, game1IconOn.getIconWidth(), game1IconOn.getIconHeight());
                                                        label2:
                                                        {
                                                            while (WIDTH > -55 - (game1IconOn.getIconWidth() - game1Icon.getIconWidth())) {
                                                                Thread.sleep(1);
                                                                game1Top.setBounds(WIDTH, 4, game1IconOn.getIconWidth(), game1Icon.getIconHeight());
                                                                game2.setBounds(GAME2WIDTH, 100, game2Icon.getIconWidth(), game2IconOn.getIconHeight());
                                                                game3.setBounds(GAME3WIDTH, 100, game3Icon.getIconWidth(), game3IconOn.getIconHeight());

                                                                WIDTH -= 7;
                                                                GAME2WIDTH -= 7;
                                                                GAME3WIDTH -= 7;
                                                                if (keepFlag[0]) {
                                                                    WIDTHNOW[0] = WIDTH;
                                                                    canGo[0] = true;
                                                                    break label2;
                                                                }
                                                            }
                                                            WIDTHNOW[0] = -(game1IconOn.getIconWidth() - game1Icon.getIconWidth());  //清零
                                                            game1Back.setSize(0, 0);
                                                        }
                                                    }
                                                }.start();
                                                GAME[0] = 2;
                                                break;
                                            case 3:
                                                new Thread() {
                                                    @SneakyThrows
                                                    @Override
                                                    public void run() {
                                                        int WIDTH = WIDTHNOW3[0];
                                                        keepFlag3[0] = false;
                                                        game3Back.setBounds(620, 100, game3IconOn.getIconWidth(), game3IconOn.getIconHeight());
                                                        label2:
                                                        {
                                                            while (WIDTH > -55 - (game3IconOn.getIconWidth() - game3Icon.getIconWidth())) {
                                                                Thread.sleep(1);
                                                                game3Top.setBounds(WIDTH, 4, game3IconOn.getIconWidth(), game3Icon.getIconHeight());
                                                                if (WIDTH < 315 - (game3IconOn.getIconWidth() - game3Icon.getIconWidth()))
                                                                    game3.setIcon(game3Icon);
                                                                WIDTH -= 7;
                                                                if (keepFlag3[0]) {
                                                                    WIDTHNOW3[0] = WIDTH;
                                                                    keepFlag3[0] = true;
                                                                    break label2;
                                                                }
                                                            }
                                                            WIDTHNOW3[0] = -(game3IconOn.getIconWidth() - game3Icon.getIconWidth());  //清零
                                                            game3Back.setSize(0, 0);
                                                        }
                                                    }
                                                }.start();
                                                GAME[0] = 2;
                                                break;
                                                default:
                                                    canGo[0] = true;
                                                    break All;
                                        }

                                        Thread.sleep(700);
                                        game2Back.setBounds(480, 100, game2IconOn.getIconWidth(), game2IconOn.getIconHeight());

                                        while (WIDTH < -48) {
                                            Thread.sleep(1);
                                            game2Top.setBounds(WIDTH, 4, game2IconOn.getIconWidth(), game2Icon.getIconHeight());
                                            game2.setIcon(game2IconNew);
                                            game3.setBounds(GAME3WIDTH, 100, game3Icon.getIconWidth(), game3IconOn.getIconHeight());

                                            WIDTH += 7;
                                            GAME3WIDTH += 7;
                                            if (!keepFlag2[0]) {
                                                WIDTHNOW2[0] = WIDTH;
                                                keepFlag2[0] = false;
                                                break label;
                                            }
                                        }
                                        WIDTHNOW2[0] = 0;  //清零
                                        canGo[0] = true;

                                    }
//                            if (WIDTH < 0) {
//                                mouseExited(e);
//                            }
                                }
                            }
                        }.start();
                    }
                }
            });

            //第三个游戏
            game3IconNew = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/game8.png")));
            game3IconOn = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/game5.png")));
            //背景
            game3Back = new JLabel();
            game3Top = new JLabel();
            game3Top.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new Thread(){
                        @SneakyThrows
                        @Override
                        public void run() {
                            PlaySound.play("sound/loginsuccess.mp3");
                        }
                    }.start();
                    JFrame btn = new JFrame();

                    btn.add(MainPanel.getInstance());
                    btn.setTitle("五子棋");
                    btn.setSize(800, 800);
                    btn.setResizable(false);
                    btn.setVisible(true);
                    Home.back.dispose();  //关闭页面
                }
            });
             game3.addMouseListener(new MouseAdapter() {

                 @SneakyThrows
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (canGo[0]) {
                        canGo[0] = false;  //禁止其他线程执行
                        game3IconOn = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/game5.png")));
                        new Thread() {  //菜单展开
                            @SneakyThrows
                            @Override
                            public void run() {
                                All:
                                {
                                    int WIDTH;
                                    if (WIDTHNOW3[0] == 0) {  //加入判断
                                        WIDTH = -(game3IconOn.getIconWidth() - game3Icon.getIconWidth());
                                    } else {
                                        WIDTH = WIDTHNOW3[0];
                                    }
                                    keepFlag3[0] = true;
                                    game3Top.setIcon(game3IconOn);
                                    game3.setIcon(game3IconNew);
//                                game3Back.setBounds(620, 100, game3IconOn.getIconWidth(), game3IconOn.getIconHeight());
                                    label:
                                    {

                                        //判断当前打开的是哪个
                                        switch (GAME[0]) {
                                            case 1:
                                                new Thread() {
                                                    @SneakyThrows
                                                    @Override
                                                    public void run() {
                                                        int WIDTH = WIDTHNOW[0];
                                                        int GAME2WIDTH = 484 + (game1IconOn.getIconWidth() - game1Icon.getIconWidth());
                                                        int GAME3WIDTH = 624 + (game1IconOn.getIconWidth() - game1Icon.getIconWidth());
                                                        keepFlag[0] = false;
                                                        game1Back.setBounds(340, 100, game1IconOn.getIconWidth(), game1IconOn.getIconHeight());
                                                        label2:
                                                        {
                                                            while (WIDTH > -55 - (game1IconOn.getIconWidth() - game1Icon.getIconWidth())) {
                                                                Thread.sleep(1);
                                                                game1Top.setBounds(WIDTH, 4, game1IconOn.getIconWidth(), game1Icon.getIconHeight());
                                                                game2.setBounds(GAME2WIDTH, 100, game2Icon.getIconWidth(), game2IconOn.getIconHeight());
                                                                game3.setBounds(GAME3WIDTH, 100, game3Icon.getIconWidth(), game3IconOn.getIconHeight());

                                                                WIDTH -= 7;
                                                                GAME2WIDTH -= 7;
                                                                GAME3WIDTH -= 7;
                                                                if (keepFlag[0]) {
                                                                    WIDTHNOW[0] = WIDTH;
                                                                    canGo[0] = true;
                                                                    break label2;
                                                                }
                                                            }
                                                            WIDTHNOW[0] = -(game1IconOn.getIconWidth() - game1Icon.getIconWidth());  //清零
                                                            game1Back.setSize(0, 0);
                                                        }
                                                    }
                                                }.start();
                                                GAME[0] = 3;
                                                break;
                                            case 2:
                                                new Thread() {
                                                    @SneakyThrows
                                                    @Override
                                                    public void run() {
                                                        int WIDTH = WIDTHNOW2[0];
                                                        int GAME3WIDTH = 624 + (game2IconOn.getIconWidth() - game2Icon.getIconWidth());
                                                        keepFlag2[0] = false;
                                                        game2Back.setBounds(480, 100, game2IconOn.getIconWidth(), game2IconOn.getIconHeight());
                                                        label2:
                                                        {
                                                            while (WIDTH > -55 - (game2IconOn.getIconWidth() - game2Icon.getIconWidth())) {
                                                                Thread.sleep(1);
                                                                game2Top.setBounds(WIDTH, 4, game2IconOn.getIconWidth(), game2Icon.getIconHeight());
                                                                game3.setBounds(GAME3WIDTH, 100, game3Icon.getIconWidth(), game3IconOn.getIconHeight());

                                                                if (WIDTH < 285 - (game2IconOn.getIconWidth() - game2Icon.getIconWidth()))
                                                                    game2.setIcon(game2Icon);
                                                                WIDTH -= 7;
                                                                GAME3WIDTH -= 7;
                                                                if (keepFlag2[0]) {
                                                                    WIDTHNOW2[0] = WIDTH;
                                                                    keepFlag2[0] = true;
                                                                    break label2;
                                                                }
                                                            }
                                                            WIDTHNOW2[0] = -(game2IconOn.getIconWidth() - game2Icon.getIconWidth());  //清零
                                                            game2Back.setSize(0, 0);
                                                            canGo[0] = true;
                                                        }
                                                    }
                                                }.start();
                                                GAME[0] = 3;
                                                break;
                                            default:
                                                canGo[0] = true;
                                                break All;
                                        }
                                        Thread.sleep(700);

                                        game3Back.setBounds(620, 100, game3IconOn.getIconWidth(), game3IconOn.getIconHeight());
                                        while (WIDTH < -48) {
                                            Thread.sleep(1);
                                            game3Top.setBounds(WIDTH, 4, game3IconOn.getIconWidth(), game3Icon.getIconHeight());
                                            game3.setIcon(game3IconNew);
                                            WIDTH += 7;
                                            if (!keepFlag3[0]) {
                                                WIDTHNOW3[0] = WIDTH;
                                                canGo[0] = true;
                                                break label;
                                            }
                                        }
                                        WIDTHNOW3[0] = 0;  //清零
                                        canGo[0] = true;

                                    }
//                            if (WIDTH < 0) {
//                                mouseExited(e);
//                            }
                                }
                            }
                        }.start();
                    }

                }

            });

        }

        back.setLocation(310,120);
        Rbut1.setArc(25, 25);   //修改小按钮的圆角弧度
        Rbut2.setArc(25, 25);
        {  //开始让game1展开
            int WIDTH = 0;
            int GAME2WIDTH = 430 + (game1IconOn.getIconWidth() - game1Icon.getIconWidth());
            int GAME3WIDTH = 570 + (game1IconOn.getIconWidth() - game1Icon.getIconWidth());
            game1IconOn = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/game1.png")));
            game2.setBounds(GAME2WIDTH, 100, game2Icon.getIconWidth(), game2IconOn.getIconHeight());
            game3.setBounds(GAME3WIDTH, 100, game3Icon.getIconWidth(), game3IconOn.getIconHeight());
            game1Back.setBounds(296, 100, game1IconOn.getIconWidth(), game1IconOn.getIconHeight());
            game1Top.setBounds(WIDTH-12, 3, game1IconOn.getIconWidth(), game1Icon.getIconHeight());
            game1Top.setIcon(game1IconOn);

        }



        LoadingHome loadingHome = new LoadingHome();//加入加载界面
        back.add(loadingHome);
        AWTUtilities.setWindowOpacity(back, 0);  //半透明
        back.setVisible(true);  //窗口可视化
        float MAXTRANS = 0;  //透明度
        while (MAXTRANS <= 1.0) {
            Thread.sleep(4);
            AWTUtilities.setWindowOpacity(back, MAXTRANS);  //半透明
            MAXTRANS += 0.02;
        }

        new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(2000);

                float MAXTRANS = 1;  //透明度
                while (MAXTRANS >= 0) {
                    Thread.sleep(4);
                    AWTUtilities.setWindowOpacity(back, MAXTRANS);  //半透明
                    MAXTRANS -= 0.02;
                }

                loadingHome.setSize(0, 0);
                back.add(menu);  //加入菜单
                back.add(menuTop);  //加入背景
                menuBack.add(menuTop);
                game1Back.add(game1Top);
                game2Back.add(game2Top);
                game3Back.add(game3Top);
                back.add(menuBack);  //加入背景
                back.add(menuHomeBack);  //点击菜单展开的内容背景
                menuHomeBack.add(menuHomeUser);   //点击菜单展开的内容
                menuHomeBack.add(menuHomeUser1);   //点击菜单展开的内容
                menuHomeBack.add(menuHomeUser2);   //点击菜单展开的内容
                menuHomeBack.add(menuHomeUser3);   //点击菜单展开的内容
                menuHomeBack.add(menuHomeUser4);   //点击菜单展开的内容
                menuHomeBack.add(menuHomeUser5);   //点击菜单展开的内容
                menuHomeBack.add(menuHomeUser6);   //点击菜单展开的内容

                back.add(game1);  //加入背景
                back.add(game1Back);  //加入背景
                back.add(game2);  //加入背景
                back.add(game2Back);  //加入背景
                back.add(game3);  //加入背景
                back.add(game3Back);  //加入背景
                home.add(Rbut1);  //加入右上角按钮
                home.add(Rbut2);
                back.add(homeBack); //加入背景
                back.add(home);  //加入主页面


                home.setBounds(0, 50, 1300, 743);
                homeBack.setBounds(240, 80, 960, 673);
                menu.setBounds(50, 20, menuIcon.getIconWidth(), menuIcon.getIconHeight());
                game1.setBounds(290, 100, game1Icon.getIconWidth(), game1IconOn.getIconHeight());
                Rbut1.setBounds(home.getWidth() - 90, 20, 25, 25);  //设置小按钮位置
                Rbut2.setBounds(home.getWidth() - 50, 20, 25, 25);


                back.setVisible(true);  //窗口可视化
                float MAXTRANS2 = 0;  //透明度
                while (MAXTRANS2 <= 1.0) {
                    Thread.sleep(4);
                    AWTUtilities.setWindowOpacity(back, MAXTRANS2);  //半透明
                    MAXTRANS2 += 0.02;
                }
                AWTUtilities.setWindowOpacity(back, 1);  //半透明

            }
        }.start();




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
        transparencyIcon = RemoveBackground.ByteToBufferedImage(RemoveBackground.transferAlpha(realIcon));
        iconLabel = new JLabel(defaultIcon);  //默认头像
        menu.setLayout(null);
        menu.add(iconLabel); //加入头像标签

        iconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isMenuChild = true;  //是子组件
            }
        });
        final boolean[] canDo = {true,true};
        menuOpen = new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!menuFlag[0]&& canDo[0]&&canDo[1]) {  //展开
                    canDo[0] = false;
                    canDo[1] = false;
                    menuFlag[0] = true;//修改标记位
                    menuHomeBack.setBounds(115, 10, menuIcon.getIconWidth()+600, menuIcon.getIconHeight()+20);
                    menuHomeUser.setBounds(124, 8, menuIcon.getIconWidth()+200, menuIcon.getIconHeight());

                    if (openMenuIndex != -1) {
                        Menu.DealWithOldMenuCont(openMenuIndex,canDo);
                    }

                    new Thread() {  //菜单栏开启窗口动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS=1;  //透明度
                            while (MAXTRANS <= 255) {
                                Thread.sleep(6);
                                menuHomeUser.setColor(new Color(239, 238, 238,MAXTRANS));
                                menuHomeUser.repaint();
                                MAXTRANS += 5;
                            }
                            canDo[0] = true;
                        }
                    }.start();
                    new Thread() {  //菜单栏开启窗口动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS=255;  //透明度
                            while (MAXTRANS >= 100) {
                                Thread.sleep(6);
                                home.setColor(new Color(166, 163, 163,MAXTRANS));
                                game1Back.remove(game1Top);
                                game2Back.remove(game2Top);
                                game3Back.remove(game3Top);
                                homeBack.setColor(new Color(239, 238, 238, MAXTRANS));
                                game1.setSize(0,0);
                                game2.setSize(0,0);
                                game3.setSize(0,0);
                                home.repaint();
                                MAXTRANS -= 7;
                            }
                            canDo[1] = true;
                        }
                    }.start();
                    openMenuIndex = 0;

                }else if (menuFlag[0]&& canDo[0]&&canDo[1]) {
                    canDo[0] = false;
                    canDo[1] = false;
                    menuFlag[0] = false;//修改标记位
                    new Thread() {  //菜单栏开启窗口动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS=255;  //透明度
                            while (MAXTRANS >= 0) {
                                Thread.sleep(6);
                                menuHomeUser.setColor(new Color(239, 238, 238,MAXTRANS));
                                menuHomeUser.repaint();
                                MAXTRANS -= 5;
                            }
                            canDo[0] = true;
                        }
                    }.start();
                    openMenuIndex = -1;
                    new Thread() {  //菜单栏开启窗口动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS=1;  //透明度
                            while (MAXTRANS <= 255) {
                                Thread.sleep(6);
                                home.setColor(new Color(166, 163, 163,MAXTRANS));
                                game1Back.add(game1Top);
                                game2Back.add(game2Top);
                                game3Back.add(game3Top);
                                game1.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                                game2.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                                game3.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                                home.repaint();
                                MAXTRANS += 12;
                            }
                            canDo[1] = true;
                        }
                    }.start();
                }

            }

        };
        iconLabel.addMouseListener(menuOpen);
        iconLabel.setBounds(-20, -10, icon.getWidth(), icon.getHeight());
        iconLabel.setIcon(new ImageIcon(transparencyIcon));  //设置用户自己的头像

    }

    public static void main(String[] args) {
        new Home();
    }

    /**
     * 最小化动画
     */
    @Override
    public void minimize() {
        //透明开启效果
        new Thread() {  //开启窗口动画
            @SneakyThrows
            @Override
            public void run() {
                float MAXTRANS=1;  //透明度
                while (MAXTRANS >= 0) {
                    Thread.sleep(4);
                    AWTUtilities.setWindowOpacity(back, MAXTRANS);  //半透明
                    MAXTRANS -= 0.03;
                }
                AWTUtilities.setWindowOpacity(back, 1);  //半透明
            }
        }.start();
    }

    /**
     * 最大化动画
     */
    @Override
    public void maximize() {
//透明开启效果
        new Thread() {  //开启窗口动画
            @SneakyThrows
            @Override
            public void run() {
                float MAXTRANS=0;  //透明度
                while (MAXTRANS <= 1.0) {
                    if (iconified) {
                        AWTUtilities.setWindowOpacity(back, 0);  //半透明
                        return;
                    }
                    Thread.sleep(4);
                    if (iconified) {
                        AWTUtilities.setWindowOpacity(back, 0);  //半透明
                        return;
                    }
                    AWTUtilities.setWindowOpacity(back, MAXTRANS);  //半透明
                    MAXTRANS += 0.03;
                    if (iconified) {
                        AWTUtilities.setWindowOpacity(back, 0);  //半透明
                        return;
                    }
                }
                iconified = false;
                AWTUtilities.setWindowOpacity(back, 1);  //半透明
            }
        }.start();
    }
}
