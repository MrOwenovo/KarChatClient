package KarChat.Chat.HomePage;

import KarChat.Chat.Login.RadioJLabel;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static KarChat.Chat.HomePage.Home.*;

/**
 * 给menu添加组件
 */
public class Menu {

    private static ImageIcon newMenuIcon;

    @SneakyThrows
    public static void init() {
        final boolean[] keepFlag = {true};
        final int[] WIDTHNOW = {0};  //加入状态判断，防止来回试探卡bug
        //菜单展开
        MouseAdapter mouseAd = new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseEntered(MouseEvent e) {
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
                                WIDTH = -(newMenuIcon.getIconWidth() - menuIcon.getIconWidth());
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
                                    if (!keepFlag[0]) {
                                        WIDTHNOW[0] = WIDTH;
                                        keepFlag[0] = false;
                                        break label;
                                    }
                                }
                                WIDTHNOW[0] = 0;  //清零
                            }
//                            if (WIDTH < 0) {
//                                mouseExited(e);
//                            }
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
                            while (WIDTH > -180-(newMenuIcon.getIconWidth() - menuIcon.getIconWidth())) {
                                Thread.sleep(1);
                                menuTop.setBounds(WIDTH, 0, newMenuIcon.getIconWidth(), menuIcon.getIconHeight());
                                if (menuFlag[0]&& MENUWIDTH>12)
                                    menuHomeUser1.setBounds(MENUWIDTH, 10, menuIcon.getIconWidth()+200, menuIcon.getIconHeight());
                                WIDTH -= 3;
                                if (menuFlag[0])
                                    MENUWIDTH -= 3;
                                if (keepFlag[0]) {
                                    WIDTHNOW[0] = WIDTH;
                                    break label2;
                                }
                            }
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
        RadioJLabel addFriendBackLabel = new RadioJLabel("");  //加好友标签
        addFriendBackLabel.setColor(new Color(45,101,154,0));
        menu.add(addFriendBackLabel);  //选中背景
        addFriendBackLabel.setBounds(0, 150, addFriendIcon.getIconWidth()+32, addFriendIcon.getIconHeight()+20);

        //好友选中背景右部分
        RadioJLabel addFriendBackRightLabel = new RadioJLabel("");  //加好友标签
        addFriendBackRightLabel.setColor(new Color(45,101,154,0));
        menuTop.add(addFriendBackRightLabel);  //选中背景
        addFriendBackRightLabel.setBounds(0, 150, addFriendIcon.getIconWidth()+144, addFriendIcon.getIconHeight()+20);

        final boolean[] canDo = {true}; //判断能否加载动画，防止多线程闪屏bug
        final boolean[] isIn = {true}; //判断是否在组件里
        menuTop.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isMenuChild = true;  //是子组件
//                    isIn[0] = true;
            }
        });
        addFriendBackRightLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
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
                                addFriendBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                addFriendBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                addFriendBackLabel.repaint();
                                addFriendBackRightLabel.repaint();
                                MAXTRANS += 4;
                            }
                            canDo[0] = true;
                            Thread.sleep(200);
                            if (!isIn[0])
                                mouseExited(e);
                        }
                    }.start();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
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
                                addFriendBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                addFriendBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                addFriendBackLabel.repaint();
                                addFriendBackRightLabel.repaint();
                                MAXTRANS -= 4;
                            }
                            canDo[0] = true;
                            Thread.sleep(200);
                            if (isIn[0])
                                mouseEntered(e);
                        }
                    }.start();
                }
            }
        });
        addFriendLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
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
                                addFriendBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                addFriendBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                addFriendBackLabel.repaint();
                                addFriendBackRightLabel.repaint();
                                MAXTRANS += 4;
                            }
                            canDo[0] = true;
                            Thread.sleep(200);
                            if (!isIn[0])
                                mouseExited(e);
                        }
                    }.start();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
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
                                addFriendBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                addFriendBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                addFriendBackLabel.repaint();
                                addFriendBackRightLabel.repaint();
                                MAXTRANS -= 4;
                            }
                            canDo[0] = true;
                            Thread.sleep(200);
                            if (isIn[0])
                                mouseEntered(e);
                        }
                    }.start();
                }
            }
        });
