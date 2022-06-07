package com.Karchat.util.ComponentUtil.Label;

import javax.swing.*;
import java.awt.*;

/**
 * 背景带图片和文字的标签
 */
public class RadioTextJLabel extends RadioJLabel {
    private  Color color = new Color(124, 122, 122);
    private  Font font = new Font("Serif", Font.BOLD, 18);
    private  int Y = this.getHeight()-80;
    private  String text ;
    private int Width;
    private int Height;
    private DynamicJLabel Text;
    private RadioJLabel context;


    public RadioTextJLabel(String text,int Width,int Height) {
        super("");
        this.text = text;
        this.setColor(new Color(166,163,163));
        this.setSize(Width,Height);
        Text = new DynamicJLabel(text, font,  Y);
        Text.setCenter(this.getWidth());  //居中下侧

        this.add(Text);  //加入文本

    }

    public RadioTextJLabel(String text, ImageIcon icon,int Width,int Height) {
        super("");
        this.text = text;
        this.setSize(Width,Height);
        this.setColor(new Color(215, 210, 210));
        Text = new DynamicJLabel(text, font, Y);
        //加入图片
        Text.setCenter(this.getWidth()+5);
        context = new RadioJLabel(icon);

        this.add(Text);
        this.add(context);

        context.setBounds(10,10,125,125);
    }

    /**
     * 返回其中图片内容
     * @return
     */
    public RadioJLabel getIconContent() {
        return context;
    }

    /**
     * 修改颜色
     * @param color
     */
    public void setRadioColor(Color color) {
        this.setColor(color);
    }

    /**
     * 修改字体
     * @param font
     */
    public void setRadioFont(Font font) {
        Text.setTextDynamic(text,font);
    }

    /**
     * 修改文本Y值
     * @param Y
     */
    public void setTextY(int Y) {
        Text.setBoundsDynamic(Text.getX(),Y);
    }

    /**
     * 修改文本内容
     */
    public void setRadioText(String text) {
        this.text = text;
        Text.setTextDynamic(text);
    }

    /**
     * 修改外标签圆角
     * @param arc
     */
    public void setArcOut(int arc) {
        this.setArc(arc,arc);
    }

    /**
     * 修改内标签圆角
     * @param arc
     */
    public  void setArcIn(int arc) {
        context.setArc(arc,arc);
    }

}