package KarChat.Server.DataBase.Mapper;

import KarChat.Server.DataBase.Entry.Friends;
import KarChat.Server.DataBase.Entry.Post;
import KarChat.Server.DataBase.Entry.User;
import org.apache.ibatis.annotations.*;

public interface AddFriend {

    //发送好友请求
    @Insert("insert into user.addFriend(post,geter) values(#{post},#{geter}) ")
    int addFriend(@Param("post") String post, @Param("geter") String geter);

    //查找几个人加我好友
    @Select("select * from user.addFriend where geter=#{name} and state =0 ")
    Post[] checkGet(String name);

    //查找发送给了几个人
    @Select("select * from user.addFriend where post=#{name}")
    Post[] checkPost(String name);

    //判断是否已经发给了该用户
    @Select("select * from user.addFriend where post=#{post} and geter=#{geter}")
    Post checkPostIsExist(@Param("post") String post,@Param("geter") String geter);

    //创建名为name的表，储存name的所有好友
    int createFriendsTable(@Param("tableName")String  tableName);


    //创建两个人的聊天记录表，表名称储存在两个人的好友聊表中
    int createChatTable(@Param("tableName")String tableName);

    //修改addFriends的state
    @Update("update user.addFriend set state=1 where post=#{post} and geter=#{geter}")
    int updateAddState(@Param("post") String post, @Param("geter") String geter);

    //在好友中添加对方
    @Insert("insert into user.${table}(friends) values(#{friendName})   ")
    int addFriendName(@Param("table") String table, @Param("friendName") String friendName);

    //将聊天表的名字加入两个人的好友表中
    @Update("update user.${table} set chatLocation=#{chatLocation} where friends=#{friends}")
    int updateChatLocation(@Param("table") String table, @Param("chatLocation") String chatLocation, @Param("friends") String friends);

    //删除addFriend中的邀请
    @Delete("delete from user.addFriend where post=#{post} and geter=#{geter}")
    int deleteFriendName(@Param("post") String post, @Param("geter") String geter);

    //获得用户的全部好友
    @Results({
            @Result(column = "friends",property = "friends")
    })
    @Select("select * from user.${table}")
    Friends[] checkFriends(String table);

    //查询用户状态
    @Select("select * from user.user where username=#{username}")
    User getUserState(String username);

}
