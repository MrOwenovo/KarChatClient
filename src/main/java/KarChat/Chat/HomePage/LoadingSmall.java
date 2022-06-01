package KarChat.Chat.HomePage;

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
public class LoadingSmall extends JPanel {

    private static final long serialVersionUID = 1551571546L;

    private Color color=Color.RED;
    private Timer timer;
    private int delay;  //转动间隔
    private int startAngle;  //设置圆角
    private int arcAngle = 0;
    private int orientation;  //转动方向

    public static final int CLOCKWISE = 0;  //顺时针
    public static final int ANTICLOCKWISE = 1;  //逆时针

    public LoadingSmall() {
        this.delay = 10;
        this.orientation = CLOCKWISE;
        init();
    }

    public LoadingSmall(int delay) {
        this.delay = delay;
        this.orientation = CLOCKWISE;
        init();
    }

    public LoadingSmall(int delay, int orientation) {
        this.delay = delay;
        this.orientation = orientation;
        init();
    }

    @Override
    public void show() {
        this.timer.start();
    }

    /**
     * @param orientation	set the direction of rotation
     *
     * @beaninfo
     *        enum: CLOCKWISE LodingPanel.CLOCKWISE
     *        		ANTICLOCKWISE LodingPanel.ANTICLOCKWISE
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
    protected void paintComponent(Graphics g) {
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
        g2d.setColor(new Color(239, 238, 238));
        g2d.drawArc(width / 2 - 25-25, height / 2 - 5-25, 20 + 60-60, 20 + 60-60, 0, 360);
        g2d.setColor(color);
        g2d.fillArc(width / 2 - 25-25, height / 2 - 5-25, 20 + 60-60, 20 + 60-60, startAngle, arcAngle);
        g2d.setColor(new Color(239, 238, 238));
        g2d.fillArc(width / 2 - 20-25, height / 2 - 0-25, 20 + 50-60, 20 + 50-60, 0, 360);
        g2d.dispose();
    }

    private class ReboundListener implements ActionListener {

        private int o = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (startAngle < 360) {
                //控制每个DELAY周期旋转的角度，+ 为逆时针  - 为顺时针
                switch (orientation) {
                    case CLOCKWISE:
                        startAngle = startAngle + 1;
                        break;
                    case ANTICLOCKWISE:
                        startAngle = startAngle - 1;
                        break;
                    default:
                        startAngle = startAngle + 1;
                        break;
                }
            } else {
                startAngle = 0;
            }

            if (o == 0) {
                if (arcAngle >= 359) {
                    o = 1;
                    orientation = ANTICLOCKWISE;
                }else {
                    if (orientation == CLOCKWISE) {
                        arcAngle += 1;
                    }
                }
            }else {
                if (arcAngle <= 1) {
                    o = 0;
                    orientation = CLOCKWISE;
                }else {
                    if (orientation == ANTICLOCKWISE) {
                        arcAngle -= 1;
                    }
                }
            }

            repaint();
        }
    }
}
