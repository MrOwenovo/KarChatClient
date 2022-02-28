package KarChat.Server;

import KarChat.Chat.Login.LoginHome;
import KarChat.Server.DataBase.Entry.Icon;
import KarChat.Server.DataBase.Entry.User;
import KarChat.Server.DataBase.MybatisUnit;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * 服务器处理类,加入多线程机制,在此类中处理客户端发来的数据，并返回给客户端
 */
public class EchoThread implements Runnable {  //实现Runnable接口
    private Socket client = null;  //接收客户端
    public static boolean exit = false;  //判断是否退出
    String username = null;
    public EchoThread(Socket client) {  //通过构造方法设置Socket
        this.client = client;
    }

    @SneakyThrows
    @Override
    public void run() {
        //用try-with-resource处理
        try (PrintStream out = new PrintStream(
                client.getOutputStream());  //实例化客户端的输出流
             //用于接收客户端发来的信息
             BufferedReader buf = new BufferedReader(  //得到客户端的输入信息
                     new InputStreamReader(client.getInputStream()));) {

            boolean flag = true;  //标志位，表示一个客户端是否操作完毕
            while (flag) {
                String str = buf.readLine();  //在此处不断的接收信息
                switch (str) {
                    case "login":
                        username = buf.readLine();  //读取用户名
                        String password = buf.readLine();  //读取密码

                        String finalUsername = username;
                        MybatisUnit.doSqlWork(mapper -> {
                            User user = mapper.checkLogin(finalUsername, password);
                            if (user != null) {
                                //判断该用户是否已经登录
                                if (user.getState() != 1) {  //若未登录
                                    System.out.println("登录成功");
                                    out.println("true");
                                    MybatisUnit.doSqlWork(mapper2 -> {
                                        mapper2.updateState(1, finalUsername);
                                    });
                                }else{
                                    out.println("already");
                                }
                            } else {
                                out.println("false");
                            }
                        });
                        break;
                    case "register":
                        String username2 = buf.readLine();  //读取用户名
                        String password2 = buf.readLine();  //读取密码
                        String iconString = buf.readLine();  //读取用户头像

                        MybatisUnit.doSqlWork(mapper->{
                            int i=mapper.register(username2, password2,iconString);
                            if (i == 1) {
                                out.println("true");
                            }
                        });
                        break;
                    case "getIcon":
                        MybatisUnit.doSqlWork(mapper->{
                            Icon icon=mapper.getIcon(username);
                            out.println(icon.getIconString());
                        });
                        break;
                }

            }

        } catch (SocketException | NullPointerException e) {
            System.out.println(this.client.getInetAddress() + "连接异常");
        }finally{
            String finalUsername1 = username;
            MybatisUnit.doSqlWork(mapper->{
                mapper.updateState(0, finalUsername1);
            });
        }

    }
}
