package KarChat.Server.DataBase.Mapper;

import KarChat.Server.DataBase.Entry.Friends;
import KarChat.Server.DataBase.MybatisUnit;

public class demo {
    public static void main(String[] args) {
        Friends[] friend=new Friends[1];
        String[] chatLocation = new String[1];
        MybatisUnit.doChatWork(mapper -> {
            if ("123123".matches("^[0-9]*$")) {
                friend[0] = mapper.getChatLocation("_" + "123123", "1231234");
            } else {
                friend[0] = mapper.getChatLocation("123123", "1231234");
            }
            chatLocation[0] = friend[0].getChatLocation();
            System.out.println(chatLocation[0]);
        });
        final int[] q = new int[1];
        //存到聊天表里
        MybatisUnit.doChatWork(mapper->{
            q[0] =mapper.insertMessage(chatLocation[0], "123123", "1231234", "你好");  //添加信息
        });
        System.out.println(q[0]);

    }
}
