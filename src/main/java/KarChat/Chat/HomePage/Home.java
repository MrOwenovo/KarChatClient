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
import KarChat.Game.Panel.MainPanel;
import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
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

    public static JLabel menu;
    public static ImageIcon menuIcon;
    public static JLabel iconLabel ; //头像标签
    public static BufferedImage icon;  //头像图片
    public static MouseAdapter menuOpen;  //打开菜单鼠标点击事件
    private final JLabel game1Back;
    private final JLabel game1Top;
    private final ImageIcon game1Icon;
    private final ImageIcon game2Icon;
    private final ImageIcon game2IconNew;
    private final ImageIcon game3Icon;
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
    public static JLabel menuHomeBack1;
    public static JLabel menuHomeBack2;
    public static JLabel menuHomeBack3;
    public static JLabel menuHomeBack4;
    public static JLabel menuHomeBack5;
    public static JLabel menuHomeBack6;
    public static Frameless back;
    public static RadioJLabelNew home;
    private ImageIcon game3IconOn;
    private final JLabel game3Back;
    private final JLabel game3Top;
    private ImageIcon game2IconOn;
    private final JLabel game2Back;
    private final JLabel game2Top;
    private ImageIcon game1IconOn;
    private ImageIcon newMenuIcon;
    public static JLabel menuBack=new JLabel();
    public static JLabel menuTop=new JLabel();
    public static boolean[] menuFlag = {false}; //是否打开菜单栏
    public static boolean isMenuChild = false;  //判断是否是menu中的组件，防止一直弹




    @SneakyThrows
    public Home() {
        System.setProperty("sun.java2d.noddraw", "true");  //防止输入法输入时白屏，禁用DirectDraw
        back = new Frameless(1300,843,false);
        back.setUndecorated(true);  //不要边框
        back.setIconImage(ImageIO.read(Resources.getResourceAsStream("login/sign.png")));

        JLabel game1;  //三个游戏标签
        JLabel game2;  //三个游戏标签
        JLabel game3;  //三个游戏标签
        {  //初始化三个标签
            game1Icon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/game2.png")));
            game1 = new JLabel(game1Icon);
            game2Icon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/game7.png")));
            game2 = new JLabel(game2Icon);
            game3Icon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/game6.png")));
            game3 = new JLabel(game3Icon);
        }


        home = new RadioJLabelNew("");
        menuHomeBack = new JLabel();   //创建菜单背景
        menuHomeBack1 = new JLabel();
        menuHomeBack2 = new JLabel();
        menuHomeBack3 = new JLabel();
        menuHomeBack4 = new JLabel();
        menuHomeBack5 = new JLabel();
        menuHomeBack6 = new JLabel();
        menuHomeUser = new RadioJLabelNew("");   //创建菜单内容
        menuHomeUser1 = new RadioJLabelNew("");
        menuHomeUser2 = new RadioJLabelNew("");
        menuHomeUser3 = new RadioJLabelNew("");
        menuHomeUser4 = new RadioJLabelNew("");
        menuHomeUser5 = new RadioJLabelNew("");
        menuHomeUser6 = new RadioJLabelNew("");
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

        home.setArc(30,30);
        menuHomeUser.setArc(30,30);
        new Thread() {  //开启窗口动画
            @SneakyThrows
            @Override
            public void run() {
                int MAXTRANS=1;  //透明度
                while (MAXTRANS <= 255) {
                    Thread.sleep(6);
                    home.setColor(new Color(239, 238, 238,MAXTRANS));
                    home.repaint();
                    MAXTRANS += 3;
                }
            }
        }.start();

        {  //菜单加入图片
            menuIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/menubar2.png")));
            menu = new JLabel(menuIcon);
            //初始化菜单
            Menu.init();

        }
        {  //加入头像
            EchoClient.getIcon = true;  //修改标志位
        }
        {  //三个游戏标签加入图像和动效
            final int[] GAME = {1};  //默认从第一个开始
            final boolean[] keepFlag3 = {true};
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
                                                                WIDTH -= 9;
                                                                GAME3WIDTH -= 9;
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
                                                                WIDTH -= 9;
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

                                        Thread.sleep(350);
                                        game1Back.setBounds(340, 100, game1IconOn.getIconWidth(), game1IconOn.getIconHeight());

                                        while (WIDTH < -48) {
                                            Thread.sleep(1);
                                            game1Top.setBounds(WIDTH, 3, game1IconOn.getIconWidth(), game1Icon.getIconHeight());
                                            //移动game2,game3
                                            game2.setBounds(GAME2WIDTH, 100, game2Icon.getIconWidth(), game2IconOn.getIconHeight());
                                            game3.setBounds(GAME3WIDTH, 100, game3Icon.getIconWidth(), game3IconOn.getIconHeight());

                                            WIDTH += 9;
                                            GAME2WIDTH += 9;
                                            GAME3WIDTH += 9;
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

                                                                WIDTH -= 9;
                                                                GAME2WIDTH -= 9;
                                                                GAME3WIDTH -= 9;
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
                                                                WIDTH -= 9;
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

                                        Thread.sleep(350);
                                        game2Back.setBounds(480, 100, game2IconOn.getIconWidth(), game2IconOn.getIconHeight());

                                        while (WIDTH < -48) {
                                            Thread.sleep(1);
                                            game2Top.setBounds(WIDTH, 4, game2IconOn.getIconWidth(), game2Icon.getIconHeight());
                                            game2.setIcon(game2IconNew);
                                            game3.setBounds(GAME3WIDTH, 100, game3Icon.getIconWidth(), game3IconOn.getIconHeight());

                                            WIDTH += 9;
                                            GAME3WIDTH += 9;
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

                                                                WIDTH -= 9;
                                                                GAME2WIDTH -= 9;
                                                                GAME3WIDTH -= 9;
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
                                                                WIDTH -= 11;
                                                                GAME3WIDTH -= 11;
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
                                        Thread.sleep(350);

                                        game3Back.setBounds(620, 100, game3IconOn.getIconWidth(), game3IconOn.getIconHeight());
                                        while (WIDTH < -48) {
                                            Thread.sleep(1);
                                            game3Top.setBounds(WIDTH, 4, game3IconOn.getIconWidth(), game3Icon.getIconHeight());
                                            game3.setIcon(game3IconNew);
                                            WIDTH += 11;
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


        back.add(menu);  //加入菜单
        back.add(menuTop);  //加入背景
        menuBack.add(menuTop);
        game1Back.add(game1Top);
        game2Back.add(game2Top);
        game3Back.add(game3Top);
        back.add(menuBack);  //加入背景
        back.add(menuHomeBack);  //点击菜单展开的内容背景
        menuHomeBack.add(menuHomeUser);   //点击菜单展开的内容
        back.add(menuHomeBack1);  //点击菜单展开的内容背景
        menuHomeBack.add(menuHomeUser1);   //点击菜单展开的内容
        back.add(menuHomeBack2);  //点击菜单展开的内容背景
        menuHomeBack.add(menuHomeUser2);   //点击菜单展开的内容
        back.add(menuHomeBack3);  //点击菜单展开的内容背景
        menuHomeBack.add(menuHomeUser3);   //点击菜单展开的内容
        back.add(menuHomeBack4);  //点击菜单展开的内容背景
        menuHomeBack.add(menuHomeUser4);   //点击菜单展开的内容
        back.add(menuHomeBack5);  //点击菜单展开的内容背景
        menuHomeBack.add(menuHomeUser5);   //点击菜单展开的内容
        back.add(menuHomeBack6);  //点击菜单展开的内容背景
        menuHomeBack.add(menuHomeUser6);   //点击菜单展开的内容

        back.add(game1);  //加入背景
        back.add(game1Back);  //加入背景
        back.add(game2);  //加入背景
        back.add(game2Back);  //加入背景
        back.add(game3);  //加入背景
        back.add(game3Back);  //加入背景
        home.add(Rbut1);  //加入右上角按钮
        home.add(Rbut2);
        back.add(home);  //加入主页面



        home.setBounds(0,50,1300,743);
        menu.setBounds(50, 20, menuIcon.getIconWidth(), menuIcon.getIconHeight());
        game1.setBounds(290, 100, game1Icon.getIconWidth(), game1IconOn.getIconHeight());
        Rbut1.setBounds(home.getWidth()-90, 20, 25, 25);  //设置小按钮位置
        Rbut2.setBounds(home.getWidth()-50, 20, 25, 25);



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
            @Override
            public void mouseEntered(MouseEvent e) {
                isMenuChild = true;  //是子组件
            }
        });
        menuOpen = new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!menuFlag[0]) {  //展开
                    menuFlag[0] = true;//修改标记位
                    menuHomeBack.setBounds(115, 10, menuIcon.getIconWidth()+600, menuIcon.getIconHeight()+20);
                    menuHomeUser.setBounds(124, 8, menuIcon.getIconWidth()+200, menuIcon.getIconHeight());
                    new Thread() {  //菜单栏开启窗口动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS=1;  //透明度
                            while (MAXTRANS <= 255) {
                                Thread.sleep(6);
                                menuHomeUser.setColor(new Color(239, 238, 238,MAXTRANS));
                                menuHomeUser.repaint();
                                MAXTRANS += 4;
                            }
                        }
                    }.start();
                    new Thread() {  //菜单栏开启窗口动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS=255;  //透明度
                            while (MAXTRANS >= 0) {
                                Thread.sleep(6);
                                home.setColor(new Color(239, 238, 238,MAXTRANS));
                                home.repaint();
                                MAXTRANS -= 3;
                            }
                        }
                    }.start();

                }else if (menuFlag[0]) {
                    menuFlag[0] = false;//修改标记位
                    new Thread() {  //菜单栏开启窗口动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS=255;  //透明度
                            while (MAXTRANS >= 100) {
                                Thread.sleep(6);
                                menuHomeUser.setColor(new Color(239, 238, 238,MAXTRANS));
                                menuHomeUser.repaint();
                                MAXTRANS -= 4;
                            }
                        }
                    }.start();
                    new Thread() {  //菜单栏开启窗口动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS=1;  //透明度
                            while (MAXTRANS <= 255) {
                                Thread.sleep(6);
                                home.setColor(new Color(239, 238, 238,MAXTRANS));
                                home.repaint();
                                MAXTRANS += 3;
                            }
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

}
