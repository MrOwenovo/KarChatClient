package KarChat.Chat.HomePage;

import KarChat.Chat.Login.RadioJLabel;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static KarChat.Chat.HomePage.Home.*;

/**
 * 给menu添加组件
 */
public class Menu {

    private static ImageIcon newMenuIcon;
    static int homeColor1=166;  //主页颜色
    static int homeColor2=163;  //主页颜色
    static int homeColor3=163;  //主页颜色
    static int homeColorBack1=239;  //主页背景颜色
    static int homeColorBack2=238;  //主页背景颜色
    static int homeColorBack3=238;  //主页背景颜色
    private static boolean[] canDo;
    private static boolean[] isIn;
    private static boolean[] isClick1_1;
    private static boolean[] canDo2;
    private static boolean[] isIn2;
    private static boolean[] isClick2_1;
    private static boolean[] canDo3;
    private static boolean[] isIn3;
    private static boolean[] isClick3_1;
    private static boolean[] canDo4;
    private static boolean[] isIn4;
    private static boolean[] isClick4_1;
    private static boolean[] canDo5;
    private static boolean[] isIn5;
    private static boolean[] isClick5_1;
    private static boolean[] canDo6;
    private static boolean[] isIn6;
    private static boolean[] isClick6_1;
    private static RadioJLabel addFriendBackLabel;
    private static RadioJLabel addFriendBackRightLabel;
    private static RadioJLabel chatFriendBackLabel;
    private static RadioJLabel chatFriendBackRightLabel;
    private static RadioJLabel colorBackLabel;
    private static RadioJLabel colorBackRightLabel;
    private static RadioJLabel officialBackLabel;
    private static RadioJLabel officialBackRightLabel;
    private static RadioJLabel marketBackLabel;
    private static RadioJLabel marketBackRightLabel;
    private static RadioJLabel settingBackLabel;
    private static RadioJLabel settingBackRightLabel;

    @SneakyThrows
    public static void init() {
        newMenuIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/menubar3.png")));
        final boolean[] keepFlag = {true};
        final boolean[] isOpen = {false};  //判断是否打开
        final int[] WIDTHNOW = {0};  //加入状态判断，防止来回试探卡bug
        final boolean[] isInAll = {false}; //判断是否在菜单里
        //菜单展开
        MouseAdapter mouseAd = new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseEntered(MouseEvent e) {
                isInAll[0] = true;
                if (!isMenuChild) {
                    isMenuChild = false;
                    newMenuIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/menubar3.png")));
                    new Thread() {  //菜单展开
                        @SneakyThrows
                        @Override
                        public void run() {
                            int WIDTH;
                            int MENUWIDTH = 180 - (newMenuIcon.getIconWidth() - menuIcon.getIconWidth());
                            if (WIDTHNOW[0] == 0) {  //加入判断
                                WIDTH = -5-(newMenuIcon.getIconWidth() - menuIcon.getIconWidth());
                            } else {
                                WIDTH = WIDTHNOW[0];
                            }
                            keepFlag[0] = true;
                            menuTop.setIcon(newMenuIcon);
                            menuBack.setBounds(110, 20, newMenuIcon.getIconWidth(), newMenuIcon.getIconHeight());
                            label:
                            {
                                while (WIDTH < -40) {
                                    Thread.sleep(1);
                                    menuTop.setBounds(WIDTH, 0, newMenuIcon.getIconWidth(), menuIcon.getIconHeight());
                                    WIDTH += 3;
//                                    if (!keepFlag[0]) {
//                                        WIDTHNOW[0] = WIDTH;
//                                        keepFlag[0] = false;
//                                        break label;
//                                    }
                                }
                                isOpen[0] = true;
                                WIDTHNOW[0] = 0;  //清零
                            }
//
                        }
                    }.start();
                }
            }


        };
        menu.addMouseListener(mouseAd);

        //菜单收缩
        menuBack.addMouseListener(new MouseAdapter() {  //菜单收缩
            @Override
            public void mouseExited(MouseEvent e) {
                isInAll[0] = false;
                isMenuChild = false;
                new Thread(){
                    @SneakyThrows
                    @Override
                    public void run() {
                        int WIDTH = WIDTHNOW[0];
                        int MENUWIDTH = 140;
                        keepFlag[0] = false;
                        menuBack.setBounds(110, 20, newMenuIcon.getIconWidth(), newMenuIcon.getIconHeight());
                        label2:
                        {
                            while (WIDTH > -20-180-(newMenuIcon.getIconWidth() - menuIcon.getIconWidth())) {
                                Thread.sleep(1);
                                menuTop.setBounds(WIDTH, 0, newMenuIcon.getIconWidth(), menuIcon.getIconHeight());
                                if (menuFlag[0]&& MENUWIDTH>12)
                                    menuHomeUser.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth()+200, menuIcon.getIconHeight());
                                if (menuFlag1[0]&& MENUWIDTH>12)
                                    menuHomeUser1.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth()+200, menuIcon.getIconHeight());
                                if (menuFlag2[0]&& MENUWIDTH>12)
                                    menuHomeUser2.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth()+200, menuIcon.getIconHeight());
                                if (menuFlag3[0]&& MENUWIDTH>12)
                                    menuHomeUser3.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth()+200, menuIcon.getIconHeight());
                                if (menuFlag4[0]&& MENUWIDTH>12)
                                    menuHomeUser4.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth()+200, menuIcon.getIconHeight());
                                if (menuFlag5[0]&& MENUWIDTH>12)
                                    menuHomeUser5.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth()+200, menuIcon.getIconHeight());
                                if (menuFlag6[0]&& MENUWIDTH>12)
                                    menuHomeUser6.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth()+200, menuIcon.getIconHeight());

                                WIDTH -= 3;
                                if (menuFlag[0]||menuFlag1[0]||menuFlag2[0]||menuFlag3[0]||menuFlag4[0]||menuFlag5[0]||menuFlag6[0])
                                    MENUWIDTH -= 3;

