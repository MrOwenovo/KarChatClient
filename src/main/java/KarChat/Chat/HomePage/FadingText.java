package KarChat.Chat.HomePage;

import com.sun.awt.AWTUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author zhenyu tan
 * 2014年4月2日
 * 使用到了JDK1.6中新特性的透明窗体，所以必须要使用JDK1.6及其以上版本
 * 功能如下：
 * 1.窗体出现时逐渐清晰
 * 2.停留一会时间之后会自动逐渐模糊直至消失
 * 3.点击关闭按钮后逐渐模糊直至消失
 */
class FadingText {

    JFrame frame;
    JLabel label;
    JEditorPane editorPane;

    private int width;//窗体宽度
    private int height;//窗体高度
    private int stayTime;//休眠时间
    private String title;//消息标题
    private String message;//窗体内容
    private int style;//窗体样式

    static {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param width 提示框宽度
     * @param height 提示框高度
     * @param stayTime 提示框停留时间
     * @param style 提示框的样式
     * @param title 提示框标题
     * @param message 提示框内容（支持HTML标签）
     */
    public FadingText(int width, int height, int stayTime, int style, String title, String message) {
        this.width = width;
        this.height = height;
        this.stayTime = stayTime;
        this.style = style;
        this.title = title;
        this.message = message;
    }

    public FadingText(int stayTime, int style, String title, String message) {
        this.width = 300;
        this.height = 100;
        this.stayTime = stayTime;
        this.style = style;
        this.title = title;
        this.message = message;
    }

    public void initialize() {
        frame = new JFrame();
        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setContentType("text/html");
        editorPane.setText(message);
        frame.add(editorPane);
        frame.setTitle(title);
        //设置窗体的位置及大小
        Point location = MouseInfo.getPointerInfo().getLocation();
        frame.setBounds((int)location.getX(), (int)location.getY(), width, height);
        frame.setUndecorated(true);//去掉窗口的装饰
        frame.getRootPane().setWindowDecorationStyle(style);//设置窗体样式
        AWTUtilities.setWindowOpacity(frame, 0);//初始化透明度
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);//窗体置顶
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                hide();
            }
        });
    }

    //窗体逐渐变清晰
    public void show() {
        for (int i = 1; i < 20; i++) {
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                AWTUtilities.setWindowOpacity(frame, i * 0.05F);
            }
        }
    }

    //窗体逐渐变淡甚至消失
    public void hide() {
        float opacity = 100;
        while(true) {
            if (opacity < 2) {
                break;
            }

            opacity -= 2;
            AWTUtilities.setWindowOpacity(frame, opacity / 100);
            try {
                Thread.sleep(150);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        frame.dispose();
    }

    public void run() {
        initialize();
        show();
        try {
            Thread.sleep(stayTime * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        hide();
    }

    public static void main(String[] args) {
        String title = "友情提示！";
        String message = "主人!<br />该休息了！";
        FadingText FadingText = new FadingText(2, JRootPane.QUESTION_DIALOG, title, message);
        FadingText.run();
    }
}
