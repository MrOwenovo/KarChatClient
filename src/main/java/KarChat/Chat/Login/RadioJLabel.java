package KarChat.Chat.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;

/**
 * 通过按钮调用printComponent方法完成的圆角矩形
 */
public class RadioJLabel extends JLabel implements MouseListener , MouseMotionListener {
    private Shape shape = null;  //形状

    private Color put;  //放置时颜色

    private int arcWidth = 20;  //圆角水平圆度,默认20

    private int arcHeight = 20; //圆角垂直圆度,默认20

    public RadioJLabel(String s) {
        super(s);
        addMouseListener(this);
    }
    public RadioJLabel(ImageIcon icon) {
        super(icon);
        addMouseListener(this);
    }

    public void setColor(Color p) {
        put = p;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    /**
     * 点击按钮时调用,fillRoundRect是打印图形大小和圆角,arcWidth控制圆角,height等控制宽度
     * @param g 要打印的图像
     */
    public void paintComponent(Graphics g) {
        g.setColor(put);
        //填充圆角矩形区域 也可以为其它的图形
        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1,
                arcWidth, arcHeight);
        //必须放在最后 否则画不出来
        super.paintComponent(g);
    }

    /**
     * 画圆角按钮边框
     */
    public void paintBorder(Graphics g) {
        //画边界区域
        g.setColor(put);
        g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1,
                arcWidth, arcHeight);
    }

    /**
     * 若上面改变arcw和arch，这里也要改
     * @param x 默认0
     * @param y 默认0
     * @return 返回shape调用contains
     */
    public boolean contains(int x, int y) {
        //判断点（x,y）是否在按钮内
        if (shape == null || !(shape.getBounds().equals(getBounds()))) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(),
                    arcWidth, arcHeight);
        }
        return shape.contains(x, y);
    }

    /**
     * 设置按钮的水平圆角和垂直圆角
     * @param arcWidth 水平圆角角度
     * @param arcHeight 垂直圆角角度
     */
    public void setArc(int arcWidth, int arcHeight) {
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
    }

    /**
     * 获得当前颜色
     */
    public Color getColor() {
        return put;
    }
}