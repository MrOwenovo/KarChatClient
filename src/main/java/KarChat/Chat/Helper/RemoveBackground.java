package KarChat.Chat.Helper;

import java.awt.Graphics;

import java.awt.Graphics2D;

import java.awt.GraphicsConfiguration;

import java.awt.GraphicsDevice;

import java.awt.GraphicsEnvironment;

import java.awt.HeadlessException;

import java.awt.Image;

import java.awt.Transparency;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;

import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;



public class RemoveBackground {



    public static byte[] transferAlpha(Image image) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {

            ImageIcon imageIcon = new ImageIcon(image);

            BufferedImage bufferedImage = new BufferedImage(imageIcon

                    .getIconWidth(), imageIcon.getIconHeight(),

                    BufferedImage.TYPE_4BYTE_ABGR);

            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();

            g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon

                    .getImageObserver());

            int alpha = 0;

            for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage

                    .getHeight(); j1++) {

                for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage

                        .getWidth(); j2++) {

                    int rgb = bufferedImage.getRGB(j2, j1);



                    int R =(rgb & 0xff0000 ) >> 16 ;

                    int G= (rgb & 0xff00 ) >> 8 ;

                    int B= (rgb & 0xff );

                    //if(((255-R)<30) && ((255-G)<30) && ((255-B)<30)){ //去除白色背景；

                    if(((255-R)>160) && ((255-G)>160) && ((255-B)>160)){//去除黑色背景——dlgcy；

                        rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);

                    }

                    bufferedImage.setRGB(j2, j1, rgb);

                }

            }

            g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());

            //ImageIO.write(bufferedImage, "png", new File("d:/test.png.png"));

            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

        } catch (Exception e) {

        }finally{

        }



        return byteArrayOutputStream.toByteArray();

    }







    //byte[] ------>BufferedImage

    public static BufferedImage ByteToBufferedImage(byte[] byteImage) throws IOException{

        ByteArrayInputStream in = new ByteArrayInputStream(byteImage);

        BufferedImage buffImage = ImageIO.read(in);

        return buffImage;

    }



    //Image转换为BufferedImage；

    public static BufferedImage toBufferedImage(Image image) {

        if (image instanceof BufferedImage) {

            return (BufferedImage) image;

        }

        image = new ImageIcon(image).getImage();

        boolean hasAlpha = false;

        BufferedImage bimage = null;

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        try {

            int transparency = Transparency.OPAQUE;

            if (hasAlpha) {

                transparency = Transparency.BITMASK;

            }

            GraphicsDevice gs = ge.getDefaultScreenDevice();

            GraphicsConfiguration gc = gs.getDefaultConfiguration();

            bimage = gc.createCompatibleImage(image.getWidth(null),

                    image.getHeight(null), transparency);

        } catch (HeadlessException e) {

        }

        if (bimage == null) {

            int type = BufferedImage.TYPE_INT_RGB;

            if (hasAlpha) {

                type = BufferedImage.TYPE_INT_ARGB;

            }

            bimage = new BufferedImage(image.getWidth(null),

                    image.getHeight(null), type);

        }

        Graphics g = bimage.createGraphics();

        g.drawImage(image, 0, 0, null);

        g.dispose();

        return bimage;

    }



}