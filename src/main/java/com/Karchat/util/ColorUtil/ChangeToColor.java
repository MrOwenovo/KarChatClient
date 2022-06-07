package com.Karchat.util.ColorUtil;

import java.awt.*;

/**
 * 把"#FFFFFF的颜色转化成Color"
 */
public class ChangeToColor {
    public static Color getColorFromHex(String hex) {
        if (hex == null || hex.length() != 7) {  //判断输入的长度是否正确
            try {
                throw new Exception("不能转换这种类型的颜色");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        int r = Integer.valueOf(hex.substring(1, 3), 16);  //按16进制转化对象
        int g = Integer.valueOf(hex.substring(3, 5), 16);
        int b = Integer.valueOf(hex.substring(5), 16);
        return new Color(r, g, b);
    }
}
