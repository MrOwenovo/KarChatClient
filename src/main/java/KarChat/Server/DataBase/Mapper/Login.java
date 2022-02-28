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

    //获取头像
    @Results({
            @Result(column = "icon",property = "iconString")
    })
    @Select("select user.icon from user.user where username=#{username}")
    Icon getIcon(String username);


}
