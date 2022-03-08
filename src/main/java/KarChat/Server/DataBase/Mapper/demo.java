package KarChat.Server.DataBase.Mapper;

import KarChat.Server.DataBase.MybatisUnit;

public class demo {
    public static void main(String[] args) {
        MybatisUnit.doChatWork(mapper -> {
            int i=mapper.addFriend("123123","1231234");
            System.out.println(i);
        });
    }
}
