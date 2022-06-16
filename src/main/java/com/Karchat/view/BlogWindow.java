package com.Karchat.view;

import static javax.swing.BorderFactory.createEmptyBorder;
import static java.lang.Math.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.Karchat.entity.blogcore.BlogModel;
import com.Karchat.util.ComponentUtil.Label.RadioJLabel;
import com.Karchat.dao.operation.DatabaseOperation;
import com.Karchat.entity.blogcore.BlogList;


public class BlogWindow {
    public JPanel mainContentPane;
    private JPanel newTaskControls;
    private JButton addTaskButton;
    private JTextField newTaskField;
    private JScrollPane taskListScrollPane;
    private JPanel taskListControls;
    private JButton upButton;
    private JButton deleteButton;
    private JButton downButton;
    private JList<String> taskList;
    private RadioJLabel contentLabel;
    private RadioJLabel statusBar;
    private BlogList blogList;
    private BlogModel blogModel;

    public BlogWindow() {
        //新建todoList和todoModel
        this.blogList = new BlogList();
        this.blogModel = new BlogModel(this.blogList);
        this.getMainContentPane();

        //添加初始内容
        this.addInitialContent();
    }

    private void getMainContentPane() {
        //设置主窗口
        if (mainContentPane == null) {
            this.mainContentPane = new JPanel();
            this.mainContentPane.setBackground(new Color(239, 238, 238));
            this.mainContentPane.setBounds(280, 100, 880, 620);
            this.mainContentPane.setLayout(null);
            //添加窗口组件
            this.mainContentPane.add(getContentLabel());
            this.contentLabel.add(getNewTaskControls());
            this.contentLabel.add(getTasksListScrollPane());
            this.contentLabel.add(getTasksListControls());
            this.contentLabel.add(getStatusBar());
        }
    }

    private JLabel getContentLabel() {
        contentLabel = new RadioJLabel();
        contentLabel.setColor(new Color(239, 238, 238));
        contentLabel.setBounds(0, 0, 880, 620);
        contentLabel.setLayout(null);
        return contentLabel;
    }

    private Component getNewTaskControls() {
        if (this.newTaskControls == null) {
            this.newTaskControls = new JPanel();
            this.newTaskControls.setBounds(0, 0, 880, 50);
            this.newTaskControls.setBackground(new Color(239, 238, 238));
            this.newTaskControls.setLayout(null);
            this.newTaskControls.setBorder(createEmptyBorder(10, 0, 10, 10));
            this.newTaskControls.add(getNewTaskField());
            this.newTaskControls.add(getAddTaskButton());
        }

        return this.newTaskControls;
    }

    private JTextField getNewTaskField() {
        if (this.newTaskField == null) {
            this.newTaskField = new JTextField();
            this.newTaskField.setBounds(0, 0, 700, 40);
            this.newTaskField.setBorder(new RoundBorder(new Color(189, 99, 252)));
        }
        return this.newTaskField;
    }

    private Component getTasksListScrollPane() {
        if (this.taskListScrollPane == null) {
            this.taskListScrollPane = new JScrollPane(getTaskList());
            this.taskListScrollPane.setBounds(0, 50, 700, 500);

        }

        return this.taskListScrollPane;
    }

    private JList<String> getTaskList() {
        if (this.taskList == null) {
            this.taskList = new JList<>();
            this.taskList.setModel(this.blogModel);
        }

        return this.taskList;
    }

    private Component getTasksListControls() {
        if (this.taskListControls == null) {
            this.taskListControls = new JPanel();
            this.taskListControls.setBounds(750, 50, 100, 500);
            this.taskListControls.setBackground(new Color(239, 238, 238));
//            BoxLayout layout = new BoxLayout(this.taskListControls, BoxLayout.Y_AXIS);
            this.taskListControls.setLayout(null);
//            this.taskListControls.setBorder(createEmptyBorder(5, 5, 5, 5));

            JButton button = getUpButton();
//            button.setAlignmentX(JFrame.CENTER_ALIGNMENT);
            this.taskListControls.add(button);

//            this.taskListControls.add(createVerticalStrut(10));

            button = getDeleteButton();
//            button.setAlignmentX(JFrame.CENTER_ALIGNMENT);
            this.taskListControls.add(button);

//            this.taskListControls.add(createVerticalStrut(10));

            button = getDownButton();
//            button.setAlignmentX(JFrame.CENTER_ALIGNMENT);
            this.taskListControls.add(button);
        }

        return this.taskListControls;
    }

