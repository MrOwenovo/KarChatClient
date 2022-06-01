package KarChat.Chat.Helper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RemoveBlack {
    public static BufferedImage resize(int faceWidth, BufferedImage srcImg) throws IOException {

        int imgWidth = 0;
        if(faceWidth>70 && faceWidth<120){
                imgWidth = faceWidth*7/8;
        }else if(faceWidth>60 && faceWidth<70){
                imgWidth = faceWidth*11/10;
        }else if(faceWidth < 60){
                imgWidth = faceWidth*6/5;
        }else{
                imgWidth = faceWidth*4/5;
        }
        int imgHeight = imgWidth*srcImg.getHeight()/srcImg.getWidth();

        //构建新的图片
        BufferedImage resizedImg = new BufferedImage(imgWidth,imgHeight,BufferedImage.TYPE_INT_RGB);
        //将原图放大或缩小后画下来:并且保持png图片放大或缩小后背景色是透明的而不是黑色
        Graphics2D resizedG = resizedImg.createGraphics();
        resizedImg = resizedG.getDeviceConfiguration().createCompatibleImage(imgWidth,imgHeight,Transparency.TRANSLUCENT);
        resizedG.dispose();
        resizedG = resizedImg.createGraphics();
        Image from = srcImg.getScaledInstance(imgWidth, imgHeight, srcImg.SCALE_AREA_AVERAGING);
        resizedG.drawImage(from, 0, 0, null);
        resizedG.dispose();

        return resizedImg;
    }
}
