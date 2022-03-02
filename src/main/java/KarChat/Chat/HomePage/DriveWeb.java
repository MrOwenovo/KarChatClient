package KarChat.Chat.HomePage;//package ProjectInstances.图形界面.MyComponent;

import java.awt.*;
import javax.swing.*;

import KarChat.Chat.Login.Frameless;
import KarChat.Chat.Login.RadioJLabel;
import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
/**
 * @author lijunming
 * @date 2018/7/22 下午6:00
 */
public class DriveWeb extends JPanel {

    private JPanel webBrowserPanel;

    private JWebBrowser webBrowser;

    public DriveWeb(String url) {
        super(new BorderLayout());
        webBrowserPanel = new JPanel(new BorderLayout());
        webBrowser = new JWebBrowser();
        webBrowser.navigate(url);  //输入网址
        webBrowser.setButtonBarVisible(false);
        webBrowser.setMenuBarVisible(false);
        webBrowser.setBarsVisible(false);
        webBrowser.setStatusBarVisible(false);
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        add(webBrowserPanel, BorderLayout.CENTER);
        //执行Js代码
//        webBrowser.executeJavascript("alert('hello swing')");
    }


    /**
     * 在swing里内嵌浏览器
     * @param url  要访问的url
     */
    public  static void  openForm(String url, RadioJLabelNew frame2){
        UIUtils.setPreferredLookAndFeel();
        NativeInterface.open();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JLabel frame = new JLabel();
                frame2.add(frame);
                //设置窗体关闭的时候不关闭应用程序
                DriveWeb driveWeb = new DriveWeb(url);
                frame.add(driveWeb);
                driveWeb.setSize(1400,700);
                frame.setSize(1400,700);
                //让窗体可见
                frame2.setVisible(true);
                //重置窗体大小
                // 设置窗体的宽度、高度
                 //设置窗体居中显示
            }
        });
        NativeInterface.runEventPump();
    }

    public static void main(String[] args) {
        openForm("http://localhost:8080/KarCharWeb/home3-1",null);
    }
}