//        addFriendLabel.addMouseListener(new MouseAdapter() {
//            @SneakyThrows
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (!menuFlag[0]) {  //展开
//                    menuFlag[0] = true;//修改标记位
//                    menuHomeBack1.setBounds(115, 10, menuIcon.getIconWidth() + 600, menuIcon.getIconHeight() + 20);
//                    menuHomeUser1.setBounds(124, 8, menuIcon.getIconWidth() + 200, menuIcon.getIconHeight());
//                    new Thread() {  //菜单栏开启窗口动画
//                        @SneakyThrows
//                        @Override
//                        public void run() {
//                            int MAXTRANS = 1;  //透明度
//                            while (MAXTRANS <= 255) {
//                                Thread.sleep(6);
//                                menuHomeUser1.setColor(new Color(239, 238, 238, MAXTRANS));
//                                menuHomeUser1.repaint();
//                                MAXTRANS += 4;
//                            }
//                        }
//                    }.start();
//                    new Thread() {  //菜单栏开启窗口动画
//                        @SneakyThrows
//                        @Override
//                        public void run() {
//                            int MAXTRANS = 255;  //透明度
//                            while (MAXTRANS >= 0) {
//                                Thread.sleep(6);
//                                home.setColor(new Color(239, 238, 238, MAXTRANS));
//                                home.repaint();
//                                MAXTRANS -= 3;
//                            }
//                        }
//                    }.start();
//
//                } else if (menuFlag[0]) {
//                    menuFlag[0] = false;//修改标记位
//                    new Thread() {  //菜单栏开启窗口动画
//                        @SneakyThrows
//                        @Override
//                        public void run() {
//                            int MAXTRANS = 255;  //透明度
//                            while (MAXTRANS >= 100) {
//                                Thread.sleep(6);
//                                menuHomeUser1.setColor(new Color(239, 238, 238, MAXTRANS));
//                                menuHomeUser1.repaint();
//                                MAXTRANS -= 4;
//                            }
//                        }
//                    }.start();
//                    new Thread() {  //菜单栏开启窗口动画
//                        @SneakyThrows
//                        @Override
//                        public void run() {
//                            int MAXTRANS = 1;  //透明度
//                            while (MAXTRANS <= 255) {
//                                Thread.sleep(6);
//                                home.setColor(new Color(239, 238, 238, MAXTRANS));
//                                home.repaint();
//                                MAXTRANS += 3;
//                            }
//                        }
//                    }.start();
//                }
//
//            }
//
//        });

        //好友聊天
        ImageIcon chatIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/peoples.png")));
        JLabel chatLabel = new JLabel(chatIcon);  //加好友标签
        menu.add(chatLabel);
        int oriY = addFriendLabel.getY() + addFriendIcon.getIconHeight() + 20;
        chatLabel.setBounds(11, oriY, chatIcon.getIconWidth(), chatIcon.getIconHeight());

        //好友选中背景左部分
        RadioJLabel chatFriendBackLabel = new RadioJLabel("");  //聊天标签
        chatFriendBackLabel.setColor(new Color(45,101,154,0));
        menu.add(chatFriendBackLabel);  //选中背景
        chatFriendBackLabel.setBounds(0, oriY-10, addFriendIcon.getIconWidth()+32, addFriendIcon.getIconHeight()+20);

        //好友选中背景右部分
        RadioJLabel chatFriendBackRightLabel = new RadioJLabel("");  //加好友标签
        chatFriendBackRightLabel.setColor(new Color(45,101,154,0));
        menuTop.add(chatFriendBackRightLabel);  //选中背景
        chatFriendBackRightLabel.setBounds(0, oriY-10, addFriendIcon.getIconWidth()+144, addFriendIcon.getIconHeight()+20);

        final boolean[] canDo2 = {true}; //判断能否加载动画，防止多线程闪屏bug
        final boolean[] isIn2 = {true}; //判断是否在组件里
        chatFriendBackRightLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isMenuChild = true;  //是子组件
                isIn2[0] = true;
                if (canDo2[0]) {
                    canDo2[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 1;  //透明度
                            while (MAXTRANS <= 255) {
                                Thread.sleep(6);
                                chatFriendBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                chatFriendBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                chatFriendBackLabel.repaint();
                                chatFriendBackRightLabel.repaint();
                                MAXTRANS += 4;
                            }
                            canDo2[0] = true;
                            Thread.sleep(200);
                            if (!isIn2[0])
                                mouseExited(e);
                        }
                    }.start();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isIn2[0] = false;
                if (canDo2[0]) {
                    canDo2[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 255;  //透明度
                            while (MAXTRANS >= 0) {
                                Thread.sleep(6);
                                chatFriendBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                chatFriendBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                chatFriendBackLabel.repaint();
                                chatFriendBackRightLabel.repaint();
                                MAXTRANS -= 4;
                            }
                            canDo2[0] = true;
                            Thread.sleep(200);
                            if (isIn2[0])
                                mouseEntered(e);
                        }
                    }.start();
                }
            }
        });
        chatFriendBackLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isMenuChild = true;  //是子组件
                isIn2[0] = true;
                if (canDo2[0]) {
                    canDo2[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 1;  //透明度
                            while (MAXTRANS <= 255) {
                                Thread.sleep(6);
                                chatFriendBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                chatFriendBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                chatFriendBackLabel.repaint();
                                chatFriendBackRightLabel.repaint();
                                MAXTRANS += 4;
                            }
                            canDo2[0] = true;
                            Thread.sleep(200);
                            if (!isIn2[0])
                                mouseExited(e);
                        }
                    }.start();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isIn2[0] = false;
                if (canDo2[0]) {
                    canDo2[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 255;  //透明度
                            while (MAXTRANS >= 0) {
                                Thread.sleep(6);
                                chatFriendBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                chatFriendBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                chatFriendBackLabel.repaint();
                                chatFriendBackRightLabel.repaint();
                                MAXTRANS -= 4;
                            }
                            canDo2[0] = true;
                            Thread.sleep(200);
                            if (isIn2[0])
                                mouseEntered(e);
                        }
                    }.start();
                }
            }
        });

        //主页风格
        ImageIcon colorIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/platte.png")));
        JLabel colorLabel = new JLabel(colorIcon);  //改主题标签
        menu.add(colorLabel);
        int oriY2 = oriY + addFriendIcon.getIconHeight() + 20;
        colorLabel.setBounds(11, oriY2, chatIcon.getIconWidth(), chatIcon.getIconHeight());

        //选中背景左部分
        RadioJLabel colorBackLabel = new RadioJLabel("");  //聊天标签
        colorBackLabel.setColor(new Color(45,101,154,0));
        menu.add(colorBackLabel);  //选中背景
        colorBackLabel.setBounds(0, oriY2-10, addFriendIcon.getIconWidth()+32, addFriendIcon.getIconHeight()+20);

        //选中背景右部分
        RadioJLabel colorBackRightLabel = new RadioJLabel("");  //加好友标签
        colorBackRightLabel.setColor(new Color(45,101,154,0));
        menuTop.add(colorBackRightLabel);  //选中背景
        colorBackRightLabel.setBounds(0, oriY2-10, addFriendIcon.getIconWidth()+144, addFriendIcon.getIconHeight()+20);

        final boolean[] canDo3 = {true}; //判断能否加载动画，防止多线程闪屏bug
        final boolean[] isIn3 = {true}; //判断是否在组件里
        colorBackRightLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isMenuChild = true;  //是子组件
                isIn3[0] = true;
                if (canDo3[0]) {
                    canDo3[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 1;  //透明度
                            while (MAXTRANS <= 255) {
                                Thread.sleep(6);
                                colorBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                colorBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                colorBackLabel.repaint();
                                colorBackRightLabel.repaint();
                                MAXTRANS += 4;
                            }
                            canDo3[0] = true;
                            Thread.sleep(200);
                            if (!isIn3[0])
                                mouseExited(e);
                        }
                    }.start();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isIn3[0] = false;
                if (canDo3[0]) {
                    canDo3[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 255;  //透明度
                            while (MAXTRANS >= 0) {
                                Thread.sleep(6);
                                colorBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                colorBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                colorBackLabel.repaint();
                                colorBackRightLabel.repaint();
                                MAXTRANS -= 4;
                            }
                            canDo3[0] = true;
                            Thread.sleep(200);
                            if (isIn3[0])
                                mouseEntered(e);
                        }
                    }.start();
                }
            }
        });
        colorBackLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isMenuChild = true;  //是子组件
                isIn3[0] = true;
                if (canDo3[0]) {
                    canDo3[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 1;  //透明度
                            while (MAXTRANS <= 255) {
                                Thread.sleep(6);
                                colorBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                colorBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                colorBackLabel.repaint();
                                colorBackRightLabel.repaint();
                                MAXTRANS += 4;
                            }
                            canDo3[0] = true;
                            Thread.sleep(200);
                            if (!isIn3[0])
                                mouseExited(e);
                        }
                    }.start();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isIn3[0] = false;
                if (canDo3[0]) {
                    canDo3[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 255;  //透明度
                            while (MAXTRANS >= 0) {
                                Thread.sleep(6);
                                colorBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                colorBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                colorBackLabel.repaint();
                                colorBackRightLabel.repaint();
                                MAXTRANS -= 4;
                            }
                            canDo3[0] = true;
                            Thread.sleep(200);
                            if (isIn3[0])
                                mouseEntered(e);
                        }
                    }.start();
                }
            }
        });


        //网站
        ImageIcon officialIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/browser.png")));
        JLabel officialLabel = new JLabel(officialIcon);  //改主题标签
        menu.add(officialLabel);
        int oriY3 = oriY2 + addFriendIcon.getIconHeight() + 20;
        officialLabel.setBounds(11, oriY3, chatIcon.getIconWidth(), chatIcon.getIconHeight());

        //选中背景左部分
        RadioJLabel officialBackLabel = new RadioJLabel("");  //聊天标签
        officialBackLabel.setColor(new Color(45,101,154,0));
        menu.add(officialBackLabel);  //选中背景
        officialBackLabel.setBounds(0, oriY3-10, addFriendIcon.getIconWidth()+32, addFriendIcon.getIconHeight()+20);

        //选中背景右部分
        RadioJLabel officialBackRightLabel = new RadioJLabel("");  //加好友标签
        officialBackRightLabel.setColor(new Color(45,101,154,0));
        menuTop.add(officialBackRightLabel);  //选中背景
        officialBackRightLabel.setBounds(0, oriY3-10, addFriendIcon.getIconWidth()+144, addFriendIcon.getIconHeight()+20);

        final boolean[] canDo4 = {true}; //判断能否加载动画，防止多线程闪屏bug
        final boolean[] isIn4 = {true}; //判断是否在组件里
        officialBackRightLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isMenuChild = true;  //是子组件
                isIn4[0] = true;
                if (canDo4[0]) {
                    canDo4[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 1;  //透明度
                            while (MAXTRANS <= 255) {
                                Thread.sleep(6);
                                officialBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                officialBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                officialBackLabel.repaint();
                                officialBackRightLabel.repaint();
                                MAXTRANS += 4;
                            }
                            canDo4[0] = true;
                            Thread.sleep(200);
                            if (!isIn4[0])
                                mouseExited(e);
                        }
                    }.start();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isIn4[0] = false;
                if (canDo4[0]) {
                    canDo4[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 255;  //透明度
                            while (MAXTRANS >= 0) {
                                Thread.sleep(6);
                                officialBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                officialBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                officialBackLabel.repaint();
                                officialBackRightLabel.repaint();
                                MAXTRANS -= 4;
                            }
                            canDo4[0] = true;
                            Thread.sleep(200);
                            if (isIn4[0])
                                mouseEntered(e);
                        }
                    }.start();
                }
            }
        });
        officialBackLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isMenuChild = true;  //是子组件
                isIn4[0] = true;
                if (canDo4[0]) {
                    canDo4[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 1;  //透明度
                            while (MAXTRANS <= 255) {
                                Thread.sleep(6);
                                officialBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                officialBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                officialBackLabel.repaint();
                                officialBackRightLabel.repaint();
                                MAXTRANS += 4;
                            }
                            canDo4[0] = true;
                            Thread.sleep(200);
                            if (!isIn4[0])
                                mouseExited(e);
                        }
                    }.start();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isIn4[0] = false;
                if (canDo4[0]) {
                    canDo4[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 255;  //透明度
                            while (MAXTRANS >= 0) {
                                Thread.sleep(6);
                                officialBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                officialBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                officialBackLabel.repaint();
                                officialBackRightLabel.repaint();
                                MAXTRANS -= 4;
                            }
                            canDo4[0] = true;
                            Thread.sleep(200);
                            if (isIn4[0])
                                mouseEntered(e);
                        }
                    }.start();
                }
            }
        });


        //商城
        ImageIcon marketIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/market.png")));
        JLabel marketLabel = new JLabel(marketIcon);  //改主题标签
        menu.add(marketLabel);
        int oriY4 = oriY3 + addFriendIcon.getIconHeight() + 20;
        marketLabel.setBounds(11, oriY4, chatIcon.getIconWidth(), chatIcon.getIconHeight());

        //选中背景左部分
        RadioJLabel  marketBackLabel = new RadioJLabel("");  //聊天标签
        marketBackLabel.setColor(new Color(45,101,154,0));
        menu.add(marketBackLabel);  //选中背景
        marketBackLabel.setBounds(0, oriY4-10, addFriendIcon.getIconWidth()+32, addFriendIcon.getIconHeight()+20);

        //选中背景右部分
        RadioJLabel marketBackRightLabel = new RadioJLabel("");  //加好友标签
        marketBackRightLabel.setColor(new Color(45,101,154,0));
        menuTop.add(marketBackRightLabel);  //选中背景
        marketBackRightLabel.setBounds(0, oriY4-10, addFriendIcon.getIconWidth()+144, addFriendIcon.getIconHeight()+20);

        final boolean[] canDo5 = {true}; //判断能否加载动画，防止多线程闪屏bug
        final boolean[] isIn5 = {true}; //判断是否在组件里
        marketBackRightLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isMenuChild = true;  //是子组件
                isIn5[0] = true;
                if (canDo5[0]) {
                    canDo5[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 1;  //透明度
                            while (MAXTRANS <= 255) {
                                Thread.sleep(6);
                                marketBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                marketBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                marketBackLabel.repaint();
                                marketBackRightLabel.repaint();
                                MAXTRANS += 4;
                            }
                            canDo5[0] = true;
                            Thread.sleep(200);
                            if (!isIn5[0])
                                mouseExited(e);
                        }
                    }.start();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isIn5[0] = false;
                if (canDo5[0]) {
                    canDo5[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 255;  //透明度
                            while (MAXTRANS >= 0) {
                                Thread.sleep(6);
                                marketBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                marketBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                marketBackLabel.repaint();
                                marketBackRightLabel.repaint();
                                MAXTRANS -= 4;
                            }
                            canDo5[0] = true;
                            Thread.sleep(200);
                            if (isIn5[0])
                                mouseEntered(e);
                        }
                    }.start();
                }
            }
        });
        marketBackLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isMenuChild = true;  //是子组件
                isIn5[0] = true;
                if (canDo5[0]) {
                    canDo5[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 1;  //透明度
                            while (MAXTRANS <= 255) {
                                Thread.sleep(6);
                                marketBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                marketBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                marketBackLabel.repaint();
                                marketBackRightLabel.repaint();
                                MAXTRANS += 4;
                            }
                            canDo5[0] = true;
                            Thread.sleep(200);
                            if (!isIn5[0])
                                mouseExited(e);
                        }
                    }.start();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isIn5[0] = false;
                if (canDo5[0]) {
                    canDo5[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 255;  //透明度
                            while (MAXTRANS >= 0) {
                                Thread.sleep(6);
                                marketBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                marketBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                marketBackLabel.repaint();
                                marketBackRightLabel.repaint();
                                MAXTRANS -= 4;
                            }
                            canDo5[0] = true;
                            Thread.sleep(200);
                            if (isIn5[0])
                                mouseEntered(e);
                        }
                    }.start();
                }
            }
        });



        //设置
        ImageIcon settingIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/setting.png")));
        JLabel settingLabel = new JLabel(settingIcon);  //改主题标签
        menu.add(settingLabel);
        int oriY5 = oriY4 + addFriendIcon.getIconHeight() + 180;
        settingLabel.setBounds(11, oriY5, chatIcon.getIconWidth(), chatIcon.getIconHeight());

        //选中背景左部分
        RadioJLabel  settingBackLabel = new RadioJLabel("");  //聊天标签
        settingBackLabel.setColor(new Color(45,101,154,0));
        menu.add(settingBackLabel);  //选中背景
        settingBackLabel.setBounds(0, oriY5-10, addFriendIcon.getIconWidth()+32, addFriendIcon.getIconHeight()+20);

        //选中背景右部分
        RadioJLabel settingBackRightLabel = new RadioJLabel("");  //加好友标签
        settingBackRightLabel.setColor(new Color(45,101,154,0));
        menuTop.add(settingBackRightLabel);  //选中背景
        settingBackRightLabel.setBounds(0, oriY5-10, addFriendIcon.getIconWidth()+144, addFriendIcon.getIconHeight()+20);

        final boolean[] canDo6 = {true}; //判断能否加载动画，防止多线程闪屏bug
        final boolean[] isIn6 = {true}; //判断是否在组件里
        settingBackRightLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isMenuChild = true;  //是子组件
                isIn6[0] = true;
                if (canDo6[0]) {
                    canDo6[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 1;  //透明度
                            while (MAXTRANS <= 255) {
                                Thread.sleep(6);
                                settingBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                settingBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                settingBackLabel.repaint();
                                settingBackRightLabel.repaint();
                                MAXTRANS += 4;
                            }
                            canDo6[0] = true;
                            Thread.sleep(200);
                            if (!isIn6[0])
                                mouseExited(e);
                        }
                    }.start();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isIn6[0] = false;
                if (canDo6[0]) {
                    canDo6[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 255;  //透明度
                            while (MAXTRANS >= 0) {
                                Thread.sleep(6);
                                settingBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                settingBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                settingBackLabel.repaint();
                                settingBackRightLabel.repaint();
                                MAXTRANS -= 4;
                            }
                            canDo6[0] = true;
                            Thread.sleep(200);
                            if (isIn6[0])
                                mouseEntered(e);
                        }
                    }.start();
                }
            }
        });
        settingBackLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isMenuChild = true;  //是子组件
                isIn6[0] = true;
                if (canDo6[0]) {
                    canDo6[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 1;  //透明度
                            while (MAXTRANS <= 255) {
                                Thread.sleep(6);
                                settingBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                settingBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                settingBackLabel.repaint();
                                settingBackRightLabel.repaint();
                                MAXTRANS += 4;
                            }
                            canDo6[0] = true;
                            Thread.sleep(200);
                            if (!isIn6[0])
                                mouseExited(e);
                        }
                    }.start();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isIn6[0] = false;
                if (canDo6[0]) {
                    canDo6[0] = false;
                    new Thread() {  //背景动画
                        @SneakyThrows
                        @Override
                        public void run() {
                            int MAXTRANS = 255;  //透明度
                            while (MAXTRANS >= 0) {
                                Thread.sleep(6);
                                settingBackLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                settingBackRightLabel.setColor(new Color(45, 101, 154, MAXTRANS));
                                settingBackLabel.repaint();
                                settingBackRightLabel.repaint();
                                MAXTRANS -= 4;
                            }
                            canDo6[0] = true;
                            Thread.sleep(200);
                            if (isIn6[0])
                                mouseEntered(e);
                        }
                    }.start();
                }
            }
        });

    }
}
