package com.Karchat.util.ComponentUtil.Loading;


import com.Karchat.util.ColorUtil.ChangeToColor;
import com.Karchat.util.ComponentUtil.Label.RadioJLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 利用Timer(计时器)更改变量X的值和重写paintComponent方法实现动画旋转效果
 * 设定Timer的delay和定义一个ActionListener
 * 在paintComponent方法中绘制一个图形(圆)再更改画笔颜色去填充，填充时根据变量X设定填充开始位置(角度)
 * 在ActionListener事件中不断修改变量X的值和调用重写的paintComponent方法
 */
public class LoadingBack extends RadioJLabel {

    private static final long serialVersionUID = 1551571546L;

    private Color color = Color.RED;
    private Timer timer;
    private int delay;  //转动间隔
    private int startAngle=-10;  //设置圆角
    private int arcAngle = 0;
    private int orientation;  //转动方向
    private int WIDTH = 130;  //宽度


    public static final int CLOCKWISE = 0;  //顺时针
    public static final int ANTICLOCKWISE = 1;  //逆时针

    public LoadingBack() {
        this.delay = 1;
        this.orientation = CLOCKWISE;
        init();
    }

    public LoadingBack(int delay) {
        this.delay = delay;
        this.orientation = CLOCKWISE;
        init();
    }

    public LoadingBack(int width, Color color) {
        this.WIDTH = width;
        this.delay = 1;
        this.orientation = CLOCKWISE;
        this.color = color;
        if (WIDTH > 110 && WIDTH <= 130)
            this.setArc(WIDTH, WIDTH);
        if (WIDTH > 130 && WIDTH <= 170)
            this.setArc(WIDTH - 10, WIDTH - 10);
        if (WIDTH > 170 && WIDTH <= 210)
            this.setArc(WIDTH - 10, WIDTH - 10);
//
        init();
    }

    public LoadingBack(int delay, int orientation) {
        this.delay = delay;
        this.orientation = orientation;
        init();
    }

    @Override
    public void show() {
        this.timer.start();
    }

    /**
     * @param orientation set the direction of rotation
     * @beaninfo enum: CLOCKWISE LodingPanel.CLOCKWISE
     * ANTICLOCKWISE LodingPanel.ANTICLOCKWISE
     */
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    private void init() {
        this.timer = new Timer(delay, new ReboundListener());
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawArc(g);
    }

    private void drawArc(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        //抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        //设置画笔颜色
//        g2d.setColor(Color.WHITE);
        g2d.setColor(ChangeToColor.getColorFromHex("#333333"));
        g2d.drawArc(width / 2 - WIDTH / 2, height / 2 - WIDTH / 2, WIDTH, WIDTH, 0, 360);
        g2d.setColor(color);
        if (WIDTH > 110 && WIDTH <= 130)
            g2d.fillArc(width / 2 - WIDTH / 2, height / 2 - WIDTH / 2, WIDTH, WIDTH, startAngle, arcAngle);
        if (WIDTH > 130 && WIDTH <= 170)
            g2d.fillArc(width / 2 - WIDTH / 2, height / 2 - WIDTH / 2, WIDTH, WIDTH, startAngle + 30, arcAngle);
        if (WIDTH > 170 && WIDTH <= 210)
            g2d.fillArc(width / 2 - WIDTH / 2, height / 2 - WIDTH / 2, WIDTH, WIDTH, startAngle + 60, arcAngle);

        g2d.setColor(ChangeToColor.getColorFromHex("#333333"));
//        g2d.setColor(Color.WHITE);
        g2d.fillArc(width / 2 - WIDTH / 2 + 5, height / 2 - WIDTH / 2 + 5, WIDTH - 10, WIDTH - 10, 0, 360);
        g2d.dispose();
    }

    private class ReboundListener implements ActionListener {

        private int o = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
//            if (startAngle < 360) {
            //控制每个DELAY周期旋转的角度，+ 为逆时针  - 为顺时针
                    if (WIDTH > 110 && WIDTH <= 130) {
                        startAngle = startAngle - 5;
                        arcAngle = 100;
                    }
                    if (WIDTH > 130 && WIDTH <= 170) {
                        startAngle = startAngle - 4;
                        arcAngle = 100;
                    }
                    if (WIDTH > 170 && WIDTH <= 210) {
                        startAngle = startAngle - 3;
                        arcAngle = 100;
                    }

            repaint();
        }
            }
        }