                                if (keepFlag[0]) {
                                    WIDTHNOW[0] = WIDTH;
                                    break label2;
                                }
                            }
                            isOpen[0] = false;
                            WIDTHNOW[0] = -(newMenuIcon.getIconWidth() - menuIcon.getIconWidth());  //清零
                        }
                    }
                }.start();
            }
        });

        //加好友
        ImageIcon addFriendIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/addFriend.png")));
        JLabel addFriendLabel = new JLabel(addFriendIcon);  //加好友标签
        menu.add(addFriendLabel);
        addFriendLabel.setBounds(11, 160, addFriendIcon.getIconWidth(), addFriendIcon.getIconHeight());

        //好友选中背景左部分
        //加好友标签
        addFriendBackLabel = new RadioJLabel("");
        addFriendBackLabel.setColor(new Color(45,101,154,0));
        menu.add(addFriendBackLabel);  //选中背景
        addFriendBackLabel.setBounds(0, 150, addFriendIcon.getIconWidth()+32, addFriendIcon.getIconHeight()+20);

        //好友选中背景右部分
        //加好友标签
        addFriendBackRightLabel = new RadioJLabel("");
        addFriendBackRightLabel.setColor(new Color(45,101,154,0));
        menuTop.add(addFriendBackRightLabel);  //选中背景
        addFriendBackRightLabel.setBounds(0, 150, addFriendIcon.getIconWidth()+140, addFriendIcon.getIconHeight()+20);

        //判断能否加载动画，防止多线程闪屏bug
        canDo = new boolean[]{true};
        //判断是否在组件里
        isIn = new boolean[]{true};
        //判断当前组件是否被选定
        isClick1_1 = new boolean[]{false};
        menuTop.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isMenuChild = true;  //是子组件
            }
        });
        addFriendBackRightLabel.addMouseListener(new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseEntered(MouseEvent e) {
                enterBackAction(isIn, canDo, isClick1_1, addFriendBackLabel, addFriendBackRightLabel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitBackAction(isIn, canDo, isClick1_1, addFriendBackLabel, addFriendBackRightLabel);

            }
        });
        addFriendLabel.addMouseListener(new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseEntered(MouseEvent e) {
//                enterAction(isOpen,WIDTHNOW,keepFlag,isIn, CanDo, isClick1_1, addFriendBackLabel, addFriendBackRightLabel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                exitAction(isIn, CanDo, isClick1_1, addFriendBackLabel, addFriendBackRightLabel);
            }
        });

        //添加好友展开内容
        final boolean[] canDo1_1 = {true,true};  //判断内容页是否执行完
        menuOpen = new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                //固定背景标签，加入紫色提示
                isClick1_1[0] = !isClick1_1[0];  //点击状态相反

                openContent(canDo1_1,1);
            }

        };
        addFriendLabel.addMouseListener(menuOpen);
        addFriendBackRightLabel.addMouseListener(menuOpen);

        //好友聊天
        ImageIcon chatIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/peoples.png")));
        JLabel chatLabel = new JLabel(chatIcon);  //加好友标签
        menu.add(chatLabel);
        int oriY = addFriendLabel.getY() + addFriendIcon.getIconHeight() + 20;
        chatLabel.setBounds(11, oriY, chatIcon.getIconWidth(), chatIcon.getIconHeight());

        //好友选中背景左部分
        //聊天标签
        chatFriendBackLabel = new RadioJLabel("");
        chatFriendBackLabel.setColor(new Color(45,101,154,0));
        menu.add(chatFriendBackLabel);  //选中背景
        chatFriendBackLabel.setBounds(0, oriY-10, addFriendIcon.getIconWidth()+32, addFriendIcon.getIconHeight()+20);

        //好友选中背景右部分
        //加好友标签
        chatFriendBackRightLabel = new RadioJLabel("");
        chatFriendBackRightLabel.setColor(new Color(45,101,154,0));
        menuTop.add(chatFriendBackRightLabel);  //选中背景
        chatFriendBackRightLabel.setBounds(0, oriY-10, addFriendIcon.getIconWidth()+140, addFriendIcon.getIconHeight()+20);

        //判断能否加载动画，防止多线程闪屏bug
        canDo2 = new boolean[]{true};
        //判断是否在组件里
        isIn2 = new boolean[]{true};
        //判断当前组件是否被选定
        isClick2_1 = new boolean[]{false};
        chatFriendBackRightLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                enterBackAction(isIn2, canDo2, isClick2_1, chatFriendBackLabel, chatFriendBackRightLabel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitBackAction(isIn2, canDo2, isClick2_1, chatFriendBackLabel, chatFriendBackRightLabel);

            }
        });
        chatFriendBackLabel.addMouseListener(new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseEntered(MouseEvent e) {
                enterAction(isOpen,WIDTHNOW,keepFlag, isIn2, canDo2, isClick2_1, chatFriendBackLabel, chatFriendBackRightLabel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitAction(isIn2, canDo2, isClick2_1, chatFriendBackLabel, chatFriendBackRightLabel);
            }
        });
        //聊天好友展开内容
        final boolean[] canDo2_1 = {true,true};

        menuOpen = new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                //固定背景标签，加入紫色提示
                isClick2_1[0] = !isClick2_1[0];  //点击状态相反

                openContent(canDo2_1,2);
            }

        };
        chatLabel.addMouseListener(menuOpen);
        chatFriendBackRightLabel.addMouseListener(menuOpen);

        //主页风格
        ImageIcon colorIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/platte.png")));
        JLabel colorLabel = new JLabel(colorIcon);  //改主题标签
        menu.add(colorLabel);
        int oriY2 = oriY + addFriendIcon.getIconHeight() + 20;
        colorLabel.setBounds(11, oriY2, chatIcon.getIconWidth(), chatIcon.getIconHeight());

        //选中背景左部分
        //聊天标签
        colorBackLabel = new RadioJLabel("");
        colorBackLabel.setColor(new Color(45,101,154,0));
        menu.add(colorBackLabel);  //选中背景
        colorBackLabel.setBounds(0, oriY2-10, addFriendIcon.getIconWidth()+32, addFriendIcon.getIconHeight()+20);

        //选中背景右部分
        //加好友标签
        colorBackRightLabel = new RadioJLabel("");
        colorBackRightLabel.setColor(new Color(45,101,154,0));
        menuTop.add(colorBackRightLabel);  //选中背景
        colorBackRightLabel.setBounds(0, oriY2-10, addFriendIcon.getIconWidth()+140, addFriendIcon.getIconHeight()+20);

        //判断能否加载动画，防止多线程闪屏bug
        canDo3 = new boolean[]{true};
        //判断是否在组件里
        isIn3 = new boolean[]{true};
        //判断当前组件是否被选定
        isClick3_1 = new boolean[]{false};
        colorBackRightLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                enterBackAction(isIn3, canDo3, isClick3_1, colorBackLabel, colorBackRightLabel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitBackAction(isIn3, canDo3, isClick3_1, colorBackLabel, colorBackRightLabel);
            }
        });
        colorBackLabel.addMouseListener(new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseEntered(MouseEvent e) {
                enterAction(isOpen,WIDTHNOW,keepFlag, isIn3, canDo3, isClick3_1, colorBackLabel, colorBackRightLabel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitAction(isIn3, canDo3, isClick3_1, colorBackLabel, colorBackRightLabel);
            }
        });
        //主题展开内容
        final boolean[] canDo3_1 = {true,true};

        menuOpen = new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                //固定背景标签，加入紫色提示
                isClick3_1[0] = !isClick3_1[0];  //点击状态相反

                openContent(canDo3_1,3);
            }

        };
        colorLabel.addMouseListener(menuOpen);
        colorBackRightLabel.addMouseListener(menuOpen);


        //网站
        ImageIcon officialIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/browser.png")));
        JLabel officialLabel = new JLabel(officialIcon);  //改主题标签
        menu.add(officialLabel);
        int oriY3 = oriY2 + addFriendIcon.getIconHeight() + 20;
        officialLabel.setBounds(11, oriY3, chatIcon.getIconWidth(), chatIcon.getIconHeight());

        //选中背景左部分
        //聊天标签
        officialBackLabel = new RadioJLabel("");
        officialBackLabel.setColor(new Color(45,101,154,0));
        menu.add(officialBackLabel);  //选中背景
        officialBackLabel.setBounds(0, oriY3-10, addFriendIcon.getIconWidth()+32, addFriendIcon.getIconHeight()+20);

        //选中背景右部分
        //加好友标签
        officialBackRightLabel = new RadioJLabel("");
        officialBackRightLabel.setColor(new Color(45,101,154,0));
        menuTop.add(officialBackRightLabel);  //选中背景
        officialBackRightLabel.setBounds(0, oriY3-10, addFriendIcon.getIconWidth()+140, addFriendIcon.getIconHeight()+20);

        //判断能否加载动画，防止多线程闪屏bug
        canDo4 = new boolean[]{true};
        //判断是否在组件里
        isIn4 = new boolean[]{true};
        //判断当前组件是否被选定
        isClick4_1 = new boolean[]{false};

        officialBackRightLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                enterBackAction(isIn4, canDo4, isClick4_1, officialBackLabel, officialBackRightLabel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitBackAction(isIn4, canDo4, isClick4_1, officialBackLabel, officialBackRightLabel);
            }
        });
        officialBackLabel.addMouseListener(new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseEntered(MouseEvent e) {
                enterAction(isOpen,WIDTHNOW,keepFlag, isIn4, canDo4, isClick4_1, officialBackLabel, officialBackRightLabel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitAction(isIn4, canDo4, isClick4_1, officialBackLabel, officialBackRightLabel);
            }
        });

        //主题展开内容
        final boolean[] canDo4_1 = {true,true};

        menuOpen = new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                //固定背景标签，加入紫色提示
                isClick4_1[0] = !isClick4_1[0];  //点击状态相反

                openContent(canDo4_1,4);
            }

        };
        officialLabel.addMouseListener(menuOpen);
        officialBackRightLabel.addMouseListener(menuOpen);


        //商城
        ImageIcon marketIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/market.png")));
        JLabel marketLabel = new JLabel(marketIcon);  //改主题标签
        menu.add(marketLabel);
        int oriY4 = oriY3 + addFriendIcon.getIconHeight() + 20;
        marketLabel.setBounds(11, oriY4, chatIcon.getIconWidth(), chatIcon.getIconHeight());

        //选中背景左部分
        //聊天标签
        marketBackLabel = new RadioJLabel("");
        marketBackLabel.setColor(new Color(45,101,154,0));
        menu.add(marketBackLabel);  //选中背景
        marketBackLabel.setBounds(0, oriY4-10, addFriendIcon.getIconWidth()+32, addFriendIcon.getIconHeight()+20);

        //选中背景右部分
        //加好友标签
        marketBackRightLabel = new RadioJLabel("");
        marketBackRightLabel.setColor(new Color(45,101,154,0));
        menuTop.add(marketBackRightLabel);  //选中背景
        marketBackRightLabel.setBounds(0, oriY4-10, addFriendIcon.getIconWidth()+140, addFriendIcon.getIconHeight()+20);

        //判断能否加载动画，防止多线程闪屏bug
        canDo5 = new boolean[]{true};
        //判断是否在组件里
        isIn5 = new boolean[]{true};
        //判断当前组件是否被选定
        isClick5_1 = new boolean[]{false};

        marketBackRightLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                enterBackAction(isIn5, canDo5, isClick5_1, marketBackLabel, marketBackRightLabel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitBackAction(isIn5, canDo5, isClick5_1, marketBackLabel, marketBackRightLabel);
            }
        });
        marketBackLabel.addMouseListener(new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseEntered(MouseEvent e) {
                enterAction(isOpen,WIDTHNOW,keepFlag, isIn5, canDo5, isClick5_1, marketBackLabel, marketBackRightLabel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitAction(isIn5, canDo5, isClick5_1, marketBackLabel, marketBackRightLabel);

            }
        });

        //商城展开内容
        final boolean[] canDo5_1 = {true,true};

        menuOpen = new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                //固定背景标签，加入紫色提示
                isClick5_1[0] = !isClick5_1[0];  //点击状态相反

                openContent(canDo5_1,5);
            }

        };
        marketLabel.addMouseListener(menuOpen);
        marketBackRightLabel.addMouseListener(menuOpen);



        //设置
        ImageIcon settingIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/setting.png")));
        JLabel settingLabel = new JLabel(settingIcon);  //改主题标签
        menu.add(settingLabel);
        int oriY5 = oriY4 + addFriendIcon.getIconHeight() + 180;
        settingLabel.setBounds(11, oriY5, chatIcon.getIconWidth(), chatIcon.getIconHeight());

        //选中背景左部分
        //聊天标签
        settingBackLabel = new RadioJLabel("");
        settingBackLabel.setColor(new Color(45,101,154,0));
        menu.add(settingBackLabel);  //选中背景
        settingBackLabel.setBounds(0, oriY5-10, addFriendIcon.getIconWidth()+32, addFriendIcon.getIconHeight()+20);

        //选中背景右部分
        //加好友标签
        settingBackRightLabel = new RadioJLabel("");
        settingBackRightLabel.setColor(new Color(45,101,154,0));
        menuTop.add(settingBackRightLabel);  //选中背景
        settingBackRightLabel.setBounds(0, oriY5-10, addFriendIcon.getIconWidth()+140, addFriendIcon.getIconHeight()+20);

        //判断能否加载动画，防止多线程闪屏bug
        canDo6 = new boolean[]{true};
        //判断是否在组件里
        isIn6 = new boolean[]{true};
        //判断当前组件是否被选定
        isClick6_1 = new boolean[]{false};

        settingBackRightLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                enterBackAction(isIn6, canDo6, isClick6_1, settingBackLabel, settingBackRightLabel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitBackAction(isIn6, canDo6, isClick6_1, settingBackLabel, settingBackRightLabel);
            }
        });
        settingBackLabel.addMouseListener(new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseEntered(MouseEvent e) {
                enterAction(isOpen,WIDTHNOW,keepFlag, isIn6, canDo6, isClick6_1, settingBackLabel, settingBackRightLabel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitAction(isIn6, canDo6, isClick6_1, settingBackLabel, settingBackRightLabel);
            }
        });

        //设置展开内容
        final boolean[] canDo6_1 = {true,true};

        menuOpen = new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                //固定背景标签，加入紫色提示
                isClick6_1[0] = !isClick6_1[0];  //点击状态相反

                openContent(canDo6_1,6);

            }

        };
        settingLabel.addMouseListener(menuOpen);
        settingBackRightLabel.addMouseListener(menuOpen);

    }

    /**
     * 进入菜单组件事件处理
     */
    @SneakyThrows
    private static void enterAction(boolean[] isOpen,int[] WIDTHNOW, boolean[] keepFlag, boolean[] isIn, boolean[] canDo, boolean[] isClick, RadioJLabel BackLabel, RadioJLabel BackRightLabel) {
        isIn[0] = true;
        if (isMenuChild&&!isOpen[0]) {
            newMenuIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/menubar3.png")));
            new Thread() {  //菜单展开
                @SneakyThrows
                @Override
                public void run() {
                    int WIDTH;
                    int MENUWIDTH = 180 - (newMenuIcon.getIconWidth() - menuIcon.getIconWidth());
                    if (WIDTHNOW[0] == 0) {  //加入判断
                        WIDTH = -5-(newMenuIcon.getIconWidth() - menuIcon.getIconWidth());
                    } else {
                        WIDTH = WIDTHNOW[0];
                    }
                    keepFlag[0] = true;
                    menuTop.setIcon(newMenuIcon);
                    menuBack.setBounds(110, 20, newMenuIcon.getIconWidth(), newMenuIcon.getIconHeight());
                    label:
                    {
                        int demo = 54;
                        isOpen[0] = true;
                        while (WIDTH < -40) {
                            Thread.sleep(1);
                            menuTop.setBounds(WIDTH, 0, newMenuIcon.getIconWidth(), menuIcon.getIconHeight());
                            menuHomeUser.setBounds(demo, 8, menuIcon.getIconWidth()+200, menuIcon.getIconHeight());
                            WIDTH += 3;
                            demo += 3;
                        }
                        WIDTHNOW[0] = 0;  //清零
                    }
//
                }
            }.start();
        }

        isMenuChild = true;  //是子组件
        isIn[0] = true;
//        if (canDo[0]&&!isClick[0]) {
//            canDo[0] = false;
//            new Thread() {  //背景动画
//                @SneakyThrows
//                @Override
//                public void run() {
//                    int MAXTRANS = 1;  //透明度
//                    while (MAXTRANS <= 255) {
//                        Thread.sleep(6);
//                        BackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
//                        BackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
//                        BackLabel.repaint();
//                        BackRightLabel.repaint();
//                        MAXTRANS += 4;
//                    }
//                    canDo[0] = true;
//                    Thread.sleep(200);
//                    if (!isIn[0])
//                        otherOutCheck(isIn,canDo,BackLabel,BackRightLabel);
//                }
//            }.start();
//        }
    }

    /**
     * 进入菜单组件事件处理
     */
    private static void exitAction(boolean[] isIn, boolean[] canDo,boolean[] isClick, RadioJLabel BackLabel, RadioJLabel BackRightLabel) {
        isIn[0] = false;
//        if (canDo[0]&&!isClick[0]) {
//            canDo[0] = false;
//            new Thread() {  //背景动画
//                @SneakyThrows
//                @Override
//                public void run() {
//                    int MAXTRANS = 255;  //透明度
//                    while (MAXTRANS >= 0) {
//                        Thread.sleep(6);
//                        BackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
//                        BackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
//                        BackLabel.repaint();
//                        BackRightLabel.repaint();
//                        MAXTRANS -= 4;
//                    }
//                    canDo[0] = true;
//                    Thread.sleep(200);
//                    if (isIn[0])
//                        otherOutCheck(isIn,canDo,BackLabel,BackRightLabel);
//                }
//            }.start();
//        }
    }


        /**
         * 进入菜单背景组件事件处理
         */
    private static void enterBackAction(boolean[] isIn, boolean[] canDo,boolean[] isClick, RadioJLabel BackLabel, RadioJLabel BackRightLabel) {
        isMenuChild = true;  //是子组件
        isIn[0] = true;
        if (canDo[0]&&(!isClick[0])) {
            canDo[0] = false;
            new Thread() {  //背景动画
                @SneakyThrows
                @Override
                public void run() {
                    int MAXTRANS = 1;  //透明度
                    while (MAXTRANS <= 255) {
                        Thread.sleep(6);
                        BackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                        BackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                        BackLabel.repaint();
                        BackRightLabel.repaint();
                        MAXTRANS += 4;
                    }
                    canDo[0] = true;
                    Thread.sleep(200);
                    if (!isIn[0])
                        otherInCheck(isIn,canDo,BackLabel,BackRightLabel);
                }
            }.start();
        }
    }

    /**
     * 进入菜单背景组件事件处理
     */
    private static void exitBackAction(boolean[] isIn, boolean[] canDo,boolean[] isClick, RadioJLabel BackLabel, RadioJLabel BackRightLabel) {
        isIn[0] = false;
        if (canDo[0]&&!isClick[0]) {
            canDo[0] = false;
            new Thread() {  //背景动画
                @SneakyThrows
                @Override
                public void run() {
                    int MAXTRANS = 255;  //透明度
                    while (MAXTRANS >= 0) {
                        Thread.sleep(6);
                        BackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                        BackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                        BackLabel.repaint();
                        BackRightLabel.repaint();
                        MAXTRANS -= 4;
                    }
                    canDo[0] = true;
                    Thread.sleep(200);
                    if (isIn[0])
                        otherOutCheck(isIn,canDo,BackLabel,BackRightLabel);
                }
            }.start();
        }
    }
        /**
         *  点击后检查是否卡了检测帧进入背景
         */
    private static void otherOutCheck(boolean[] isIn,boolean[] canDo,RadioJLabel BackLabel,RadioJLabel BackRightLabel) {
        isMenuChild = true;  //是子组件
        isIn[0] = true;
        if (canDo[0]) {
            canDo[0] = false;
            new Thread() {  //背景动画
                @SneakyThrows
                @Override
                public void run() {
                    int MAXTRANS = 1;  //透明度
                    while (MAXTRANS <= 255) {
                        Thread.sleep(6);
                        BackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                        BackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                        BackLabel.repaint();
                        BackRightLabel.repaint();
                        MAXTRANS += 4;
                    }
                    canDo[0] = true;
                    Thread.sleep(200);
                }
            }.start();
        }
    }
    /**
     *  点击后检查是否卡了检测帧进入背景
     */
    private static void otherInCheck(boolean[] isIn,boolean[] canDo,RadioJLabel BackLabel,RadioJLabel BackRightLabel) {
        isIn[0] = false;
        if (canDo[0]) {
            canDo[0] = false;
            new Thread() {  //背景动画
                @SneakyThrows
                @Override
                public void run() {
                    int MAXTRANS = 255;  //透明度
                    while (MAXTRANS >= 0) {
                        Thread.sleep(6);
                        BackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                        BackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                        BackLabel.repaint();
                        BackRightLabel.repaint();
                        MAXTRANS -= 4;
                    }
                    canDo[0] = true;
                    Thread.sleep(200);
                }
            }.start();
        }
    }

    /**
     * 判断是否未打开菜单，并且内容的两个页面是否执行完
     */
    private static void openContent(boolean[] canDo,int index)
    {

        switch (index) {
            case 1:
                //判断是否未打开菜单，并且内容的两个页面是否执行完
                if (!menuFlag1[0]&& canDo[0]&&canDo[1]) {  //展开
                    canDo[0] = false;
                    canDo[1] = false;
                    menuFlag1[0] = true;//修改标记位
                    menuHomeBack.setBounds(115, 10, menuIcon.getIconWidth()+600, menuIcon.getIconHeight()+20);
                    menuHomeUser1.setBounds(124, 8, menuIcon.getIconWidth()+200, menuIcon.getIconHeight());

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
                                menuHomeUser1.setColor(new Color(239, 238, 238,MAXTRANS));
                                menuHomeUser1.repaint();
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
                                home.setColor(new Color(homeColor1, homeColor2, homeColor3,MAXTRANS));
                                game1Back.remove(game1Top);
                                game2Back.remove(game2Top);
                                game3Back.remove(game3Top);
                                homeBack.setColor(new Color(homeColorBack1, homeColorBack2, homeColorBack3, MAXTRANS));
                                game1.setSize(0,0);
                                game2.setSize(0,0);
                                game3.setSize(0,0);
                                home.repaint();
                                MAXTRANS -= 7;
                            }
                            canDo[1] = true;
                        }
                    }.start();
                    openMenuIndex = 1;


                }else if (menuFlag1[0]&& canDo[0]&&canDo[1]) {
                    canDo[0] = false;
                    canDo[1] = false;
                    menuFlag1[0] = false;//修改标记位
                    new Thread() {  //菜单栏开启窗口动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS=255;  //透明度
                            while (MAXTRANS >= 0) {
                                Thread.sleep(6);
                                menuHomeUser1.setColor(new Color(239, 238, 238,MAXTRANS));
                                menuHomeUser1.repaint();
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
                                home.setColor(new Color(homeColor1, homeColor2, homeColor3,MAXTRANS));
                                game1Back.add(game1Top);
                                game2Back.add(game2Top);
                                game3Back.add(game3Top);
                                homeBack.setColor(new Color(homeColorBack1, homeColorBack2, homeColorBack3, MAXTRANS));
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
                break;
            case 2:
//判断是否未打开菜单，并且内容的两个页面是否执行完
                if (!menuFlag2[0]&& canDo[0]&&canDo[1]) {  //展开
                    canDo[0] = false;
                    canDo[1] = false;
                    menuFlag2[0] = true;//修改标记位
                    menuHomeBack.setBounds(115, 10, menuIcon.getIconWidth()+600, menuIcon.getIconHeight()+20);
                    menuHomeUser2.setBounds(124, 8, menuIcon.getIconWidth()+200, menuIcon.getIconHeight());

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
                                menuHomeUser2.setColor(new Color(239, 238, 238,MAXTRANS));
                                menuHomeUser2.repaint();
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
                                home.setColor(new Color(homeColor1, homeColor2, homeColor3,MAXTRANS));
                                game1Back.remove(game1Top);
                                game2Back.remove(game2Top);
                                game3Back.remove(game3Top);
                                homeBack.setColor(new Color(homeColorBack1, homeColorBack2, homeColorBack3, MAXTRANS));
                                game1.setSize(0,0);
                                game2.setSize(0,0);
                                game3.setSize(0,0);
                                home.repaint();
                                MAXTRANS -= 7;
                            }
                            canDo[1] = true;
                        }
                    }.start();
                    openMenuIndex = 2;


                }else if (menuFlag2[0]&& canDo[0]&&canDo[1]) {
                    canDo[0] = false;
                    canDo[1] = false;
                    menuFlag2[0] = false;//修改标记位
                    new Thread() {  //菜单栏开启窗口动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS=255;  //透明度
                            while (MAXTRANS >= 0) {
                                Thread.sleep(6);
                                menuHomeUser2.setColor(new Color(239, 238, 238,MAXTRANS));
                                menuHomeUser2.repaint();
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
                                home.setColor(new Color(homeColor1, homeColor2, homeColor3,MAXTRANS));
                                game1Back.add(game1Top);
                                game2Back.add(game2Top);
                                game3Back.add(game3Top);
                                homeBack.setColor(new Color(homeColorBack1, homeColorBack2, homeColorBack3, MAXTRANS));
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
                break;
            case 3:
//判断是否未打开菜单，并且内容的两个页面是否执行完
                if (!menuFlag3[0]&& canDo[0]&&canDo[1]) {  //展开
                    canDo[0] = false;
                    canDo[1] = false;
                    menuFlag3[0] = true;//修改标记位
                    menuHomeBack.setBounds(115, 10, menuIcon.getIconWidth()+600, menuIcon.getIconHeight()+20);
                    menuHomeUser3.setBounds(124, 8, menuIcon.getIconWidth()+200, menuIcon.getIconHeight());

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
                                menuHomeUser3.setColor(new Color(239, 238, 238,MAXTRANS));
                                menuHomeUser3.repaint();
                                MAXTRANS += 5;
                            }
                            canDo[0] = true;
                        }
                    }.start();
                    openMenuIndex = 3;


                    new Thread() {  //菜单栏开启窗口动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS=255;  //透明度
                            while (MAXTRANS >= 100) {
                                Thread.sleep(6);
                                home.setColor(new Color(homeColor1, homeColor2, homeColor3,MAXTRANS));
                                game1Back.remove(game1Top);
                                game2Back.remove(game2Top);
                                game3Back.remove(game3Top);
                                homeBack.setColor(new Color(homeColorBack1, homeColorBack2, homeColorBack3, MAXTRANS));
                                game1.setSize(0,0);
                                game2.setSize(0,0);
                                game3.setSize(0,0);
                                home.repaint();
                                MAXTRANS -= 7;
                            }
                            canDo[1] = true;
                        }
                    }.start();

                }else if (menuFlag3[0]&& canDo[0]&&canDo[1]) {
                    canDo[0] = false;
                    canDo[1] = false;
                    menuFlag3[0] = false;//修改标记位
                    new Thread() {  //菜单栏开启窗口动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS=255;  //透明度
                            while (MAXTRANS >= 0) {
                                Thread.sleep(6);
                                menuHomeUser3.setColor(new Color(239, 238, 238,MAXTRANS));
                                menuHomeUser3.repaint();
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
                                home.setColor(new Color(homeColor1, homeColor2, homeColor3,MAXTRANS));
                                game1Back.add(game1Top);
                                game2Back.add(game2Top);
                                game3Back.add(game3Top);
                                homeBack.setColor(new Color(homeColorBack1, homeColorBack2, homeColorBack3, MAXTRANS));
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
                break;
            case 4:
//判断是否未打开菜单，并且内容的两个页面是否执行完
                if (!menuFlag4[0]&& canDo[0]&&canDo[1]) {  //展开
                    canDo[0] = false;
                    canDo[1] = false;
                    menuFlag4[0] = true;//修改标记位
                    menuHomeBack.setBounds(115, 10, menuIcon.getIconWidth()+600, menuIcon.getIconHeight()+20);
                    menuHomeUser4.setBounds(124, 8, menuIcon.getIconWidth()+200, menuIcon.getIconHeight());

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
                                menuHomeUser4.setColor(new Color(239, 238, 238,MAXTRANS));
                                menuHomeUser4.repaint();
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
                                home.setColor(new Color(homeColor1, homeColor2, homeColor3,MAXTRANS));
                                game1Back.remove(game1Top);
                                game2Back.remove(game2Top);
                                game3Back.remove(game3Top);
                                homeBack.setColor(new Color(homeColorBack1, homeColorBack2, homeColorBack3, MAXTRANS));
                                game1.setSize(0,0);
                                game2.setSize(0,0);
                                game3.setSize(0,0);
                                home.repaint();
                                MAXTRANS -= 7;
                            }
                            canDo[1] = true;
                        }
                    }.start();
                    openMenuIndex = 4;


                }else if (menuFlag4[0]&& canDo[0]&&canDo[1]) {
                    canDo[0] = false;
                    canDo[1] = false;
                    menuFlag4[0] = false;//修改标记位
                    new Thread() {  //菜单栏开启窗口动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS=255;  //透明度
                            while (MAXTRANS >= 0) {
                                Thread.sleep(6);
                                menuHomeUser4.setColor(new Color(239, 238, 238,MAXTRANS));
                                menuHomeUser4.repaint();
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
                                home.setColor(new Color(homeColor1, homeColor2, homeColor3,MAXTRANS));
                                game1Back.add(game1Top);
                                game2Back.add(game2Top);
                                game3Back.add(game3Top);
                                homeBack.setColor(new Color(homeColorBack1, homeColorBack2, homeColorBack3, MAXTRANS));
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
                break;
            case 5:
//判断是否未打开菜单，并且内容的两个页面是否执行完
                if (!menuFlag5[0]&& canDo[0]&&canDo[1]) {  //展开
                    canDo[0] = false;
                    canDo[1] = false;
                    menuFlag5[0] = true;//修改标记位
                    menuHomeBack.setBounds(115, 10, menuIcon.getIconWidth()+600, menuIcon.getIconHeight()+20);
                    menuHomeUser5.setBounds(124, 8, menuIcon.getIconWidth()+200, menuIcon.getIconHeight());

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
                                menuHomeUser5.setColor(new Color(239, 238, 238,MAXTRANS));
                                menuHomeUser5.repaint();
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
                                home.setColor(new Color(homeColor1, homeColor2, homeColor3,MAXTRANS));
                                game1Back.remove(game1Top);
                                game2Back.remove(game2Top);
                                game3Back.remove(game3Top);
                                homeBack.setColor(new Color(homeColorBack1, homeColorBack2, homeColorBack3, MAXTRANS));
                                game1.setSize(0,0);
                                game2.setSize(0,0);
                                game3.setSize(0,0);
                                home.repaint();
                                MAXTRANS -= 7;
                            }
                            canDo[1] = true;
                        }
                    }.start();
                    openMenuIndex = 5;


                }else if (menuFlag5[0]&& canDo[0]&&canDo[1]) {
                    canDo[0] = false;
                    canDo[1] = false;
                    menuFlag5[0] = false;//修改标记位
                    new Thread() {  //菜单栏开启窗口动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS=255;  //透明度
                            while (MAXTRANS >= 0) {
                                Thread.sleep(6);
                                menuHomeUser5.setColor(new Color(239, 238, 238,MAXTRANS));
                                menuHomeUser5.repaint();
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
                                home.setColor(new Color(homeColor1, homeColor2, homeColor3,MAXTRANS));
                                game1Back.add(game1Top);
                                game2Back.add(game2Top);
                                game3Back.add(game3Top);
                                homeBack.setColor(new Color(homeColorBack1, homeColorBack2, homeColorBack3, MAXTRANS));
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
                break;
            case 6:
//判断是否未打开菜单，并且内容的两个页面是否执行完
                if (!menuFlag6[0]&& canDo[0]&&canDo[1]) {  //展开
                    canDo[0] = false;
                    canDo[1] = false;
                    menuFlag6[0] = true;//修改标记位
                    menuHomeBack.setBounds(115, 10, menuIcon.getIconWidth()+600, menuIcon.getIconHeight()+20);
                    menuHomeUser6.setBounds(124, 8, menuIcon.getIconWidth()+200, menuIcon.getIconHeight());


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
                                menuHomeUser6.setColor(new Color(239, 238, 238,MAXTRANS));
                                menuHomeUser6.repaint();
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
                                home.setColor(new Color(homeColor1, homeColor2, homeColor3,MAXTRANS));
                                game1Back.remove(game1Top);
                                game2Back.remove(game2Top);
                                game3Back.remove(game3Top);
                                homeBack.setColor(new Color(homeColorBack1, homeColorBack2, homeColorBack3, MAXTRANS));
                                game1.setSize(0,0);
                                game2.setSize(0,0);
                                game3.setSize(0,0);
                                home.repaint();
                                MAXTRANS -= 7;
                            }
                            canDo[1] = true;
                        }
                    }.start();
                    openMenuIndex = 6;


                }else if (menuFlag6[0]&& canDo[0]&&canDo[1]) {
                    canDo[0] = false;
                    canDo[1] = false;
                    menuFlag6[0] = false;//修改标记位
                    new Thread() {  //菜单栏开启窗口动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS=255;  //透明度
                            while (MAXTRANS >= 0) {
                                Thread.sleep(6);
                                menuHomeUser6.setColor(new Color(239, 238, 238,MAXTRANS));
                                menuHomeUser6.repaint();
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
                                home.setColor(new Color(homeColor1, homeColor2, homeColor3,MAXTRANS));
                                game1Back.add(game1Top);
                                game2Back.add(game2Top);
                                game3Back.add(game3Top);
                                homeBack.setColor(new Color(homeColorBack1, homeColorBack2, homeColorBack3, MAXTRANS));
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
                break;
        }

    }

    /**
     * 收回上一次展开的内容，并取消按钮选择
     * @param index 上一次是第几个窗口
     * @param canDo 是否能执行
     */
    public static void DealWithOldMenuCont(int index,boolean[] canDo) {
        switch (index) {
            case 0:
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
                break;
            case 1:
                exitBackAction(isIn, canDo, isClick1_1, addFriendBackLabel, addFriendBackRightLabel);
                canDo[0] = false;
                canDo[1] = false;
                menuFlag1[0] = false;//修改标记位
                new Thread() {  //菜单栏开启窗口动画
                    @SneakyThrows
                    @Override
                    public void run() {
                        int MAXTRANS=255;  //透明度
                        while (MAXTRANS >= 0) {
                            Thread.sleep(6);
                            menuHomeUser1.setColor(new Color(239, 238, 238,MAXTRANS));
                            menuHomeUser1.repaint();
                            MAXTRANS -= 5;
                        }
                        canDo[0] = true;
                    }
                }.start();

                new Thread() {  //菜单栏开启窗口动画
                    @SneakyThrows
                    @Override
                    public void run() {
                        int MAXTRANS=1;  //透明度
                        while (MAXTRANS <= 255) {
                            Thread.sleep(6);
                            home.setColor(new Color(homeColor1, homeColor2, homeColor3,MAXTRANS));
                            game1Back.add(game1Top);
                            game2Back.add(game2Top);
                            game3Back.add(game3Top);
                            homeBack.setColor(new Color(homeColorBack1, homeColorBack2, homeColorBack3, MAXTRANS));
                            game1.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                            game2.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                            game3.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                            home.repaint();
                            MAXTRANS += 12;
                        }
                        canDo[1] = true;
                    }
                }.start();
                break;

            case 2:
                exitBackAction(isIn2, canDo2, isClick2_1, chatFriendBackLabel, chatFriendBackRightLabel);
                canDo[0] = false;
                canDo[1] = false;
                menuFlag2[0] = false;//修改标记位
                new Thread() {  //菜单栏开启窗口动画
                    @SneakyThrows
                    @Override
                    public void run() {
                        int MAXTRANS=255;  //透明度
                        while (MAXTRANS >= 0) {
                            Thread.sleep(6);
                            menuHomeUser2.setColor(new Color(239, 238, 238,MAXTRANS));
                            menuHomeUser2.repaint();
                            MAXTRANS -= 5;
                        }
                        canDo[0] = true;
                    }
                }.start();

                new Thread() {  //菜单栏开启窗口动画
                    @SneakyThrows
                    @Override
                    public void run() {
                        int MAXTRANS=1;  //透明度
                        while (MAXTRANS <= 255) {
                            Thread.sleep(6);
                            home.setColor(new Color(homeColor1, homeColor2, homeColor3,MAXTRANS));
                            game1Back.add(game1Top);
                            game2Back.add(game2Top);
                            game3Back.add(game3Top);
                            homeBack.setColor(new Color(homeColorBack1, homeColorBack2, homeColorBack3, MAXTRANS));
                            game1.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                            game2.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                            game3.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                            home.repaint();
                            MAXTRANS += 12;
                        }
                        canDo[1] = true;
                    }
                }.start();
                break;
            case 3:
                canDo[0] = false;
                canDo[1] = false;
                menuFlag3[0] = false;//修改标记位
                new Thread() {  //菜单栏开启窗口动画
                    @SneakyThrows
                    @Override
                    public void run() {
                        int MAXTRANS=255;  //透明度
                        while (MAXTRANS >= 0) {
                            Thread.sleep(6);
                            menuHomeUser3.setColor(new Color(239, 238, 238,MAXTRANS));
                            menuHomeUser3.repaint();
                            MAXTRANS -= 5;
                        }
                        canDo[0] = true;
                    }
                }.start();

                new Thread() {  //菜单栏开启窗口动画
                    @SneakyThrows
                    @Override
                    public void run() {
                        int MAXTRANS=1;  //透明度
                        while (MAXTRANS <= 255) {
                            Thread.sleep(6);
                            home.setColor(new Color(homeColor1, homeColor2, homeColor3,MAXTRANS));
                            game1Back.add(game1Top);
                            game2Back.add(game2Top);
                            game3Back.add(game3Top);
                            homeBack.setColor(new Color(homeColorBack1, homeColorBack2, homeColorBack3, MAXTRANS));
                            game1.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                            game2.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                            game3.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                            home.repaint();
                            MAXTRANS += 12;
                        }
                        canDo[1] = true;
                    }
                }.start();
                break;
            case 4:
                canDo[0] = false;
                canDo[1] = false;
                menuFlag4[0] = false;//修改标记位
                new Thread() {  //菜单栏开启窗口动画
                    @SneakyThrows
                    @Override
                    public void run() {
                        int MAXTRANS=255;  //透明度
                        while (MAXTRANS >= 0) {
                            Thread.sleep(6);
                            menuHomeUser4.setColor(new Color(239, 238, 238,MAXTRANS));
                            menuHomeUser4.repaint();
                            MAXTRANS -= 5;
                        }
                        canDo[0] = true;
                    }
                }.start();

                new Thread() {  //菜单栏开启窗口动画
                    @SneakyThrows
                    @Override
                    public void run() {
                        int MAXTRANS=1;  //透明度
                        while (MAXTRANS <= 255) {
                            Thread.sleep(6);
                            home.setColor(new Color(homeColor1, homeColor2, homeColor3,MAXTRANS));
                            game1Back.add(game1Top);
                            game2Back.add(game2Top);
                            game3Back.add(game3Top);
                            homeBack.setColor(new Color(homeColorBack1, homeColorBack2, homeColorBack3, MAXTRANS));
                            game1.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                            game2.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                            game3.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                            home.repaint();
                            MAXTRANS += 12;
                        }
                        canDo[1] = true;
                    }
                }.start();
                break;
            case 5:
                canDo[0] = false;
                canDo[1] = false;
                menuFlag5[0] = false;//修改标记位
                new Thread() {  //菜单栏开启窗口动画
                    @SneakyThrows
                    @Override
                    public void run() {
                        int MAXTRANS=255;  //透明度
                        while (MAXTRANS >= 0) {
                            Thread.sleep(6);
                            menuHomeUser5.setColor(new Color(239, 238, 238,MAXTRANS));
                            menuHomeUser5.repaint();
                            MAXTRANS -= 5;
                        }
                        canDo[0] = true;
                    }
                }.start();

                new Thread() {  //菜单栏开启窗口动画
                    @SneakyThrows
                    @Override
                    public void run() {
                        int MAXTRANS=1;  //透明度
                        while (MAXTRANS <= 255) {
                            Thread.sleep(6);
                            home.setColor(new Color(homeColor1, homeColor2, homeColor3,MAXTRANS));
                            game1Back.add(game1Top);
                            game2Back.add(game2Top);
                            game3Back.add(game3Top);
                            homeBack.setColor(new Color(homeColorBack1, homeColorBack2, homeColorBack3, MAXTRANS));
                            game1.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                            game2.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                            game3.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                            home.repaint();
                            MAXTRANS += 12;
                        }
                        canDo[1] = true;
                    }
                }.start();
                break;
            case 6:
                canDo[0] = false;
                canDo[1] = false;
                menuFlag6[0] = false;//修改标记位
                new Thread() {  //菜单栏开启窗口动画
                    @SneakyThrows
                    @Override
                    public void run() {
                        int MAXTRANS=255;  //透明度
                        while (MAXTRANS >= 0) {
                            Thread.sleep(6);
                            menuHomeUser6.setColor(new Color(239, 238, 238,MAXTRANS));
                            menuHomeUser6.repaint();
                            MAXTRANS -= 5;
                        }
                        canDo[0] = true;
                    }
                }.start();

                new Thread() {  //菜单栏开启窗口动画
                    @SneakyThrows
                    @Override
                    public void run() {
                        int MAXTRANS=1;  //透明度
                        while (MAXTRANS <= 255) {
                            Thread.sleep(6);
                            home.setColor(new Color(homeColor1, homeColor2, homeColor3,MAXTRANS));
                            game1Back.add(game1Top);
                            game2Back.add(game2Top);
                            game3Back.add(game3Top);
                            homeBack.setColor(new Color(homeColorBack1, homeColorBack2, homeColorBack3, MAXTRANS));
                            game1.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                            game2.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                            game3.setSize(game1Icon.getIconWidth(),game1Icon.getIconHeight());
                            home.repaint();
                            MAXTRANS += 12;
                        }
                        canDo[1] = true;
                    }
                }.start();
                break;

        }
    }
    }
