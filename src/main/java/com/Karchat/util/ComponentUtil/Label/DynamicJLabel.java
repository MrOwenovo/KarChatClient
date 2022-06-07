package com.Karchat.util.ComponentUtil.Label;


import com.Karchat.util.FontUtil.GetStringWidth;

import java.awt.*;

/**
 * 动态JLabel,根据文字长度变化大小
 */
public class DynamicJLabel extends ShakeLabel {
    Font font;
    boolean CENTER=false;  //判断是否居中
    int FRAMELENGTH;  //储存容器的长度
    public DynamicJLabel(String message, Font font, int y) {
        super(message); //设置信息
        this.setFont(font);
        this.setBounds(0,y, GetStringWidth.getStringWidth(message,font)+10, GetStringWidth.getStringHeight(message,font)+10);  //设置大小
        this.font=font;
    }

    /**
     * 修改内容，默认字体不变
     * @param message  内容
     */
    public void setTextDynamic(String message){
        this.setText(message);
        this.setBounds(this.getX(),this.getY(),GetStringWidth.getStringWidth(message,font)+10, GetStringWidth.getStringHeight(message,font)+10);  //设置大小
        if(CENTER) this.setCenter(FRAMELENGTH);
    }


    /**
     * 修改内容并修改字体
     * @param message 要修改的内容
     * @param font 要修改的字体
     */
    public void setTextDynamic(String message, Font font) {
        int x=this.getX();
        int y=this.getY();
        this.font=font;
        this.setText(message);
        this.setFont(font);
        this.setBounds(x,y,GetStringWidth.getStringWidth(message,font)+10, GetStringWidth.getStringHeight(message,font)+10);  //设置大小
        if(CENTER) this.setCenter(FRAMELENGTH);
    }

    /**
     * 让组件居中
     * @param framLength  容器的长度
     */
    public void setCenter(int framLength) {
        int marge = (framLength - this.getWidth()) / 2; //计算边框
        this.setBounds(marge, this.getY(), this.getWidth(), this.getHeight());
        FRAMELENGTH = framLength;
        CENTER = true;
    }

    /**
     * 取消居中
     */
    public void setNotCenter(){
        CENTER = false;
    }

    /**
     * 设置起始坐标
     * @param x 起始x坐标
     * @param y 起始y坐标
     */
    public void setBoundsDynamic(int x, int y) {
        this.setBounds(x,y,this.getWidth(),this.getHeight());
    }

}
