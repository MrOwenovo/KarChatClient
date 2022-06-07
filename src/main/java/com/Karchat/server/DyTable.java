package com.Karchat.server;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class DyTable {
    public void init() {
        Vector columnNames = new Vector(); //设置列名

        columnNames.add("IP");
        columnNames.add("端口");
        columnNames.add("用户名");
        columnNames.add("状态");


        Vector rowData = new Vector();

        Vector hang = new Vector();//设置每一行的值

        hang.add("data");
        hang.add("data[1");
        hang.add("data[2]");
        hang.add("data[3]");
        hang.add("data[4]");

        rowData.add(hang);//加入rowData中

        DefaultTableModel defaultTableModel = new DefaultTableModel(rowData, columnNames);

        JTable table = new JTable(defaultTableModel);
    }

}
