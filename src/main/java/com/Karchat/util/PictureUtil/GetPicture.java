package com.Karchat.util.PictureUtil;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 将数据库中的图像字符串解析成BufferedImage
 */
public class GetPicture {
    public static String fileformat = "png";
    public static String fileNameFormat = "yyyy-MM-dd_HH-mm-ss";

    // 将字符串格式的图片转换为图片并保存
    public static BufferedImage stringToImage(String string) {
        if (string.contains(",")) {
            // 这里没有自带的那个分割方法，原因是分割速度没有这个快，有人考证在分割字符长度很大的情况下，系统的分割方法容易造成内存溢出。
            // 还有subString方法，不知道最新版本的jdk改了源码了么
            String[] imagetemp = split(string, ",");
            byte[] image = new byte[imagetemp.length];
            for (int i = 0; i < imagetemp.length; i++) {
                image[i] = Byte.parseByte(imagetemp[i]);
            }
            return saveImage(image);
        } else {
            System.out.println("不能解析");
            return null;
        }
    }

    // 将byte[] 转换为BufferedImage
    private static BufferedImage readImage(byte[] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        return ImageIO.read(bais);
    }

    // 保存图片
    @SneakyThrows
    public static BufferedImage saveImage(byte[] imgages) {
        BufferedImage bis = readImage(imgages);
        return bis;
    }

    // 分割字符串
    public static String[] split(String s, String token) {
        if (s == null)
            return null;
        if (token == null || s.length() == 0)
            return new String[] { s };
        int size = 0;
        String[] result = new String[4];
        while (s.length() > 0) {
            int index = s.indexOf(token);
            String splitOne = s;
            if (index > -1) {
                splitOne = s.substring(0, index);
                s = s.substring(index + token.length());
            } else {
                s = "";
            }
            if (size >= result.length) {
                String[] tmp = new String[result.length * 2];
                System.arraycopy(result, 0, tmp, 0, result.length);
                result = tmp;
            }
            if (splitOne.length() > 0) {
                result[size++] = splitOne;
            }
        }
        String[] tmp = result;
        result = new String[size];
        System.arraycopy(tmp, 0, result, 0, size);
        return result;
    }
}