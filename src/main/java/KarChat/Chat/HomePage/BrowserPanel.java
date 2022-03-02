package KarChat.Chat.HomePage;
import java.awt.*;

import javax.swing.JPanel;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

public class BrowserPanel extends JPanel {

    private JWebBrowser browser;

    public JWebBrowser getBrowser() {

        return browser;

    }

    private void setBrowser(JWebBrowser browser) {

        this.browser = browser;

    }

    public BrowserPanel() {

        setLayout(new BorderLayout(0, 0));

        browser = new JWebBrowser();

        browser.navigate("http://localhost:8080/KarCharWeb/home"); //这一行是告诉browser浏览哪个网址

        browser.setBarsVisible(false);

        browser.setButtonBarVisible(false);

        browser.setMenuBarVisible(false);

        add(browser, BorderLayout.CENTER);

    }

    public static void main(String[] args) {

    }
}