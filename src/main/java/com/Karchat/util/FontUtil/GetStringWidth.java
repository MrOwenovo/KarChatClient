package com.Karchat.util.FontUtil;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

/**
 * 获得字符串的字体宽度长度
 */
public class GetStringWidth {
    private static AffineTransform atf = new AffineTransform();  //获得仿射变换

    private static FontRenderContext frc = new FontRenderContext(atf, true,
            true);  //获得文本内容容器

    public static int getStringHeight(String str, Font font) {  //获取字符串在该字体下的宽度
        if (str == null || str.isEmpty() || font == null) {
            return 0;
        }
        return (int) font.getStringBounds(str, frc).getHeight();

    }

    public static int getStringWidth(String str, Font font) { //获取字符串在该字体下的长度
        if (str == null || str.isEmpty() || font == null) {
            return 0;
        }
        return (int) font.getStringBounds(str, frc).getWidth();
    }

}
