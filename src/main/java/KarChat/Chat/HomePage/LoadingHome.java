package KarChat.Chat.HomePage;

import KarChat.Chat.Helper.ChangeToColor;
import KarChat.Chat.Login.RadioJLabel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class LoadingHome extends RadioJLabel {

    private int yOld;
    private int xOld;

    public LoadingHome() {
        this.setArc(30, 30);
        this.setColor(new Color(166, 163, 163));
        this.setBounds(0, 50, 1300, 743);


        //处理鼠标点击事件
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xOld = e.getX();  //存储点击时的坐标
                yOld = e.getY();
            }
        });
        //处理鼠标拖拽事件
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int xOnScreen = e.getXOnScreen(); //获取当前鼠标拖拽时x坐标
                int yOnScreen = e.getYOnScreen(); //获取当前鼠标拖拽时y坐标
                int xx = xOnScreen - xOld;  //移动差值
                int yy = yOnScreen - yOld;
                LoadingHome.this.setLocation(xx, yy - 50);  //移动窗口
            }
        });

        //加入加载条
//        this.setColor(ChangeToColor.getColorFromHex("#333333"));
        this.setColor(new Color(51, 51, 51));

        LoadingBack load1 = new LoadingBack(130, ChangeToColor.getColorFromHex("#F317FE"));
        LoadingBack load2 = new LoadingBack(170, ChangeToColor.getColorFromHex("#AD55CB"));
        LoadingBack load3 = new LoadingBack(210, ChangeToColor.getColorFromHex("#9172DB"));
        load1.show();
        load2.show();
        load3.show();
        this.add(load1);
        this.add(load2);
        this.add(load3);
        load1.setSize(130,130);
        load2.setSize(170,170);
        load3.setSize(210,210);
        load1.setLocation(100+this.getWidth()/2 - load1.getWidth()-39, 120-81+this.getHeight()/2 - load1.getHeight());
        load2.setLocation(100+this.getWidth()/2 - load2.getWidth()-19, 120-60+this.getHeight()/2 - load2.getHeight());
        load3.setLocation(100+this.getWidth()/2 - load3.getWidth(), 120+this.getHeight()/2 - load3.getHeight()-40);
    }
    }
