package com.Karchat.util.FontUtil;

import java.awt.*;

public class GetAllFont {
    public static void main(String[] args) {
        //取得本地的绘图设备和字体的集合
        GraphicsEnvironment eq = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //取得全部可用字体
        String[] fontNames = eq.getAvailableFontFamilyNames();
        //循环输出所有内容
        for (int i = 0; i < fontNames.length; i++) {
            System.out.println(fontNames[i]);
        }

    }
}
