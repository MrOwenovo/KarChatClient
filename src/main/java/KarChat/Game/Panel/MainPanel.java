package KarChat.Game.Panel;



import KarChat.Game.Domain.ChessPad;
import KarChat.Game.Listiner.PutChessListener;

import javax.swing.*;

/**
 * 主容器
 */

//主界面，父类是JSplitPane
public class MainPanel extends JSplitPane {
    private  static JSplitPane instance;

    //主界面的构造函数，用于初始化界面
    static {
        ButtonPanel buttonPanel=new ButtonPanel();
        ChessPadPanel chessPadPanel=ChessPadPanel.getInstance();
        chessPadPanel.listener=new PutChessListener();


        ChessPad.ClearColorPad();   //初始化棋盘
        instance=new JSplitPane(JSplitPane.VERTICAL_SPLIT,chessPadPanel,buttonPanel);
        instance.setDividerLocation(700);
        instance.setEnabled(false);
    }

    public static JSplitPane getInstance(){
        return instance;
    }
}
