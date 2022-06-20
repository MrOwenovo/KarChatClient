package com.Karchat.dao.operation;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseOperation {
    String url;
    String user;
    String password;
    Connection conn;
    Statement stmt;

    public DatabaseOperation() {
        this.url = "jdbc:mysql://geoffyli.mysql.rds.aliyuncs.com:3306/user";
        this.user = "k_administrator";
        this.password = "Kk123456";
        this.conn = null;


        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接数据库
            conn = DriverManager.getConnection(url, user, password);
            //获取语句
            this.stmt = conn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(String item, int i){
        String sql = "insert into blog values(" + i + ", '" + item + "')";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeAt(int i){
        String removeSql = "delete from blog where id = " + i;
        String updateIdSql = "update blog set id = id - 1 where id > " + i;
        try {
            stmt.executeUpdate(removeSql);
            stmt.executeUpdate(updateIdSql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editAt(String content, int i){
        String updateSql = "update blog set content = '" + content + "' " + "where id = " + i;
        try {
            stmt.executeUpdate(updateSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void moveUp(int i){
        String updateIdSql01 = "update blog set id = -1 where id = " + i;
        String updateIdSql02 = "update blog set id = id + 1 where id = " + (i - 1);
        String updateIdSql03 = "update blog set id = " + (i - 1) + " where id = -1";
        try {
            stmt.executeUpdate(updateIdSql01);
            stmt.executeUpdate(updateIdSql02);
            stmt.executeUpdate(updateIdSql03);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void moveDown(int i){
        String updateIdSql01 = "update blog set id = -1 where id = " + i;
        String updateIdSql02 = "update blog set id = id - 1 where id = " + (i + 1);
        String updateIdSql03 = "update blog set id = " + (i + 1) + " where id = -1";
        try {
            stmt.executeUpdate(updateIdSql01);
            stmt.executeUpdate(updateIdSql02);
            stmt.executeUpdate(updateIdSql03);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getContent(int i){
        String content = null;
        String querySql = "select content from blog where id = " + i;
        try {
            ResultSet rs = stmt.executeQuery(querySql);
            if(rs.next()){
                content = rs.getString(1);
            }
            return content;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getAllContent(){
        ArrayList<String> contents = new ArrayList<>();
        String querySql = "select content from blog";
        try {
            ResultSet rs = stmt.executeQuery(querySql);
            while(rs.next()){
                contents.add(rs.getString(1));
            }
            return contents;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}