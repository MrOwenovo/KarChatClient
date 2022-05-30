package KarChat.Chat.Login.Button;


import KarChat.Chat.Login.DynamicJLabel;
import KarChat.Chat.Login.RadioJLabel;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.InputStream;
import java.util.function.Consumer;

public class ChooseBackButton extends RadioJLabel implements MouseMotionListener, MouseListener {
    ImageIcon icon;  //显示的图片
    ImageIcon iconCache; //图片缓存
    ImageIcon putIcon;  //显示的图片
    ImageIcon backIcon;  //显示的图片
    String resource;  //图片名字
    String put;  //放置图片
    String backResource;  //放置图片
    public static boolean click = false;  //默认没点击
    RadioJLabel back ;  //背景JLabel
    DynamicJLabel text; //按钮附近的文字
    /**
     * 创建一个图片单选按钮，传入图片名字，放置图片名字，背景图片名字,需要放在resources下
     * 设置默认位置左上角,默认大小为图片大小
     * 需要添加一个背景标签JLabel,新创建即可不需要任何操作，然后在容器中把这个加入到容器中，不然不能正常显示背景
     * @param resource
     */
    @SneakyThrows
    public ChooseBackButton(String resource, String put, String back, RadioJLabel init) {
        super();

        try (InputStream in= Resources.getResourceAsStream(resource)){
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            icon = new ImageIcon(bytes);
            iconCache = new ImageIcon(bytes);  //复制一份图像

        } //获得图片
        try (InputStream in= Resources.getResourceAsStream(put)){
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            putIcon = new ImageIcon(bytes);
        } //获得图片
        try (InputStream in= Resources.getResourceAsStream(back)){
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            backIcon = new ImageIcon(bytes);
        } //获得图片
        this.resource = resource;  //设置名字
        this.put = put;  //设置名字
        this.backResource = back;
        this.setIcon(icon);  //设置图片
        this.setBounds(1,1,icon.getIconWidth(),icon.getIconHeight()); //设置默认位置
        this.addMouseListener(this);  //加入监听
        this.addMouseMotionListener(this);   //加入监听
        this.back = init;  //初始化背景
    }

    /**
     * 创建一个图片单选按钮，传入图片名字，放置图片名字，背景图片名字,需要放在resources下
     * 设置默认位置左上角,默认大小为图片大小
     * 需要添加一个背景标签JLabel,新创建即可不需要任何操作，然后在容器中把这个加入到容器中，不然不能正常显示背景
     * @param resource
     */
    @SneakyThrows
    public ChooseBackButton(String resource, String put, String back, RadioJLabel init, DynamicJLabel text , Location location) {
        this(resource,put,back,init);
        this.text = text; //初始化文字
        if (location== Location.EAST) {  //如果在右边添加
            text.setBoundsDynamic(this.getX() + 30, this.getY() - 3);  //文本的位置
        }else {
            text.setBoundsDynamic(this.getX() - 30 - text.getWidth(), this.getY() - 3);  //文本的位置
        }

    }
        @Override
    public void mouseClicked(MouseEvent e) {
        click = !click;  //点击后加入背景
        if(click) {
//            back.setBounds(ChooseBackButton.this.getX() - 5, ChooseBackButton.this.getY() - 5, ChooseBackButton.this.getWidth() + 5, this.getHeight() + 5);  //设置背景位置
//            back.setColor(new Color(239, 112, 56, 174)); //设置颜色
            icon = backIcon;  //现在默认为backIcon
            back.repaint();
        }else{
            //让背景消失
//            back.setBounds(ChooseBackButton.this.getX() - 5, ChooseBackButton.this.getY() - 5, 0, 0);  //设置背景位置
            icon = iconCache;   //还原默认icon
            back.repaint();

        }

    }

    /**
     * 设置方位，前提是调用的是传入DynamicJLabel的构造方法,注意前面动态文字的字体要提前创建
     */
    public void setLocation(Location location) {
        if (location== Location.EAST) {  //如果在右边添加
            text.setBoundsDynamic(this.getX() + 30, this.getY() - 3);  //文本的位置
        }else {
            text.setBoundsDynamic(this.getX() - 30 - text.getWidth()+20, this.getY() - 3);  //文本的位置
        }
    }

    /**
     * 函数式方法，按钮中存储着是否处于点击状态，将外部消费者传入，交给消费者现在的点击状态，消费者通过点击状态处理事件
     * @param comsumer  要处理监听事件的消费者
     */
    public void isClick(Consumer<Boolean> comsumer) {
        comsumer.accept(click); //消费者接收click
    }
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setIcon(icon); //还原图片
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @SneakyThrows
    @Override
    public void mouseMoved(MouseEvent e) {
        this.setIcon(putIcon);  //设置放置时图片
    }

    /**
     * 内部枚举类
     */
    public enum Location{
        EAST,   //文字在右边
        WEST    //文字在左边
    }

}

