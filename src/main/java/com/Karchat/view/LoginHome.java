package com.Karchat.view;

import com.Karchat.service.Minimize;
import com.Karchat.util.ColorUtil.ChangeToColor;
import com.Karchat.util.ComponentUtil.Button.ChooseBackButton;
import com.Karchat.util.ComponentUtil.Button.RoundButton;
import com.Karchat.util.ComponentUtil.Frame.Frameless;
import com.Karchat.util.ComponentUtil.Label.DynamicJLabel;
import com.Karchat.util.ComponentUtil.Label.RadioJLabel;
import com.Karchat.util.ComponentUtil.Label.ShakeLabel;
import com.Karchat.util.ComponentUtil.Loading.Loading;
import com.Karchat.util.Constant;
import com.Karchat.util.EmailUtil.sendEmail;
import com.Karchat.util.PictureUtil.ToBufferedImage;
import com.Karchat.util.PictureUtil.ToPicture;
import com.Karchat.util.SoundUtil.PlaySound;
import com.sun.awt.AWTUtilities;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

/**
 * 登录窗口,用绝对定位前后排列
 */
@Component
@Scope("prototype")
@Slf4j
public class LoginHome implements ActionListener, Minimize {

    public static boolean ANIMATION_KEEP_ON = false;
    static boolean iconified;
    private final ChooseBackButton remember;
    private final ChooseBackButton switchMode;
    private static JTextField emailText;
    private final JLabel registerLabel1;
    private final ImageIcon registerIcon1;
    private final MouseAdapter leftAdapter;
    private ImageIcon leftOpenIcon;
    private RadioJLabel sendEmailLabel;
    private RadioJLabel leftContent;
    private RadioJLabel leftBack;
    private JLabel leftOpen;
    private Boolean Switch;
    private static String verrify;
    private static boolean leftOpening = false;
    private TimerTask timer;
    private TimerTask timerOn;
    private Timer timer2;
    private Timer timerFather=new Timer();;

    public static void main(String[] args) {
        new LoginHome();
    }

    public static Frameless background;
    private final JLabel left;
    public static JPanel right;
    private final ImageIcon login;
    private final JLabel rightLabel;
    private final JLabel usernameLabel;
    private final ImageIcon text;
    private final JLabel passwordLabel;
    public static JTextField passwordMessage;
    public static JLabel sign;
    private final ImageIcon icon;
    public static ShakeLabel loginLabel;
    private final ImageIcon loginButtonIcon;
    private final JButton loginButton;
    public static JTextField username;
    public static JPasswordField password;
    private final JLabel registerLabel;
    private final ImageIcon registerIcon;
    private final ImageIcon registerbgIcon;
    private final JButton registerButton;
    private static JTextField registerText2;
    private static ShakeLabel registerLabel2;
    public static ShakeLabel registerLabel5;
    private static JPasswordField registerPassword3;
    private final JTextField registerMessage4;
    private static ShakeLabel registerLabel3;
    private static ShakeLabel registerLabel4;
    private static JPasswordField registerPassword4;
    private static JLabel registerMessage;
    private static JLabel registerLabelMessage1;
    private final JButton signButton;
    private final ShakeLabel signLabel;
    private final DynamicJLabel signMessage;
    public static DynamicJLabel wrongMessage;
    public static Loading load;
    public static Loading loadIn;
    private ImageIcon texton;
    private static String usernameMessage;
    private static String passwordMessage1;
    private JLabel registerbgLabel;
    public static String iconString;  //头像的String类型储存图片,默认是官方头像
    public static boolean isAlive = true;
    public static boolean switchFlag = false;  //判断是什么动画格式


    @SneakyThrows
    @PostConstruct
    public void init() {
        iconString = ToPicture.imageToString(ImageIO.read(Resources.getResourceAsStream("login/sign.png")), "png");
        log.info("登录界面启动中.....");
    }


