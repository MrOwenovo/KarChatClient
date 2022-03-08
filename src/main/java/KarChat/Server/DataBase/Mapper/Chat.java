package KarChat.Server.DataBase.Mapper;

import KarChat.Server.DataBase.Entry.Post;
import org.apache.ibatis.annotations.*;

public interface Chat {

    //发送好友请求
    @Insert("insert into user.addFriend(post,geter) values(#{post},#{geter}) ")
    int addFriend(@Param("post") String post, @Param("geter") String geter);

    //查找几个人加我好友
    @Select("select * from user.addFriend where geter=#{name}")
    Post[] checkGet(String name);

    //查找发送给了几个人
    @Select("select * from user.addFriend where post=#{name}")
    Post[] checkPost(String name);

    //判断是否已经发给了该用户
    @Select("select * from user.addFriend where post=#{post} and geter=#{get}")
    Post checkPostIsExist(@Param("post") String post,@Param("geter") String geter);

    //创建名为name的表，储存name的所有好友
    int createFriendsTable(@Param("tableName")String  tableName);


    //创建两个人的聊天记录表，表名称储存在两个人的好友聊表中
    int createChatTable(@Param("tableName")String tableName);
}
