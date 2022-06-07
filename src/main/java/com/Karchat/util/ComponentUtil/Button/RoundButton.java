package com.Karchat.util.ComponentUtil.Button;

import java.awt.*;

/**
 * 完成右上角圆按钮。需要调用处编写
 * 加了Border,实现抗锯齿
 * 设立圆按钮点前点后的颜色
 */
public class RoundButton extends RadioButton {

    @Override
    public boolean contains(int x, int y) {
        return super.contains(x, y);
    }

    @Override
    public void paintBorder(Graphics g) {
        super.paintBorder(g);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public RoundButton(String message, Color click, Color normal, Color put) {
        super(message);
        this.setBorder(new ThreeDimensionalBorder(Color.gray,200,3));
        this.setColor(click,normal,put);

    }
}