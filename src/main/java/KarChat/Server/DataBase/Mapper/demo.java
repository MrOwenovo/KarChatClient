package KarChat.Server.DataBase.Mapper;

import KarChat.Server.DataBase.Entry.Friends;
import KarChat.Server.DataBase.Entry.Post;
import KarChat.Server.DataBase.MybatisUnit;

public class demo {
    public static void main(String[] args) {
//        MybatisUnit.doChatWork(mapper -> {
//            int i=mapper.createFriendsTable("D12341123");
//            System.out.println(i);
//        });
        MybatisUnit.doChatWork(mapper->{
            String username="123123";
            if (username.matches("^[0-9]*$")) {  //如果好友账号是纯数字
                System.out.println("_"+username);
                Friends[] friends=mapper.checkFriends("_" + username);
                System.out.println(friends.length);
                for (int i = 0; i < friends.length; i++) {
                    System.out.println(friends[i].getFriends());
                }
            } else {
                mapper.checkFriends(username);
            }
        });
    }
}