    @SneakyThrows
    public LoginHome() {
        System.setProperty("sun.java2d.noddraw", "true");  //防止输入法输入时白屏，禁用DirectDraw
        //创建圆角容器
        background = new Frameless(1380, 743, false);
        AWTUtilities.setWindowOpacity(background, 0);  //全透明

        left = new JLabel();  //左半部分标签，不用容器
        DynamicJLabel plate = new DynamicJLabel("KarGoBang", new Font("Serif", Font.BOLD, 16), 56);
        plate.setForeground(new Color(30, 29, 29));

        ImageIcon toolBarIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("login/hamburger.png")));
        JLabel toolBar = new JLabel(toolBarIcon);
        right = new JPanel();  //右半部分容器

        RadioJLabel toolMenu = new RadioJLabel("");  //菜单栏
        toolMenu.setColor(new Color(162, 159, 159, 0));

        RadioJLabel toolMenuItem1 = new RadioJLabel("");
        RadioJLabel toolMenuItem2 = new RadioJLabel("");
        toolMenuItem1.setColor(new Color(162, 159, 159, 0));
        toolMenuItem2.setColor(new Color(162, 159, 159, 0));
        toolMenuItem1.setArc(15, 15);
        toolMenuItem2.setArc(15, 15);
        DynamicJLabel toolMenuMessage1 = new DynamicJLabel("记住密码", new Font("Serif", Font.BOLD, 16), 101);
        toolMenuMessage1.setForeground(new Color(79, 78, 78, 0));
        DynamicJLabel toolMenuMessage2 = new DynamicJLabel("暂停动画", new Font("Serif", Font.BOLD, 16), 141);
        toolMenuMessage2.setForeground(new Color(79, 78, 78, 0));


        remember = new ChooseBackButton("main/save.png", "main/saveOn.png", "main/saveOn.png", toolMenuItem1, new DynamicJLabel("", null, 0), ChooseBackButton.Location.EAST) {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                remember.isClick(c -> {
                    Constant.remember = c;
                }); //获取按钮是否点击
            }
        };  //单选按钮，是否记住密码
        switchMode = new ChooseBackButton("main/switch.png", "main/switchOn.png", "main/switchOn.png", toolMenuItem2, new DynamicJLabel("", null, 0), ChooseBackButton.Location.EAST) {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                remember.isClick(c -> {
                    switchFlag = !switchFlag;
                    new Thread() {
                        @Override
                        public void run() {
                            setPicture(left);
                        }
                    }.start();

                }); //获取按钮是否点击
            }
        };  //单选按钮,切换播放动画

        remember.setColor(new Color(162, 159, 159, 0));
        switchMode.setColor(new Color(162, 159, 159, 0));

        final boolean[] toolMenuFlag = {false};
        //开启菜单
        toolBar.addMouseListener(new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                if (toolMenuFlag[0]) {
                    toolMenuFlag[0] = false;
                    toolMenuItem1.setColor(new Color(162, 159, 159, 0));
                    toolMenuItem2.setColor(new Color(162, 159, 159, 0));
                    toolMenuMessage1.setForeground(new Color(79, 78, 78, 0));
                    toolMenuMessage2.setForeground(new Color(79, 78, 78, 0));
                    remember.setBounds(left.getWidth() - 260, 98, 0, 0);
                    switchMode.setBounds(left.getWidth() - 260, 138, 0, 0);
                    toolMenu.setColor(new Color(162, 159, 159, 0));


                    toolMenuItem1.repaint();
                    toolMenuItem2.repaint();
                    toolMenuMessage1.repaint();
                    toolMenuMessage2.repaint();
                    remember.repaint();
                    switchMode.repaint();
                    toolMenu.repaint();
                    toolMenuFlag[0] = false;

                } else {
                    toolMenuFlag[0] = true;
                    toolMenuItem1.setColor(new Color(162, 159, 159, 255));
                    toolMenuItem2.setColor(new Color(162, 159, 159, 255));
                    toolMenuMessage1.setForeground(new Color(79, 78, 78, 255));
                    toolMenuMessage2.setForeground(new Color(79, 78, 78, 255));
                    remember.setBounds(left.getWidth() - 260, 98, 35, 35);
                    switchMode.setBounds(left.getWidth() - 260, 138, 35, 35);
                    toolMenu.setColor(new Color(162, 159, 159, 255));

                    toolMenuItem1.repaint();
                    toolMenuItem2.repaint();
                    toolMenuMessage1.repaint();
                    toolMenuMessage2.repaint();
                    remember.repaint();
                    switchMode.repaint();
                    toolMenu.repaint();
                    toolMenuFlag[0] = true;

                }
            }
        });



        //最大化最小化动画
        background.addWindowListener(new WindowAdapter() {
            @SneakyThrows
            @Override
            public void windowIconified(WindowEvent e) {
                AWTUtilities.setWindowOpacity(background, 0);  //半透明
                //最小化状态
                iconified = true;
            }

            @SneakyThrows
            @Override
            public void windowDeiconified(WindowEvent e) {
                AWTUtilities.setWindowOpacity(background, 0);  //半透明
                iconified = false;
                maximize();
//                Thread.sleep(1000);
            }


        });
        //创建右上角圆按钮，并添加监听器
        RoundButton Rbut1 = new RoundButton("", new Color(58, 124, 243, 190), new Color(92, 143, 236, 221), new Color(132, 171, 243, 181)) {
            @Override
            public void mouseClicked(MouseEvent e) {
                background.setExtendedState(JFrame.ICONIFIED);
            }

        };
        RoundButton Rbut2 = new RoundButton("", new Color(243, 58, 101, 192), new Color(238, 70, 109, 189), new Color(252, 108, 141, 189)) {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                float MAXTRANS = 1;  //透明度
                while (MAXTRANS >= 0) {
                    Thread.sleep(2);
                    AWTUtilities.setWindowOpacity(background, MAXTRANS);  //半透明
                    MAXTRANS -= 0.05;
                }
                System.exit(1);
            }

        };
        username = new JTextField();  //账号框
        password = new JPasswordField();  //密码输入框
        wrongMessage = new DynamicJLabel("", new Font("Serif", Font.BOLD, 18), 249);
        wrongMessage.setForeground(new Color(215, 27, 71, 205));
        //未连接时的加载框
        load = new Loading();
        load.setLayout(null);
        loadIn = new Loading();
        loadIn.setLayout(null);


        left.setBackground(new Color(0, 0, 0, 0));   //将左半部分容器设为透明
        right.setBackground(new Color(0, 0, 0, 0));   //将右半部分容器设为透明


        background.setLocation(250, 80);   //设置初始位置

        { //给右半部分加入图片
            login = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("login/login.png")));
            rightLabel = new JLabel(login);
            right.add(rightLabel, 0);
            rightLabel.setBounds(0, 0, login.getIconWidth(), login.getIconHeight());
        }
        { //给账号框和密码框加入图片
            text = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("login/text.png")));
            texton = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("login/texton.png")));
            usernameLabel = new JLabel(text);
            usernameLabel.add(username);  //加入信息框
            username.setText("请输入账号");
            username.setBounds(20, 10, text.getIconWidth() - 40, text.getIconHeight() - 20);
            username.setHorizontalAlignment(JTextField.HORIZONTAL);
            username.setOpaque(false);
            username.setBorder(null);
            username.setFont(new Font("Serif", Font.BOLD, 26));
            username.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (username.getText().equals("请输入账号"))  //若要输入账号则取消提示
                        username.setText("");
                    usernameLabel.setIcon(texton);
                    usernameLabel.setBounds(right.getWidth() - 15, 260, texton.getIconWidth(), texton.getIconHeight());
                    username.setBounds(37, 30, text.getIconWidth() - 50, text.getIconHeight() - 20);
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (username.getText().equals(""))   //若未输入内容则重新提示
                        username.setText("请输入账号");
                    usernameLabel.setIcon(text);
                    usernameLabel.setBounds(right.getWidth() - 2, 290, text.getIconWidth(), text.getIconHeight());
                    username.setBounds(20, 10, text.getIconWidth() - 40, text.getIconHeight() - 20);
                }
            });

            //账号加入键盘监听
            username.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    if (e.getKeyChar() == KeyEvent.VK_ENTER) {  //登录
                        usernameMessage = username.getText();  //获取账号密码
                        passwordMessage1 = password.getText();

                        Constant.login = true;  //登录
                    }
                }
            });

            passwordLabel = new JLabel(text);
            passwordMessage = new JTextField();  //密码信息提示
            passwordMessage.setHorizontalAlignment(JTextField.HORIZONTAL);
            passwordMessage.setOpaque(false);
            passwordMessage.setBorder(null);
            passwordMessage.setFont(new Font("Serif", Font.BOLD, 26));

            passwordLabel.add(password);  //加入密码框
            passwordLabel.add(passwordMessage);  //加入密码框
            passwordMessage.setText("请输入密码");
            passwordMessage.setBounds(20, 10, text.getIconWidth() - 40, text.getIconHeight() - 20);
            password.setBounds(20, 10, text.getIconWidth() - 40, text.getIconHeight() - 20);
            password.setHorizontalAlignment(JTextField.HORIZONTAL);
            password.setOpaque(false);
            password.setBorder(null);
            password.setFont(new Font("Serif", Font.BOLD, 32));
            password.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (passwordMessage.getText().equals("请输入密码"))
                        passwordLabel.remove(passwordMessage);
                    passwordLabel.setIcon(texton);
                    passwordLabel.setBounds(right.getWidth() - 15, 350, texton.getIconWidth(), texton.getIconHeight());
                    password.setBounds(37, 30, text.getIconWidth() - 50, text.getIconHeight() - 20);
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (password.getText().equals(""))
                        passwordLabel.add(passwordMessage);
                    passwordLabel.setIcon(text);
                    passwordLabel.setBounds(right.getWidth() - 2, 380, text.getIconWidth(), text.getIconHeight());
                    password.setBounds(20, 10, text.getIconWidth() - 40, text.getIconHeight() - 20);
                }
            });

            //密码加入键盘监听
            password.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    if (e.getKeyChar() == KeyEvent.VK_ENTER) {  //登录
                        usernameMessage = username.getText();  //获取账号密码
                        passwordMessage1 = password.getText();

                        Constant.login = true;//登录
                    }
                }
            });
        }
        {  //标志加入图片
            icon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("login/sign.png")));
            sign = new JLabel(icon); //应用标志

        }
        {  //登录按钮加入图片
            loginButtonIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("login/button.png")));
            ImageIcon loginButtonIconOn = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("login/buttonon.png")));
            loginButton = new JButton(); //按钮
            loginButton.addActionListener(this);  //按钮加入监听
            JLabel loginMessage = new JLabel("登录");
            loginLabel = new ShakeLabel(loginButtonIcon);
            loginMessage.setFont(new Font("Serif", Font.BOLD, 30));
            loginMessage.setForeground(new Color(255, 255, 255));
            loginMessage.setHorizontalAlignment(SwingConstants.CENTER);
            loginLabel.add(loginButton);  //加入按钮
            loginLabel.add(loginMessage);  //加入信息
            loginMessage.setBounds(29, 21, loginButtonIcon.getIconWidth() - 70, loginButtonIcon.getIconHeight() - 67);
            loginButton.setBounds(20, 21, loginButtonIcon.getIconWidth() - 70, loginButtonIcon.getIconHeight() - 67);
            loginButton.setContentAreaFilled(false);
            loginButton.setBorder(null);  //无边框
            loginButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    loginLabel.setIcon(loginButtonIconOn);  //修改按钮图片
                    loginMessage.setBounds(55, 48, loginButtonIcon.getIconWidth() - 70, loginButtonIcon.getIconHeight() - 67);
                    loginButton.setBounds(20, 21, loginButtonIconOn.getIconWidth() - 70, loginButtonIconOn.getIconHeight() - 67);
                    loginLabel.setBounds(right.getWidth() - 40, 472, loginButtonIconOn.getIconWidth() + 30, loginButtonIconOn.getIconHeight());
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    loginLabel.setIcon(loginButtonIcon);  //修改按钮图片
                    loginButton.setBounds(20, 21, loginButtonIcon.getIconWidth() - 70, loginButtonIcon.getIconHeight() - 67);
                    loginLabel.setBounds(right.getWidth() - 25, 490, loginButtonIcon.getIconWidth() + 30, loginButtonIcon.getIconHeight());
                    loginMessage.setBounds(29, 21, loginButtonIcon.getIconWidth() - 70, loginButtonIcon.getIconHeight() - 67);
                }
            });

        }
        {  //注册按钮加入图片
            registerIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("login/registericon.png")));
            registerLabel = new JLabel(registerIcon);
            JLabel registerMessage = new JLabel("注册账号");
            registerMessage.setFont(new Font("Courier", Font.BOLD, 17));
            registerMessage.setForeground(new Color(255, 255, 255));
            registerMessage.setHorizontalAlignment(SwingConstants.CENTER);
            JButton registerButton = new JButton();
            registerLabel.add(registerButton);  //加入按钮
            registerLabel.add(registerMessage);  //加入提示
            registerMessage.setBounds(0, 0, registerIcon.getIconWidth(), registerIcon.getIconHeight());
            registerButton.setBounds(0, 0, registerIcon.getIconWidth(), registerIcon.getIconHeight());
            registerButton.setContentAreaFilled(false);
            registerButton.setBorder(null);  //无边框
            final boolean[] flag = {true}; //背景状态的标志
            final boolean[] start = {true};  //开始状态标志
            registerButton.addMouseListener(new MouseAdapter() {
                @SneakyThrows
                @Override
                public void mousePressed(MouseEvent e) {
                    if (start[0]) {  //加入开始判断标志，防止快速点击线程没锁住，因为这里用的匿名类，所以不方便用单例模式锁线程，直接用标记位完成
                        start[0] = false;  //执行后立刻将开始标记位切换为负
                        Thread regis = new Thread() {
                            @SneakyThrows
                            @Override
                            public synchronized void run() {
                                if (flag[0]) {  //第一次点击降下
                                    int height = -registerbgIcon.getIconHeight();
                                    registerbgLabel.setBounds(0, height, registerbgIcon.getIconWidth(), registerbgIcon.getIconHeight());
                                    while (height < 85) {
                                        Thread.sleep(1);
                                        height += 7;
                                        registerbgLabel.setLocation(0, height);
                                    }
                                    flag[0] = false;  //修改标记位置
                                    start[0] = true;  //修改开始标记
                                } else {  //再次点击收起
                                    int height = 85;
                                    while (height > -registerbgIcon.getIconHeight()) {
                                        Thread.sleep(1);
                                        height -= 7;
                                        registerbgLabel.setLocation(0, height);
                                    }
                                    flag[0] = true;
                                    start[0] = true;  //修改开始标记
                                }
                            }
                        };
                        regis.start();  //开始线程
                    }
                }
            });

        }
        {  //注册背景图片加入，以及按钮和标签
            registerbgIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("register/registerbg.png")));
            registerbgLabel = new JLabel(registerbgIcon);

            //第一条提示标签
            registerIcon1 = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("register/btext.png")));
            registerLabel1 = new JLabel(registerIcon1);
            registerLabelMessage1 = new JLabel("用户注册");
            registerLabelMessage1.setFont(new Font("Courier", Font.BOLD, 30));
            registerLabelMessage1.setForeground(new Color(255, 255, 255));
            registerLabelMessage1.setHorizontalAlignment(SwingConstants.CENTER);
            registerLabelMessage1.setBounds(0, 0, registerIcon1.getIconWidth(), registerIcon1.getIconHeight());
            registerbgLabel.add(registerLabel1);  //加入提示标签
            registerLabel1.add(registerLabelMessage1);  //加入提示信息
            registerLabel1.setBounds(registerbgLabel.getWidth() / 2 - registerLabel1.getWidth() / 2 + 244, 52, registerIcon1.getIconWidth(), registerIcon1.getIconHeight());

            //账号注册框
            ImageIcon registerIcon2 = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("register/text.png")));
            ImageIcon registerIcon2On = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("register/gtext.png")));
            ImageIcon registerIcon2Wrong = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("register/ytext.png")));

            //账号框注册
            registerText2 = new JTextField();
            //账号注册框背景
            registerLabel2 = new ShakeLabel(registerIcon2);
            //加入邮箱验证
            ImageIcon leftOpenIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/left.png")));
            leftOpen = new JLabel(leftOpenIcon);
            sendEmailLabel = new RadioJLabel("");
            sendEmailLabel.setColor(ChangeToColor.getColorFromHex("#CEAB93"));
            DynamicJLabel sendEmailMessage = new DynamicJLabel("获取验证码", new Font("Serif", Font.BOLD, 18), 8);
            sendEmailMessage.setCenter(150);
            sendEmailMessage.setForeground(ChangeToColor.getColorFromHex("#FFFBE9"));
            sendEmailLabel.add(sendEmailMessage);
            leftContent = new RadioJLabel("");
            leftContent.setColor(ChangeToColor.getColorFromHex("#FFFBE9"));
            leftBack = new RadioJLabel();
            leftBack.setColor(ChangeToColor.getColorFromHex("#AD8B73"));

            System.setProperty("sun.java2d.noddraw", "true");  //防止输入法输入时白屏，禁用DirectDraw

            emailText = new JTextField();
            emailText.setForeground(ChangeToColor.getColorFromHex("#E3CAA5"));

            registerbgLabel.add(emailText);  //加入搜索框

            leftOpenIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("main/left.png")));
            leftOpen = new JLabel(leftOpenIcon);
            registerbgLabel.add(leftContent);
            registerbgLabel.add(sendEmailLabel);
            registerbgLabel.add(leftOpen);
            leftOpen.setBounds(registerbgLabel.getWidth() / 2 - registerLabel1.getWidth() / 2 + 244 + 676, 350, leftOpenIcon.getIconWidth(), leftOpenIcon.getIconHeight());

            registerbgLabel.add(leftBack);
            leftBack.setBounds(registerbgLabel.getWidth() / 2 - registerLabel1.getWidth() / 2 + 244 + 160 + registerIcon1.getIconWidth() + 80 + 40, 330, registerIcon1.getIconWidth() + 80, registerIcon1.getIconHeight());

            boolean[] LEFTISFINISH = {true};  //判断展开栏是否加载完动画
            //展开栏加入点击事件
            leftAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    label:
                    {
                        if (LEFTISFINISH[0] && !leftOpening) {
                            leftOpening = true;
                            LEFTISFINISH[0] = false;  //动画未加载完
                            //储存四个内容的位置
                            int LOCATION1 = leftBack.getX();
                            int LOCATION2 = leftContent.getX();
                            int LOCATION3 = sendEmailLabel.getX();
                            int LOCATION4 = emailText.getX();
                            final int[] LOCATION = {0};  //当前位置
                            new Thread() {
                                @SneakyThrows
                                @Override
                                public void run() {
                                    while (LOCATION[0] <= registerIcon1.getIconWidth() + 80 + 40) {
                                        Thread.sleep(2);
                                        leftBack.setBounds(LOCATION1 - LOCATION[0], leftBack.getY(), leftBack.getWidth(), leftBack.getHeight());
                                        leftContent.setBounds(LOCATION2 - LOCATION[0], leftContent.getY(), leftContent.getWidth(), leftContent.getHeight());
                                        sendEmailLabel.setBounds(LOCATION3 - LOCATION[0], sendEmailLabel.getY(), sendEmailLabel.getWidth(), sendEmailLabel.getHeight());
                                        emailText.setBounds(LOCATION4 - LOCATION[0], emailText.getY(), emailText.getWidth(), emailText.getHeight());

                                        LOCATION[0] += 4;
                                    }
                                    LEFTISFINISH[0] = true;
                                }
                            }.start();
                            break label;
                        }
                        if (LEFTISFINISH[0] && leftOpening) {
                            leftOpening = false;
                            LEFTISFINISH[0] = false;  //动画未加载完
                            final int[] LOCATIONON = {0};  //当前位置
                            //储存四个内容的位置
                            int LOCATION1 = leftBack.getX();
                            int LOCATION2 = leftContent.getX();
                            int LOCATION3 = sendEmailLabel.getX();
                            int LOCATION4 = emailText.getX();
                            new Thread() {
                                @SneakyThrows
                                @Override
                                public void run() {
                                    while (LOCATIONON[0] <= registerIcon1.getIconWidth() + 80 + 40) {
                                        Thread.sleep(2);
                                        leftBack.setBounds(LOCATION1 + LOCATIONON[0], leftBack.getY(), leftBack.getWidth(), leftBack.getHeight());
                                        leftContent.setBounds(LOCATION2 + LOCATIONON[0], leftContent.getY(), leftContent.getWidth(), leftContent.getHeight());
                                        sendEmailLabel.setBounds(LOCATION3 + LOCATIONON[0], sendEmailLabel.getY(), sendEmailLabel.getWidth(), sendEmailLabel.getHeight());
                                        emailText.setBounds(LOCATION4 + LOCATIONON[0], emailText.getY(), emailText.getWidth(), emailText.getHeight());

                                        //registerIcon1.getIconWidth()+80

                                        LOCATIONON[0] += 4;
                                    }
                                    LEFTISFINISH[0] = true;
                                }
                            }.start();
                            break label;

                        }
                        LEFTISFINISH[0] = true;
                    }
                }
            };
            leftOpen.addMouseListener(leftAdapter);
            //--------------------------
            registerbgLabel.add(registerLabel2);  //加入账号注册框
            registerLabel2.setBounds(246, 145, registerIcon1.getIconWidth(), registerIcon1.getIconHeight());

            registerLabel2.add(registerText2);  //加入信息框
            registerText2.setText("请输入账号");
            registerText2.setBounds(20, 10, registerIcon2.getIconWidth() - 40, registerIcon2.getIconHeight() - 20);
            registerText2.setHorizontalAlignment(JTextField.HORIZONTAL);
            registerText2.setOpaque(false);
            registerText2.setBorder(null);
            registerText2.setFont(new Font("Serif", Font.BOLD, 26));
            registerText2.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (registerText2.getText().equals("请输入账号") || registerText2.getText().equals("账号应为6-20位")) {  //若要输入账号则取消提示
                        registerText2.setText("");
                    }
                    registerLabel2.setIcon(registerIcon2On);
                    registerLabel2.setBounds(246, 145, registerIcon1.getIconWidth(), registerIcon1.getIconHeight());
                    registerText2.setBounds(20, 10, registerIcon2.getIconWidth() - 40, registerIcon2.getIconHeight() - 20);
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (registerText2.getText().equals("")) {   //若未输入内容则重新提示
                        registerText2.setText("请输入账号");
                        registerLabel2.setIcon(registerIcon2);
                        registerLabel2.setBounds(246, 145, registerIcon1.getIconWidth(), registerIcon1.getIconHeight());
                        registerText2.setBounds(20, 10, registerIcon2.getIconWidth() - 40, registerIcon2.getIconHeight() - 20);
                    } else if (registerText2.getText().matches("(?=.*\\d)^.{6,20}$") || registerText2.getText().matches("(?=.*[a-z])^.{6,20}$")) {  //判断账号是否符合正则要求
                        registerLabel2.setIcon(registerIcon2);
                        registerLabel2.setBounds(246, 145, registerIcon1.getIconWidth(), registerIcon1.getIconHeight());
                        registerText2.setBounds(20, 10, registerIcon2.getIconWidth() - 40, registerIcon2.getIconHeight() - 20);
                    } else {
                        registerText2.setText("账号应为6-20位");
                        registerLabel2.shake();  //错误则抖动
                        registerLabel2.setIcon(registerIcon2Wrong);
                        registerLabel2.setBounds(246, 145, registerIcon1.getIconWidth(), registerIcon1.getIconHeight());
                        registerText2.setBounds(20, 10, registerIcon2.getIconWidth() - 40, registerIcon2.getIconHeight() - 20);
                    }
                }
            });

            //账号注册加入键盘监听
            registerText2.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    if (e.getKeyChar() == KeyEvent.VK_ENTER) {  //发送邮件
                        register();
                    }
                }
            });

            //密码注册框
            ImageIcon registerIcon3 = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("register/text.png")));
            ImageIcon registerIcon3On = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("register/gtext.png")));
            ImageIcon registerIcon3Wrong = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("register/ytext.png")));
            JTextField registerMessage3 = new JTextField();  //密码信息提示
            registerMessage3.setHorizontalAlignment(JTextField.HORIZONTAL);
            registerMessage3.setOpaque(false);
            registerMessage3.setBorder(null);
            registerMessage3.setFont(new Font("Serif", Font.BOLD, 26));
            registerMessage3.setText("请输入密码");
            registerMessage3.setBounds(20, 10, text.getIconWidth() - 40, text.getIconHeight() - 20);


            //密码输入框注册
            registerPassword3 = new JPasswordField();
            //账号注册框背景
            registerLabel3 = new ShakeLabel(registerIcon3);
            registerbgLabel.add(registerLabel3);  //加入账号注册框
            registerLabel3.setBounds(246, 238, registerIcon1.getIconWidth(), registerIcon1.getIconHeight());

            registerLabel3.add(registerPassword3);  //加入信息框
            registerLabel3.add(registerMessage3);  //加入信息提示


            registerPassword3.setBounds(20, 10, registerIcon2.getIconWidth() - 40, registerIcon2.getIconHeight() - 20);
            registerPassword3.setHorizontalAlignment(JTextField.HORIZONTAL);
            registerPassword3.setOpaque(false);
            registerPassword3.setBorder(null);
            registerPassword3.setFont(new Font("Serif", Font.BOLD, 29));
            registerPassword3.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (registerMessage3.getText().equals("请输入密码") || registerMessage3.getText().equals("只能为6-12位的数字或字母"))  //若要输入账号则取消提示
                        registerLabel3.remove(registerMessage3);
                    registerMessage3.setText("请输入密码");

                    registerLabel3.setIcon(registerIcon3On);
                    registerLabel3.setBounds(246, 238, registerIcon1.getIconWidth(), registerIcon1.getIconHeight());
                    registerPassword3.setBounds(20, 10, registerIcon2.getIconWidth() - 40, registerIcon2.getIconHeight() - 20);
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (registerPassword3.getText().equals("")) {  //若未输入内容则重新提示
                        registerLabel3.add(registerMessage3);
                        registerLabel3.setIcon(registerIcon2);
                        registerLabel3.setBounds(246, 238, registerIcon1.getIconWidth(), registerIcon1.getIconHeight());
                        registerPassword3.setBounds(20, 10, registerIcon2.getIconWidth() - 40, registerIcon2.getIconHeight() - 20);
                    } else if (registerPassword3.getText().matches("(?=.*\\d)^.{6,12}$") || registerPassword3.getText().matches("(?=.*[a-z])^.{6,12}$")) {  //判断密码是否符合正则的要求
                        registerLabel3.setIcon(registerIcon2);
                        registerLabel3.setBounds(246, 238, registerIcon1.getIconWidth(), registerIcon1.getIconHeight());
                        registerPassword3.setBounds(20, 10, registerIcon2.getIconWidth() - 40, registerIcon2.getIconHeight() - 20);
                    } else {
                        registerLabel3.shake();
                        registerMessage3.setText("只能为6-12位的数字或字母");
                        registerPassword3.setText("");
                        registerLabel3.add(registerMessage3);
                        registerLabel3.setIcon(registerIcon3Wrong);
                        registerLabel3.setBounds(246, 238, registerIcon1.getIconWidth(), registerIcon1.getIconHeight());
                        registerPassword3.setBounds(20, 10, registerIcon2.getIconWidth() - 40, registerIcon2.getIconHeight() - 20);
                    }
                }
            });

            //账号注册加入键盘监听
            registerPassword3.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    if (e.getKeyChar() == KeyEvent.VK_ENTER) {  //发送邮件
                        register();
                    }
                }
            });

            //确认密码框
            //密码注册框
            ImageIcon registerIcon4 = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("register/text.png")));
            ImageIcon registerIcon4On = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("register/gtext.png")));
            ImageIcon registerIcon4Wrong = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("register/ytext.png")));
            //密码信息提示
            registerMessage4 = new JTextField();
            registerMessage4.setHorizontalAlignment(JTextField.HORIZONTAL);
            registerMessage4.setOpaque(false);
            registerMessage4.setBorder(null);
            registerMessage4.setFont(new Font("Serif", Font.BOLD, 29));
            registerMessage4.setText("请再次输入密码");
            registerMessage4.setBounds(20, 10, text.getIconWidth() - 40, text.getIconHeight() - 20);


            //密码输入框注册
            registerPassword4 = new JPasswordField();
            //账号注册框背景
            registerLabel4 = new ShakeLabel(registerIcon4);
            registerbgLabel.add(registerLabel4);  //加入账号注册框
            registerLabel4.setBounds(246, 331, registerIcon1.getIconWidth(), registerIcon1.getIconHeight());

            registerLabel4.add(registerPassword4);  //加入信息框
            registerLabel4.add(registerMessage4);  //加入信息提示


            registerPassword4.setBounds(20, 10, registerIcon2.getIconWidth() - 40, registerIcon2.getIconHeight() - 20);
            registerPassword4.setHorizontalAlignment(JTextField.HORIZONTAL);
            registerPassword4.setOpaque(false);
            registerPassword4.setBorder(null);
            registerPassword4.setFont(new Font("Serif", Font.BOLD, 26));
            registerPassword4.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (registerMessage4.getText().equals("请再次输入密码") || registerMessage4.getText().equals("输入的密码不一致")) {  //若要输入账号则取消提示
                        registerMessage4.setText("请再次输入密码");
                        registerLabel4.remove(registerMessage4);
                    }

                    registerLabel4.setIcon(registerIcon4On);
                    registerLabel4.setBounds(246, 331, registerIcon1.getIconWidth(), registerIcon1.getIconHeight());
                    registerPassword4.setBounds(20, 10, registerIcon2.getIconWidth() - 40, registerIcon2.getIconHeight() - 20);
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (registerPassword4.getText().equals("")) {   //若未输入内容则重新提示
                        registerLabel4.add(registerMessage4);
                        registerLabel4.setIcon(registerIcon2);
                        registerLabel4.setBounds(246, 331, registerIcon1.getIconWidth(), registerIcon1.getIconHeight());
                        registerPassword4.setBounds(20, 10, registerIcon2.getIconWidth() - 40, registerIcon2.getIconHeight() - 20);
                    } else if (registerPassword4.getText().equals(registerPassword3.getText())) {  //若密码一致则保留
                        registerLabel4.setIcon(registerIcon2);
                        registerLabel4.setBounds(246, 331, registerIcon1.getIconWidth(), registerIcon1.getIconHeight());
                        registerPassword4.setBounds(20, 10, registerIcon2.getIconWidth() - 40, registerIcon2.getIconHeight() - 20);
                    } else {  //输入的密码不一致时
                        registerLabel4.shake();  //错误时抖动
                        registerMessage4.setText("输入的密码不一致");
                        registerPassword4.setText("");  //清空内容
                        registerLabel4.add(registerMessage4);
                        registerLabel4.setIcon(registerIcon4Wrong);
                        registerLabel4.setBounds(246, 331, registerIcon1.getIconWidth(), registerIcon1.getIconHeight());
                        registerPassword4.setBounds(20, 10, registerIcon2.getIconWidth() - 40, registerIcon2.getIconHeight() - 20);

                    }
                }
            });

            registerPassword4.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    if (e.getKeyChar() == KeyEvent.VK_ENTER) {  //发送邮件
                        register();
                    }
                }
            });
            //确认注册按钮
            //登录按钮加入图片
            ImageIcon registerIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("register/rtext.png")));
            //按钮
            registerButton = new JButton();
            registerButton.addActionListener(this);  //按钮加入监听

            registerMessage = new JLabel("注册");
            registerLabel5 = new ShakeLabel(registerIcon);
            registerMessage.setFont(new Font("Serif", Font.BOLD, 30));
            registerMessage.setForeground(new Color(255, 255, 255));
            registerMessage.setHorizontalAlignment(SwingConstants.CENTER);

            registerbgLabel.add(registerLabel5);
            registerLabel5.add(registerButton);  //加入按钮
            registerLabel5.add(registerMessage);  //加入信息

            registerLabel5.setBounds(246, 424, registerIcon1.getIconWidth(), registerIcon1.getIconHeight());

            registerMessage.setBounds(20, 10, registerIcon2.getIconWidth() - 40, registerIcon2.getIconHeight() - 20);
            registerButton.setBounds(20, 10, registerIcon2.getIconWidth() - 40, registerIcon2.getIconHeight() - 20);
            registerButton.setContentAreaFilled(false);
            registerButton.setBorder(null);  //无边框

            //注册事件
            registerButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (Objects.equals(registerText2.getText(), "") || Objects.equals(registerText2.getText(), "请输入账号")) {
                        registerLabel2.shake();  //若未填入账号则抖动
                        registerLabel5.shake(); //按钮抖动
                        new Thread() {
                            @SneakyThrows
                            @Override
                            public void run() {
                                PlaySound.play("sound/error.mp3");
                            }
                        }.start();
                    } else if (Objects.equals(registerPassword3.getText(), "") || registerPassword3.getText().equals("请输入密码")) {
                        registerLabel3.shake();
                        registerLabel5.shake(); //按钮抖动
                        new Thread() {
                            @SneakyThrows
                            @Override
                            public void run() {
                                PlaySound.play("sound/error.mp3");
                            }
                        }.start();
                    } else if (Objects.equals(registerPassword4.getText(), "") || registerPassword4.getText().equals("请再次输入密码")) {
                        registerLabel4.shake();
                        registerLabel5.shake(); //按钮抖动
                        new Thread() {
                            @SneakyThrows
                            @Override
                            public void run() {
                                PlaySound.play("sound/error.mp3");
                            }
                        }.start();
                    } else {  //注册成功
                        if (leftOpening) {  //若弹窗已经弹出
                            String email = emailText.getText();  //储存邮箱
                            if (email.matches("^\\S{6}$")) {  //若是六位随机数
                                if (verrify.equals(email)) {  //验证码验证成功
                                    usernameMessage = registerText2.getText();  //获取账号
                                    passwordMessage1 = registerPassword3.getText(); //获取密码

                                    Constant.register = true;  //查看数据库中是否存在该账户

                                } else {
                                    emailText.setText("验证码输入错误");
                                    emailText.setFont(new Font("Serif", Font.BOLD, 20));
                                    emailText.setForeground(new Color(161, 19, 19));
                                }
                            } else {
                                emailText.setText("验证码应为6位");
                                emailText.setFont(new Font("Serif", Font.BOLD, 20));
                                emailText.setForeground(new Color(161, 19, 19));
                            }
                        } else {  //若未弹出则弹出弹框
                            leftAdapter.mouseClicked(e);
                        }
                    }
                }
            });


            //换头像按钮
            ImageIcon signIcon = new ImageIcon(ImageIO.read(Resources.getResourceAsStream("register/defaultIcon.png")));
            signButton = new JButton();
            signMessage = new DynamicJLabel("选择头像", new Font("Serif", Font.BOLD, 16), 320);
            signButton.addActionListener(this);  //按钮加入监听

            signLabel = new ShakeLabel(signIcon);

            registerbgLabel.add(signLabel);
            registerbgLabel.add(signMessage);
            signLabel.add(signButton);  //加入按钮

            signLabel.setBounds(80, 215, signIcon.getIconWidth(), signIcon.getIconHeight());
            signMessage.setCenter(269);

            signButton.setBounds(0, 0, signIcon.getIconWidth(), signIcon.getIconHeight());
            signButton.setContentAreaFilled(false);
            signButton.setBorder(null);  //无边框

        }

        {  //注册邮箱验证码

            leftContent.setBounds(registerbgLabel.getWidth() / 2 - registerLabel1.getWidth() / 2 + 244 + 180 + registerIcon1.getIconWidth() + 80 + 40, 340, registerIcon1.getIconWidth() - 110, registerIcon1.getIconHeight() - 20);
//            leftContent.setColor(new Color(0, 0, 0));

            sendEmailLabel.setBounds(registerbgLabel.getWidth() / 2 - registerLabel1.getWidth() / 2 + 244 + 250 + registerIcon1.getIconWidth() - 165 + registerIcon1.getIconWidth() + 80 + 40, 340, registerIcon1.getIconWidth() - 300, registerIcon1.getIconHeight() - 20);
//            sendEmailLabel.setColor(new Color(0, 0, 0));


            emailText.setBounds(registerbgLabel.getWidth() / 2 - registerLabel1.getWidth() / 2 + 244 + 200 + registerIcon1.getIconWidth() + 80 + 40, 345, registerIcon1.getIconWidth() - 150, registerIcon1.getIconHeight() - 30);
            emailText.setFont(new Font("Serif", Font.BOLD, 20));
            emailText.setText("输入邮箱");
            emailText.setOpaque(false);
            emailText.setBorder(null);

            emailText.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (emailText.getText().contains("输入邮箱") || emailText.getText().contains("验证码发送成功,清在此输入验证码") || emailText.getText().contains("验证码发送失败") || emailText.getText().contains("邮箱格式错误") || emailText.getText().equals("验证码发送成功,清在此输入验证码") || emailText.getText().equals("验证码发送失败") || emailText.getText().equals("邮箱格式错误") || emailText.getText().equals("验证码应为6位") || emailText.getText().equals("验证码输入错误")) {  //若要输入账号则取消提示
                        emailText.setFont(new Font("Serif", Font.BOLD, 20));
                        emailText.setForeground(ChangeToColor.getColorFromHex("#E3CAA5"));
                        emailText.setText("");
                    }
                }
            });
            emailText.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (emailText.getText().equals("输入邮箱") || emailText.getText().equals("验证码发送成功,清在此输入验证码") || emailText.getText().equals("验证码发送失败") || emailText.getText().equals("邮箱格式错误") || emailText.getText().equals("验证码应为6位") || emailText.getText().equals("验证码输入错误")) {  //若要输入账号则取消提示
                        emailText.setFont(new Font("Serif", Font.BOLD, 20));
                        emailText.setForeground(ChangeToColor.getColorFromHex("#E3CAA5"));
                        emailText.setText("");
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (emailText.getText().equals("")) {   //若未输入内容则重新提示
                        emailText.setFont(new Font("Serif", Font.BOLD, 20));
                        emailText.setForeground(ChangeToColor.getColorFromHex("#E3CAA5"));
                        emailText.setText("输入邮箱");
                    }
                }
            });

            //搜索加入键盘监听
            emailText.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    if (e.getKeyChar() == KeyEvent.VK_ENTER) {  //发送邮件
                        String email = emailText.getText();  //储存邮箱
                        if (email.matches("[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}")) {  //用正则表达式判断邮箱格式是否正确
                            try {
                                sendEmail sendEmail = new sendEmail();
                                //设置要发送的邮箱
                                sendEmail.setReceiveMailAccount(email);
                                verrify = sendEmail.Send();
                                emailText.setText("验证码发送成功,清在此输入验证码");
                                emailText.setFont(new Font("Serif", Font.BOLD, 17));
                                emailText.setForeground(new Color(62, 171, 159));
                            } catch (Exception a) {
                                emailText.setText("验证码发送失败");
                                emailText.setFont(new Font("Serif", Font.BOLD, 20));
                                emailText.setForeground(new Color(161, 19, 19));
                                new Thread() {
                                    @SneakyThrows
                                    @Override
                                    public void run() {
                                        PlaySound.play("sound/error.mp3");
                                    }
                                }.start();
                            }


                        } else {
                            if (email.matches("^\\S{6}$")) {  //若是六位随机数
                                if (email.equals(verrify)) {  //验证码验证成功
                                    usernameMessage = registerText2.getText();  //获取账号
                                    passwordMessage1 = registerPassword3.getText(); //获取密码

                                    Constant.register = true;  //查看数据库中是否存在该账户
                                } else {
                                    emailText.setText("验证码输入错误");
                                    emailText.setFont(new Font("Serif", Font.BOLD, 20));
                                    emailText.setForeground(new Color(161, 19, 19));
                                    new Thread() {
                                        @SneakyThrows
                                        @Override
                                        public void run() {
                                            PlaySound.play("sound/error.mp3");
                                        }
                                    }.start();
                                }
                            } else {
                                emailText.setText("邮箱格式错误");
                                emailText.setFont(new Font("Serif", Font.BOLD, 20));
                                emailText.setForeground(new Color(161, 19, 19));
                                new Thread() {
                                    @SneakyThrows
                                    @Override
                                    public void run() {
                                        PlaySound.play("sound/error.mp3");
                                    }
                                }.start();
                            }
                        }
                    }
                }
            });

            sendEmailLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String email = emailText.getText();  //储存邮箱
                    if (email.matches("[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}")) {  //用正则表达式判断邮箱格式是否正确
                        try {
                            sendEmail sendEmail = new sendEmail();
                            //设置要发送的邮箱
                            sendEmail.setReceiveMailAccount(email);
                            verrify = sendEmail.Send();
                            emailText.setText("验证码发送成功,清在此输入验证码");
                            emailText.setFont(new Font("Serif", Font.BOLD, 17));
                            emailText.setForeground(new Color(62, 171, 159));
                        } catch (Exception a) {
                            emailText.setText("验证码发送失败");
                            emailText.setFont(new Font("Serif", Font.BOLD, 20));
                            emailText.setForeground(new Color(161, 19, 19));
                            new Thread() {
                                @SneakyThrows
                                @Override
                                public void run() {
                                    PlaySound.play("sound/error.mp3");
                                }
                            }.start();
                        }


                    } else {
                        emailText.setText("邮箱格式错误");
                        emailText.setFont(new Font("Serif", Font.BOLD, 20));
                        emailText.setForeground(new Color(161, 19, 19));
                        new Thread() {
                            @SneakyThrows
                            @Override
                            public void run() {
                                PlaySound.play("sound/error.mp3");
                            }
                        }.start();
                    }
                }
            });
        }


        background.add(Rbut1);  //加入最小化按钮
        background.add(Rbut2);
        if (isAlive)
            background.add(sign);  //加入标志图片
        if (!isAlive)
            background.add(load);  //加入标志图片
        background.add(loadIn);  //加入标志图片
        background.add(loginLabel);  //加入登录按钮
        background.add(usernameLabel);  //加入账号框
        background.add(passwordLabel);  //加入密码框
        background.add(registerLabel); //加入注册按钮
        background.add(wrongMessage);  //加入错误信息
        background.add(plate);  //加入品牌名
        plate.setCenter(left.getWidth() + 1600);
        background.add(remember);   //加入按钮
        background.add(switchMode);   //加入按钮
        background.add(toolMenuItem1);   //加入图像1
        background.add(toolMenuItem2);   //加入图像2
        background.add(toolMenuMessage1);   //加入信息1
        background.add(toolMenuMessage2);   //加入信息2
        background.add(toolBar);  //加入工具栏
        background.add(toolMenu);
        background.add(right);  //加入右半部分
        background.add(registerbgLabel);  //加入背景
        background.add(left);  //加入左半部分


        new Thread(() -> {//因为方法中有阻塞，所以以线程方式开启
            setPicture(left);  //左半部分加入图片,要动态图
        }).start();
        background.setIconImage(ImageIO.read(Resources.getResourceAsStream("login/sign.png")));  //加入小图标


        left.setBounds(0, 85, 1380, 563);  //设置左半部分位置
        right.setBounds(630, 17, 800, 843);  //设置右半部分位置
        Rbut1.setBounds(right.getWidth() + 415, 60, 25, 25);  //设置小按钮位置
        Rbut2.setBounds(right.getWidth() + 450, 60, 25, 25);
        wrongMessage.setCenter(2039);
        if (isAlive)
            sign.setBounds(right.getX() + right.getWidth() / 2 - 73, 122, icon.getIconWidth(), icon.getIconHeight());
        if (!isAlive)
            load.setBounds(right.getX() + right.getWidth() / 2 - 79, 95, icon.getIconWidth() + 35, icon.getIconHeight() + 35);
        loadIn.setBounds(right.getX() + right.getWidth() / 2 - 81, 95, icon.getIconWidth() + 35, icon.getIconHeight() + 35);
