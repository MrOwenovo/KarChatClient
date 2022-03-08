package KarChat.Server;

import KarChat.Server.DataBase.Entry.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class UserTable implements Runnable{

    private static int index = 0;//当前第0个用户
    private static Vector rowData;
    private static Vector hang;
    private static HashMap<String,Integer> userIndex=new HashMap<>();  //用户处于第几行
    private static DefaultTableModel defaultTableModel;
    private static Vector columnNames;
    private static JTable table;
    private static Vector newVector;
    private static JPanel panel;
    private static  MyTableCellRenderer renderer;
    private static int deletIndex;


    /**
     * first复制到second
     * @param first
     * @param second
     */
    private static void CopyArray(Vector first,Vector second) {
        for (int i = 0; i < first.size(); i++) {
            second.add(first.get(i));
        }
    }
    /**
     * 加入用户数据
     */
    public synchronized static void addUser(String IP,String port,String username,String state) {

        hang = new Vector();  //创建新的一行
        hang.add(IP);
        hang.add(port);
        hang.add(username);
        hang.add(state);

        rowData.add(hang);  //表格加入行
        userIndex.put(username, index);

        defaultTableModel.getDataVector().clear();  //清空数据
        CopyArray(rowData,newVector);  //复制新数据
        defaultTableModel.setDataVector(newVector,columnNames);
        table.updateUI(); //更新数据
        // 遍历表格的每一列，分别给每一列设置单元格渲染器
        for (int i = 0; i < columnNames.size(); i++) {
            // 根据 列名 获取 表格列
            TableColumn tableColumn = table.getColumn(columnNames.get(i));
            // 设置 表格列 的 单元格渲染器
            tableColumn.setCellRenderer(renderer);
        }
        panel.repaint();


    }

    /**
     * 退出用户
     * @param username
     */
    public synchronized static void exitUser(String username) {
        try {
            deletIndex = userIndex.get(username);
        } catch (NullPointerException e) {
            System.out.println("退出异常:用户名为空");
        }

        try {
            rowData.remove(deletIndex);  //删除行
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("退出异常:数组越界");
        }

        //重新排列数组
//        reSortArray(rowData, deletIndex);

        defaultTableModel.getDataVector().clear();  //清空数据
        CopyArray(rowData,newVector);  //复制新信息
        defaultTableModel.setDataVector(newVector,columnNames);
        table.updateUI(); //更新数据
        // 遍历表格的每一列，分别给每一列设置单元格渲染器
        for (int i = 0; i < columnNames.size(); i++) {
            // 根据 列名 获取 表格列
            TableColumn tableColumn = table.getColumn(columnNames.get(i));
            // 设置 表格列 的 单元格渲染器
            tableColumn.setCellRenderer(renderer);
        }
        panel.repaint();
    }

    public UserTable(){
        JFrame jf = new JFrame("用户管理系统");
        JComboBox<String> stateList = new JComboBox<String>();  //定义下拉列表框
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(1);
            }
        });
        jf.setLocationRelativeTo(null);
        jf.setSize(800,500);
        // 创建内容面板，使用边界布局
        panel = new JPanel(new BorderLayout());

        JScrollPane scr = new JScrollPane(panel);  //加入滚动条


        // 表头（列名）
        //设置列名
        columnNames = new Vector();

        columnNames.add("IP");
        columnNames.add("端口");
        columnNames.add("用户名");
        columnNames.add("状态");

        // 表格所有行数据
        rowData = new Vector();
        newVector = new Vector();



        defaultTableModel = new DefaultTableModel(newVector, columnNames);

        // 创建一个表格，指定 所有行数据 和 表头
        table = new JTable(defaultTableModel);


        stateList.addItem("在线");  //增加下拉选项
        stateList.addItem("离线");
        stateList.setMaximumRowCount(2);
        table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(stateList));  //将下拉列表选项加入到表格

        // 创建单元格渲染器
        renderer = new MyTableCellRenderer();

        // 遍历表格的每一列，分别给每一列设置单元格渲染器
        for (int i = 0; i < columnNames.size(); i++) {
            // 根据 列名 获取 表格列
            TableColumn tableColumn = table.getColumn(columnNames.get(i));
            // 设置 表格列 的 单元格渲染器
            tableColumn.setCellRenderer(renderer);
        }

        // 如果需要自定义表头样式，也可以给表头设置一个自定义渲染器
//         table.getTableHeader().setDefaultRenderer(headerRenderer);

        // 把 表头 添加到容器顶部（使用普通的中间容器添加表格时，表头 和 内容 需要分开添加）
        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        // 把 表格内容 添加到容器中心
        panel.add(table, BorderLayout.CENTER);


        jf.setContentPane(scr);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);

    }

    boolean flag = true;  //判断是加用户还是删除
    String IP;
    String port;
    String username;
    String state;

    public void setAdd(String IP, String port, String username, String state) {
        this.IP = IP;
        this.port = port;
        this.username = username;
        this.state = state;
        flag = true;
    }

    String exitUsername;
    public void setExit(String username) {
        exitUsername = username;
        flag = false;
    }
    @Override
    public void run() {
        if (flag) {
            addUser(IP, port, username, state);
        } else {
            exitUser(exitUsername);

        }
    }

    public static void main(String[] args) {
        new UserTable();
    }
    /**
     * 单元格渲染器，继承已实现渲染器接口的默认渲染器 DefaultTableCellRenderer
     */
    public static class MyTableCellRenderer extends DefaultTableCellRenderer {
        /**
         * 返回默认的表单元格渲染器，此方法在父类中已实现，直接调用父类方法返回，在返回前做相关参数的设置即可
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // 偶数行背景设置为白色，奇数行背景设置为灰色
            if (row % 2 == 0) {
                setBackground(Color.WHITE);
            } else {
                setBackground(Color.LIGHT_GRAY);
            }

            // 第一列的内容水平居中对齐，最后一列的内容水平右对齐，其他列的内容水平左对齐
            if (column == 0) {
                setHorizontalAlignment(SwingConstants.CENTER);
            } else if (column == (table.getColumnCount() - 1)) {
                setHorizontalAlignment(SwingConstants.RIGHT);
            } else {
                setHorizontalAlignment(SwingConstants.LEFT);
            }

            // 设置提示文本，当鼠标移动到当前(row, column)所在单元格时显示的提示文本
            setToolTipText("提示的内容: " + row + ", " + column);

            // PS: 多个单元格使用同一渲染器时，需要自定义的属性，必须每次都设置，否则将自动沿用上一次的设置。

            /*
             * 单元格渲染器为表格单元格提供具体的显示，实现了单元格渲染器的 DefaultTableCellRenderer 继承自
             * 一个标准的组件类 JLabel，因此 JLabel 中相应的 API 在该渲染器实现类中都可以使用。
             *
             * super.getTableCellRendererComponent(...) 返回的实际上是当前对象（this），即 JLabel 实例，
             * 也就是以 JLabel 的形式显示单元格。
             *
             * 如果需要自定义单元格的显示形式（比如显示成按钮、复选框、内嵌表格等），可以在此自己创建一个标准组件
             * 实例返回。
             */
            // 调用父类的该方法完成渲染器的其他设置
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

}
