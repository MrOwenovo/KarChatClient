package KarChat.Server.DataBase.Mapper;

import KarChat.Server.DataBase.Entry.Friends;
import KarChat.Server.DataBase.Entry.Message;
import KarChat.Server.DataBase.MybatisUnit;
import lombok.SneakyThrows;

import java.util.Arrays;

public class demo {
    @SneakyThrows
    public static void main(String[] args) {


        while (true) {
            Thread.sleep(5000);
            MybatisUnit.doChatWork(mapper -> {
                Message[] chatHistory = mapper.getChatHistory("1231234_123123");//获取全部聊天内容
                //发送给客户端
                System.out.println(Arrays.toString(chatHistory));
                System.out.println(chatHistory.length);
            });
            Thread.sleep(2000);
            //存到聊天表里
            MybatisUnit.doChatWork(mapper->{
                mapper.insertMessage("1231234_123123", "1231234", "123123", "测试信息5");  //添加信息
            });
        }




        }
}