//        load.setBounds(0 ,0, load.getWidth()+230, load.getHeight()+230);
        loginLabel.setBounds(right.getWidth() - 25, 490, loginButtonIcon.getIconWidth() + 30, loginButtonIcon.getIconHeight());
        usernameLabel.setBounds(right.getWidth() - 2, 290, text.getIconWidth(), text.getIconHeight());
        passwordLabel.setBounds(right.getWidth() - 2, 380, text.getIconWidth(), text.getIconHeight());
        registerLabel.setBounds(right.getWidth() + 172, 620, registerIcon.getIconWidth(), registerIcon.getIconHeight());
        toolBar.setBounds(left.getWidth() - 216, 60, toolBarIcon.getIconWidth(), toolBarIcon.getIconHeight());
        toolMenu.setBounds(left.getWidth() - 266, 93, 128, 90);
        remember.setBounds(left.getWidth() - 260, 98, 0, 0);
        switchMode.setBounds(left.getWidth() - 260, 138, 0, 0);
        toolMenuMessage1.setCenter(2400);
        toolMenuMessage2.setCenter(2400);
//        leftBack.setBounds(50, 50, 150, 60);
//        leftOpen.setBounds(50, 50, leftOpenIcon.getIconWidth()+150, leftOpenIcon.getIconHeight()+150);

        Thread.sleep(500); //给加载图片的时间

        background.setVisible(true);  //窗口可视化

        //透明开启效果
        new Thread() {  //开启窗口动画
            @SneakyThrows
            @Override
            public void run() {
                float MAXTRANS = 0;  //透明度
                while (MAXTRANS <= 1.0) {
                    Thread.sleep(3);
                    AWTUtilities.setWindowOpacity(background, MAXTRANS);  //半透明
                    MAXTRANS += 0.01;
                }
                AWTUtilities.setWindowOpacity(background, 1);  //半透明
            }
        }.start();


    }

    /**
     * 函数式编程，让客户端访问此类的值
     *
     * @param login
     */
    public static void checkLogin(Consumer<String[]> login) {
        login.accept(new String[]{usernameMessage, passwordMessage1});
    }

    /**
     * 为登录界面添加背景图片，用的是JFrame下的JLayeredPane
     *
     * @param left 要添加图片的容器
     */
    @SneakyThrows
    public void setPicture(JLabel left) {
        ImageIcon Icon1;  //九张背景图片
        ImageIcon Icon2;
        ImageIcon Icon3;
        ImageIcon Icon4;
        ImageIcon Icon5;
        ImageIcon Icon6;
        ImageIcon Icon7;
        ImageIcon Icon8;
        ImageIcon Icon9;
        ImageIcon[] icons;//图片集
        try (InputStream icon1 = Resources.getResourceAsStream("newPicture/picture1.png");
             InputStream icon2 = Resources.getResourceAsStream("newPicture/picture2.png");
             InputStream icon3 = Resources.getResourceAsStream("newPicture/picture3.png");
             InputStream icon4 = Resources.getResourceAsStream("newPicture/picture4.png");
             InputStream icon5 = Resources.getResourceAsStream("newPicture/picture5.png");
             InputStream icon6 = Resources.getResourceAsStream("newPicture/picture6.png");
             InputStream icon7 = Resources.getResourceAsStream("newPicture/picture7.png");
             InputStream icon8 = Resources.getResourceAsStream("newPicture/picture8.png");
             InputStream icon9 = Resources.getResourceAsStream("newPicture/picture9.png")) {
            Icon1 = new ImageIcon(ImageIO.read(icon1));
            Icon2 = new ImageIcon(ImageIO.read(icon2));
            Icon3 = new ImageIcon(ImageIO.read(icon3));
            Icon4 = new ImageIcon(ImageIO.read(icon4));
            Icon5 = new ImageIcon(ImageIO.read(icon5));
            Icon6 = new ImageIcon(ImageIO.read(icon6));
            Icon7 = new ImageIcon(ImageIO.read(icon7));
            Icon8 = new ImageIcon(ImageIO.read(icon8));
            Icon9 = new ImageIcon(ImageIO.read(icon9));
            icons = new ImageIcon[]{Icon1, Icon2, Icon3, Icon4,Icon5,Icon6,Icon7,Icon8,Icon9}; //放入集合
        }
        JLabel background = new JLabel(Icon1);  //背景标签
        JLabel topBack = new JLabel();  //上半部分
        JLabel top = new JLabel(icons[1]);  //上半部分
        JLabel bottomBack = new JLabel(); //  //下半部分
        JLabel bottom = new JLabel(icons[1]); //  //下半部分
        left.add(topBack);
        left.add(bottomBack);
        bottomBack.add(bottom);
        topBack.add(top);
        left.add(background);
        background.setBounds(0, 0, Icon1.getIconWidth(), Icon1.getIconHeight());
        topBack.setBounds(0, -Icon1.getIconHeight(), Icon1.getIconWidth(), Icon1.getIconHeight());
        top.setBounds(0, Icon1.getIconHeight(), Icon1.getIconWidth(), Icon1.getIconHeight());
        bottomBack.setBounds(0, Icon1.getIconHeight(), Icon1.getIconWidth(), Icon1.getIconHeight());  //背景容器
        bottom.setBounds(0, -Icon1.getIconHeight(), Icon1.getIconWidth(), Icon1.getIconHeight());
        if (switchFlag) {
            System.out.println("上面");
            ANIMATION_KEEP_ON = !ANIMATION_KEEP_ON;

        } else {
            System.out.println("下边");
            ANIMATION_KEEP_ON = !ANIMATION_KEEP_ON;
            Thread.sleep(1000);
            timer = new TimerTask() {  //实现计时器类
                int index = 0;

                @SneakyThrows
                @Override
                public void run() {
                    if (!ANIMATION_KEEP_ON) {
                        while (true) {
                            Thread.sleep(1000);   //??
                            //如果不能走就卡住
                            if (ANIMATION_KEEP_ON)
                                break;
                        }
                    }
                    index++;
                    int All = Icon1.getIconHeight() / 2;
                    int Top11 = Icon1.getIconHeight();
                    int Top22 = -Icon1.getIconHeight();
                    int Bottom11 = -Icon1.getIconHeight();
                    int Bottom22 = Icon1.getIconHeight();
                    //每次先重置位置防止闪屏
                    top.setBounds(0, Top11, Icon1.getIconWidth(), Icon1.getIconHeight());
                    topBack.setBounds(0, Top22, Icon1.getIconWidth(), Icon1.getIconHeight());
                    bottom.setBounds(0, Bottom11, Icon1.getIconWidth(), Icon1.getIconHeight());
                    bottomBack.setBounds(0, Bottom22, Icon1.getIconWidth(), Icon1.getIconHeight());

                    top.setIcon(icons[index % 9]);  //设置新图片
                    bottom.setIcon(icons[index % 9]);
                    while ((All--) > 0) {  //每个卡片移动一半
                        Thread.sleep(6);  //动画间隔
                        //每次移动1
                        top.setBounds(0, --Top11, Icon1.getIconWidth(), Icon1.getIconHeight());
                        topBack.setBounds(0, ++Top22, Icon1.getIconWidth(), Icon1.getIconHeight());
                        bottom.setBounds(0, ++Bottom11, Icon1.getIconWidth(), Icon1.getIconHeight());
                        bottomBack.setBounds(0, --Bottom22, Icon1.getIconWidth(), Icon1.getIconHeight());
                    }
                    background.setIcon(icons[(index) % 9]);  //设置背景图片
                    System.out.println("运行到这");
                }
            };
            Thread.sleep(4000);  //10秒后开始
            timerFather.schedule(this.timer, new Date(), 9000);  //16秒一换

        }
    }

    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {  //若点击事件是登录按钮
            usernameMessage = username.getText();  //获取账号密码
            passwordMessage1 = password.getText();

            Constant.login = true;  //登录
        } else if (e.getSource() == signButton) {  //若点击的是换头像按钮
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setApproveButtonText("选择");
            fileChooser.setDialogTitle("选择头像图片");
            int result = fileChooser.showOpenDialog(background);
            if (result == JFileChooser.APPROVE_OPTION) {  //当点击确定按钮
                File file = fileChooser.getSelectedFile();  //获取文件路径
                if (file.getPath().contains(".png") || file.getPath().contains(".jpg")) {
                    BufferedImage newIcon = ImageIO.read(file);
                    BufferedImage realIcon = ToBufferedImage.toBufferedImage(newIcon.getScaledInstance(signLabel.getWidth(), signLabel.getHeight(), 0));  //将图片改为合适的大小，并转化为BufferedImage
                    signLabel.setIcon(new ImageIcon(realIcon));  //更换图片
                    if (file.getPath().contains(".png"))
                        iconString = ToPicture.imageToString(realIcon, "png");
                    if (file.getPath().contains(".jpg"))
                        iconString = ToPicture.imageToString(realIcon, "jpg");
                } else {
                    signLabel.shake();
                    signMessage.setTextDynamic("只能为JPG或PNG文件", new Font("Serif", Font.BOLD, 14));
                    signMessage.setForeground(new Color(215, 27, 71, 205));
                    signMessage.setCenter(270);
                    signMessage.shake();
                    new Thread() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            PlaySound.play("sound/error.mp3");
                        }
                    }.start();

                }
            } else if (result == JFileChooser.CANCEL_OPTION) {
                signLabel.shake();  //按钮抖动
                new Thread() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        PlaySound.play("sound/error.mp3");
                    }
                }.start();
            } else {
                signLabel.shake();
                new Thread() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        PlaySound.play("sound/error.mp3");
                    }
                }.start();
            }

        }
    }

    /**
     * 注册完成后的第一个反馈
     */
    public static void registerFinish(String Username, String Password) {
        wrongMessage.setTextDynamic("注册成功√");
        registerLabelMessage1.setText("您的用户名为" + Username);
        loadIn.setColor(new Color(0, 0, 0, 0));
        username.setText(Username); //设置账号
        password.setText(Password);  //设置密码
        passwordMessage.setText("");  //清空提示
    }

    /**
     * 当已经存在该用户时调用
     */
    public static void registerAlready() {
        registerMessage.setText("已经存在该用户");
        registerLabel5.shake();
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
                float MAXTRANS = 1;  //透明度
                while (MAXTRANS >= 0) {
                    Thread.sleep(4);
                    AWTUtilities.setWindowOpacity(background, MAXTRANS);  //半透明
                    MAXTRANS -= 0.05;
                }
                AWTUtilities.setWindowOpacity(background, 1);  //半透明
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
                float MAXTRANS = 0;  //透明度
                while (MAXTRANS <= 1.0) {
                    if (iconified) {
                        AWTUtilities.setWindowOpacity(background, 0);  //半透明
                        return;
                    }
                    Thread.sleep(4);
                    if (iconified) {
                        AWTUtilities.setWindowOpacity(background, 0);  //半透明
                        return;
                    }
                    AWTUtilities.setWindowOpacity(background, MAXTRANS);  //半透明
                    MAXTRANS += 0.04;
                    if (iconified) {
                        AWTUtilities.setWindowOpacity(background, 0);  //半透明
                        return;
                    }
                }
                iconified = false;
                AWTUtilities.setWindowOpacity(background, 1);  //半透明
            }
        }.start();
    }

    public void register() {
        if (Objects.equals(registerText2.getText(), "") || Objects.equals(registerText2.getText(), "请输入账号")) {
            registerLabel2.shake();  //若未填入账号则抖动
            registerLabel5.shake(); //按钮抖动
            new Thread() {
                @SneakyThrows
                @Override
                public void run() {
                    PlaySound.play("sound/error.mp3");
                }
            }.start();
        } else if (Objects.equals(registerPassword3.getText(), "") || registerPassword3.getText().equals("请输入密码")) {
            registerLabel3.shake();
            registerLabel5.shake(); //按钮抖动
            new Thread() {
                @SneakyThrows
                @Override
                public void run() {
                    PlaySound.play("sound/error.mp3");
                }
            }.start();
        } else if (Objects.equals(registerPassword4.getText(), "") || registerPassword4.getText().equals("请再次输入密码")) {
            registerLabel4.shake();
            registerLabel5.shake(); //按钮抖动
            new Thread() {
                @SneakyThrows
                @Override
                public void run() {
                    PlaySound.play("sound/error.mp3");
                }
            }.start();
        } else {  //注册成功
            if (leftOpening) {  //若弹窗已经弹出
                String email = emailText.getText();  //储存邮箱
                if (email.matches("^\\S{6}$")) {  //若是六位随机数
                    if (email.equals(verrify)) {  //验证码验证成功
                        usernameMessage = registerText2.getText();  //获取账号
                        passwordMessage1 = registerPassword3.getText(); //获取密码

                        Constant.register = true;  //查看数据库中是否存在该账户

                    } else {
                        emailText.setText("验证码输入错误");
                        emailText.setForeground(new Color(161, 19, 19));
                    }
                } else {
                    emailText.setText("验证码应为6位");
                    emailText.setForeground(new Color(161, 19, 19));
                }
            } else {  //若未弹出则弹出弹框
                boolean[] LEFTISFINISH = {true};  //判断展开栏是否加载完动画
                label:
                {
                    if (LEFTISFINISH[0] && !leftOpening) {
                        leftOpening = true;
                        LEFTISFINISH[0] = false;  //动画未加载完
                        //储存四个内容的位置
                        int LOCATION1 = leftBack.getX();
                        int LOCATION2 = leftContent.getX();
                        int LOCATION3 = sendEmailLabel.getX();
                        int LOCATION4 = emailText.getX();
                        final int[] LOCATION = {0};  //当前位置
                        new Thread() {
                            @SneakyThrows
                            @Override
                            public void run() {
                                while (LOCATION[0] <= registerIcon1.getIconWidth() + 80 + 40) {
                                    Thread.sleep(2);
                                    leftBack.setBounds(LOCATION1 - LOCATION[0], leftBack.getY(), leftBack.getWidth(), leftBack.getHeight());
                                    leftContent.setBounds(LOCATION2 - LOCATION[0], leftContent.getY(), leftContent.getWidth(), leftContent.getHeight());
                                    sendEmailLabel.setBounds(LOCATION3 - LOCATION[0], sendEmailLabel.getY(), sendEmailLabel.getWidth(), sendEmailLabel.getHeight());
                                    emailText.setBounds(LOCATION4 - LOCATION[0], emailText.getY(), emailText.getWidth(), emailText.getHeight());

                                    LOCATION[0] += 4;
                                }
                                LEFTISFINISH[0] = true;
                            }
                        }.start();
                        break label;
                    }
                    if (LEFTISFINISH[0] && leftOpening) {
                        leftOpening = false;
                        LEFTISFINISH[0] = false;  //动画未加载完
                        final int[] LOCATIONON = {0};  //当前位置
                        //储存四个内容的位置
                        int LOCATION1 = leftBack.getX();
                        int LOCATION2 = leftContent.getX();
                        int LOCATION3 = sendEmailLabel.getX();
                        int LOCATION4 = emailText.getX();
                        new Thread() {
                            @SneakyThrows
                            @Override
                            public void run() {
                                while (LOCATIONON[0] <= registerIcon1.getIconWidth() + 80 + 40) {
                                    Thread.sleep(2);
                                    leftBack.setBounds(LOCATION1 + LOCATIONON[0], leftBack.getY(), leftBack.getWidth(), leftBack.getHeight());
                                    leftContent.setBounds(LOCATION2 + LOCATIONON[0], leftContent.getY(), leftContent.getWidth(), leftContent.getHeight());
                                    sendEmailLabel.setBounds(LOCATION3 + LOCATIONON[0], sendEmailLabel.getY(), sendEmailLabel.getWidth(), sendEmailLabel.getHeight());
                                    emailText.setBounds(LOCATION4 + LOCATIONON[0], emailText.getY(), emailText.getWidth(), emailText.getHeight());

                                    //registerIcon1.getIconWidth()+80

                                    LOCATIONON[0] += 4;
                                }
                                LEFTISFINISH[0] = true;
                            }
                        }.start();
                        break label;

                    }
                    LEFTISFINISH[0] = true;
                }
            }
        }
    }


}
