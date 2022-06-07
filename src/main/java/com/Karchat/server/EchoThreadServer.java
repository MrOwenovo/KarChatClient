package com.Karchat.server;


import com.Karchat.util.DataBaseUnit.MybatisUnit;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器集中处理client请求，使用多线程实现并发访问,开启服务器后，每当接收到一个client就发送给一个EchoClient线程处理客户端请求，
 * 此类只负责处理并发，发放并启动线程
 */
@Slf4j
public class EchoThreadServer {
    private static Socket client = null;
    public static UserTable table;
    public static Thread[] threads;
    private static Socket[] clients;

    public static void main(String[] args) throws Exception{
        //用try-with-resource处理关闭
        try (ServerSocket server = new ServerSocket(8888);  //此服务器在8888端口上进行监听
        ) {
            boolean flag = true;  //判断是否输出提示,第一次才提示
            boolean f = true;  //定义一个标记为true
            int i = -1;
            //500并发
            threads = new Thread[500];
            clients = new Socket[500];
            while (f) {  //无限制接受客户端连接
                i++;
                if (flag) {
                    log.info("服务器运行，等待客户端连接....");
                    new Thread() {
                        @Override
                        public void run() {
                            //打开用户管理页面
                            table = new UserTable();  //总线程
                        }
                    }.start();
                    flag = false; //标记为负数
                }
                client = server.accept();  //接收客户端连接
                log.info("客户端" + client.getInetAddress() + ": " + client.getPort() + "已连接");
                clients[i] = client;
                EchoThread echoThread = new EchoThread(client);
                echoThread.index = i;  //保存当前的下标
                threads[i] = new Thread(echoThread);
                threads[i].start();  //实例化并启动一个线程对象
                int finalI = i;
                new Thread(() -> {  //lambda表达式  ,判断上面的线程是否消亡
                    boolean flag1 = true;  //判断此线程是否继续进行的标志
                    long start = System.currentTimeMillis();  //开始时间
                    String clientInet = client.getInetAddress().toString();
                    int clientPort = client.getPort();
                    while (flag1) {
                        if (!threads[finalI].isAlive()) {
                            EchoThread.exit = true;
                            long end = System.currentTimeMillis();
                            long duration = (end - start) / 1000; //总共进行了多少秒
                            long minute = duration / 60;  //运行了多少分钟
                            long hour = 0;  //运行了多少小时
                            if (minute > 60) {
                                hour = minute / 60;
                            }
                            //用三目表达式判断client运行时间
                            log.info("客户端" + clientInet + ": " + clientPort + "已断开,运行了" + (hour > 0 ? hour + "小时" : "") + (minute > 0 ? minute + "分钟" : "") + duration % 60 + "秒");
                            flag1 = false;  //消灭此线程
                        }
                    }
                }).start();   //执行检测线程
            }
        } catch (NullPointerException e) {
            //
        }
    }

    @SneakyThrows
    public static synchronized void sendToClient(String myName,String sendToFriend,String message) {
        //找到这个用户
        MybatisUnit.doSqlWork(mapper->{
            try {
                int index = mapper.getIndex(sendToFriend);  //获取用户客户端下标  ,该用户不存在则报错
                Socket friendClient = clients[index];  //获取到对应的客户端
                PrintStream out = new PrintStream(friendClient.getOutputStream());
                    out.println("getMessage");
                    out.println(message);  //发送信息
                    out.println(myName); //发送发送人姓名
            } catch (Exception e) {
                log.info("该用户不在线");
            }
        });

        }
    }
