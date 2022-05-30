package KarChat.Chat.Helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * 将BufferedImage图片转化成字符串储存在数据库中
 */
public class ToPicture {
    private static Log log = LogFactory.getLog(ToPicture.class);
    // 根据图片地址将图片转换为字符串类型的数据
    public static String imageToString(BufferedImage image,String format) {
        StringBuffer sb2 = new StringBuffer();
        BufferedImage image1 = image;
        byte[] img = getBytes(image1,format);

        for (int i = 0; i < img.length; i++) {
            if (sb2.length() == 0) {
                sb2.append(img[i]);
            } else {
                sb2.append("," + img[i]);
            }
        }
        return sb2.toString();

    }
    // 将BufferImage 转换为字节数组
    private static byte[] getBytes(BufferedImage image, String format) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            if (format.equalsIgnoreCase("png"))
                ImageIO.write(image, "PNG", baos);
            if (format.equalsIgnoreCase("jpg"))
                ImageIO.write(image, "JPG", baos);
        } catch (Exception e) {
            log.info(e);
        }
        return baos.toByteArray();
    }

}