package com.Karchat.util.ComponentUtil.Frame;


import com.Karchat.util.ComponentUtil.Button.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 自定义无边框容器，需要外界调用:
 * Frameless.setDefaultLookAndFeelDecorated(true);  //取消Windows本地外观
 * AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double(30.0D, 30.0D, this.getWidth(), this.getHeight(), 26.0D, 26.0D));  //将容器变为圆角
 */
public class Frameless extends JFrame {
    private int xOld = 0;  //用于处理拖动事件，表示鼠标按下时的坐标，相对于JFrame
    private int yOld = 0;

    /**
     * 如果flag为true，加入最大化最小化按钮
     */
    public Frameless(int width, int height, boolean flag) {
        super("");  //设置名称
//        this.setUndecorated(true);
//        AWTUtilities.setWindowOpacity(this, 0.8f);  //半透明
//        try {  //优化swing边界
//            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
        this.setUndecorated(true);  //不要边框
        this.setBackground(new Color(0,0,0,0));
//        this.getContentPane().setBackground(Color.DARK_GRAY);  //设置背景
        this.setLayout(null);  //绝对定位
        this.setResizable(false);  //不可扩增
        this.setLocationRelativeTo(null);  //居中
        this.setSize(width, height);  //设置大小
        if (flag) {  //如果标志位为true,加入最小化和最大化按钮
            this.setRadioButton();
        }
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(1);
            }
        });  //加入窗口监听;
        //处理鼠标点击事件
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xOld = e.getX();  //存储点击时的坐标
                yOld = e.getY();
            }
        });
        //处理鼠标拖拽事件
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int xOnScreen = e.getXOnScreen(); //获取当前鼠标拖拽时x坐标
                int yOnScreen = e.getYOnScreen(); //获取当前鼠标拖拽时y坐标
                int xx = xOnScreen - xOld;  //移动差值
                int yy = yOnScreen - yOld;
                Frameless.this.setLocation(xx,yy);  //移动窗口
            }
        });
//        //设置圆角容器
//        Frameless.setDefaultLookAndFeelDecorated(true);  //取消Windows本地外观
//        //给frame容器设立圆角
//        AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double(0.0D, 0.0D, this.getWidth(), this.getHeight(), 32.0D, 32.0D));


    }

    /**
     * 无参构造
     */
    public Frameless() {
        this(800,400,false);
    }

    /**
     * 绑定某一个容器，一起移动
     * @param brother
     */
    public void bind(JFrame brother) {
        //处理鼠标拖拽事件
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int xOnScreen = e.getXOnScreen(); //获取当前鼠标拖拽时x坐标
                int yOnScreen = e.getYOnScreen(); //获取当前鼠标拖拽时y坐标
                int xx = xOnScreen - xOld;  //移动差值
                int yy = yOnScreen - yOld;
                Frameless.this.setLocation(xx,yy);  //移动窗口
            }
        });
    }
    /**
     * 给容器添加右上角的最小化和关闭
     */
    private void setRadioButton() {
        //创建右上角圆按钮，并添加监听器
        RoundButton Rbut1 = new RoundButton("", new Color(58, 124, 243, 190), new Color(92, 143, 236, 221),new Color(132, 171, 243, 181)) {
            @Override
            public void mouseClicked(MouseEvent e) {
                Frameless.this.setExtendedState(JFrame.ICONIFIED);
            }

        };
        RoundButton Rbut2 = new RoundButton("", new Color(243, 58, 101, 192), new Color(238, 70, 109, 189),new Color(252, 108, 141, 189)){
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(1);
            }

        };
        Rbut1.setBounds(Frameless.this.getWidth() - 80,  20, 20, 20);
        Rbut2.setBounds(Frameless.this.getWidth() - 50, 20, 20, 20);
        Frameless.this.add(Rbut1);
        Frameless.this.add(Rbut2);

    }
}
