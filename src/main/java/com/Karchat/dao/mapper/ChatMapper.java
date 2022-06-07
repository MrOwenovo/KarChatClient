package com.Karchat.dao.mapper;

import com.Karchat.entity.Friends;
import com.Karchat.entity.Message;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cache.decorators.LruCache;

@Mapper
@CacheNamespace(size=512,readWrite=false,flushInterval = 6000,eviction = LruCache.class)
public interface ChatMapper {

    //在聊天表中添加聊天内容
    @Insert("insert into user.${table}(user1,user2,message) values(#{user1},#{user2},#{message})   ")
    int insertMessage(@Param("table") String table, @Param("user1") String user1,@Param("user2") String user2,@Param("message") String message);


    //查询聊天表名
    @Select("select * from user.${table} where friends=#{friends}")
    Friends getChatLocation(@Param("table")String table, @Param("friends") String friends);

    //获得聊天历史内容
    @Options(useCache = false,flushCache = Options.FlushCachePolicy.TRUE)
    @Select("select * from user.${table}")
    Message[] getChatHistory(String table);


}