    private JButton getUpButton() {
        if (this.upButton == null) {
            //新建JButton
            this.upButton = new JButton("");
            this.upButton.setBounds(0, 40, 100, 40);
            this.upButton.setBackground(new Color(239, 238, 238));
            this.upButton.setBorder(new RoundBorder());


            //设置图标
            this.upButton.setIcon(createIcon("up.png"));
            //设置监听器
            this.upButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int pos = getTaskList().getSelectedIndex();
                    blogModel.moveUp(pos);

                    getTaskList().setSelectedIndex(max(0, pos - 1));
                }
            });
        }

        return this.upButton;
    }

    private JButton getDeleteButton() {
        if (this.deleteButton == null) {
            this.deleteButton = new JButton("");
            this.deleteButton.setBackground(new Color(239, 238, 238));
            this.deleteButton.setBounds(0, 120, 100, 40);
            this.deleteButton.setIcon(createIcon("bin.png"));
            this.deleteButton.setBorder(new RoundBorder());

            this.deleteButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    blogModel.removeAt(getTaskList().getSelectedIndex());
                }
            });
        }

        return this.deleteButton;
    }

    private JButton getDownButton() {
        if (this.downButton == null) {
            this.downButton = new JButton("");
            this.downButton.setBackground(new Color(239, 238, 238));
            this.downButton.setBounds(0, 200, 100, 40);
            this.downButton.setIcon(createIcon("down.png"));
            this.downButton.setBorder(new RoundBorder());

            this.downButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int pos = getTaskList().getSelectedIndex();
                    blogModel.moveDown(pos);

                    getTaskList().setSelectedIndex(
                            min(getTaskList().getModel().getSize() - 1, pos + 1));
                }
            });
        }

        return this.downButton;
    }

    private JButton getAddTaskButton() {
        if (this.addTaskButton == null) {
            this.addTaskButton = new JButton("ADD");
            this.addTaskButton.setBackground(new Color(239, 238, 238));
            this.addTaskButton.setIcon(createIcon("diary.png"));
            this.addTaskButton.setBounds(750, 0, 100, 40);
            this.addTaskButton.setBorder(new RoundBorder());

            this.addTaskButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (getNewTaskField().getText().length() > 0) {
                        blogModel.add(getNewTaskField().getText().trim());

                        getNewTaskField().setText("");

                        getTaskList().setSelectedIndex(getTaskList().getModel().getSize() - 1);
                    }
                }
            });
        }

        return this.addTaskButton;
    }


    private JLabel getStatusBar() {
        if (this.statusBar == null) {
            this.statusBar = new RadioJLabel("NUMBER OF RECORDS: 0");
            statusBar.setHorizontalAlignment(SwingConstants.CENTER);
            statusBar.setColor(new Color(68, 138, 202));
            statusBar.setForeground(Color.WHITE);
            statusBar.setFont(new Font("Serif", Font.BOLD, 20));
            statusBar.setBounds(0, 560, 700, 30);
            this.blogModel.addListDataListener(new ListDataListener() {
                @Override
                public void contentsChanged(ListDataEvent e) {
                    updateLabel(e);
                }

                private void updateLabel(ListDataEvent e) {
                    getStatusBar().setText(" NUMBER OF RECORDS: " + ((BlogModel) e.getSource()).getSize());
                }

                @Override
                public void intervalRemoved(ListDataEvent e) {
                }

                @Override
                public void intervalAdded(ListDataEvent e) {
                }
            });
        }
        return this.statusBar;
    }

    private void addInitialContent() {
        DatabaseOperation dbOperation = new DatabaseOperation();
        ArrayList<String> list = dbOperation.getAllContent();
        for (String content : list) {
            blogModel.init(content.trim());
        }
        getTaskList().setSelectedIndex(0);
    }


    private Icon createIcon(String iconfilename) {
        return new ImageIcon(
                getClass().
                        getResource("/blogimages/" + iconfilename));
    }
}


class RoundBorder implements Border {
    private final Color color;

    public RoundBorder(Color color) {
        this.color = color;
    }

    public RoundBorder() {
        this.color = Color.BLACK;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }

    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(color);
        g.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 10, 10);
    }
}