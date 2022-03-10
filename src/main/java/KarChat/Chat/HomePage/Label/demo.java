package KarChat.Chat.HomePage.Label;

import KarChat.Chat.Login.Frameless;

import javax.swing.*;

public class demo {
    public static void main(String[] args) {
        JFrame frameless = new JFrame();
        frameless.setSize(1000,1000);
        InnerLabel innerLabel = new InnerLabel();
//        innerLabel.setBounds(100,100,100,100);
        frameless.setLayout(null);
        frameless.add(innerLabel);

        frameless.setVisible(true);
    }
}
