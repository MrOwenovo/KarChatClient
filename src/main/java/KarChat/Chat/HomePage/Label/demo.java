package KarChat.Chat.HomePage.Label;

import KarChat.Chat.HomePage.LoadingHome;
import KarChat.Chat.Login.Frameless;

import javax.swing.*;

public class demo {
    public static void main(String[] args) {
        JFrame frameless = new JFrame();
        frameless.setSize(1000,1000);
        LoadingHome home = new LoadingHome();
//        innerLabel.setBounds(100,100,100,100);
        frameless.setLayout(null);
        frameless.add(home);

        frameless.setVisible(true);
    }
}
