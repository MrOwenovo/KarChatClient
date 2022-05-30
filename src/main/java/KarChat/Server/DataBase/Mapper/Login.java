package KarChat.Server.DataBase.Mapper;

import KarChat.Server.DataBase.Entry.Icon;
import KarChat.Server.DataBase.Entry.User;
import org.apache.ibatis.annotations.*;

public interface Login {
    @Select("select * from user.user where username=#{username} and password=#{password}")
    User checkLogin(@Param("username") String username, @Param("password") String password);

    @Insert("insert into user.user(username, password,icon) VALUES(#{username},#{password},#{iconString})")
    int register(@Param("username")String username,@Param("password")String password,@Param("iconString")String iconString);

    //修改状态
    @Update("update user.user set state=#{state} where username=#{username} ")
    int updateState(@Param("state") int state,@Param("username")String username);

    //获取用户姓名
    @Select("select * from user.user where username=#{username}")
    User getName(String username);


    //获取头像
    @Results({
            @Result(column = "icon",property = "iconString")
    })
    @Select("select user.icon from user.user where username=#{username}")
    Icon getIcon(String username);

    //保存用户名与客户端下标的关系
    @Insert("insert into user.clientIndex(username,indexs) VALUES(#{username},#{indexs})")
    int insertIndex(@Param("username")String username,@Param("indexs")int indexs);

    //删除用户名与客户端下标的关系
    @Delete("delete from user.clientIndex where username=#{username}")
    int deleteIndex(String username);

    //通过用户名查找客户端下标
    @Select("select indexs from user.clientIndex where username=#{username}")
    int getIndex(String username);

}
