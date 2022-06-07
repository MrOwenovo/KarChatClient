package com.Karchat.util.DataBaseUnit;

import com.Karchat.dao.mapper.AddFriendMapper;
import com.Karchat.dao.mapper.ChatMapper;
import com.Karchat.dao.mapper.LoginMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

/**
 * 用此类创建SqlSession会话
 */
public class MybatisUnit {
    private static SqlSessionFactory sqlSessionFactory ;

    private static LoginMapper mapper1;

    private static AddFriendMapper mapper2;

    private static ChatMapper mapper3;
    static SqlSession session;

    static{
        String path = "mybatis-config.xml";
        try {
            InputStream in =Resources.getResourceAsStream(path);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        session = MybatisUnit.getSession(true);
        mapper1 = session.getMapper(LoginMapper.class);
        mapper2 = session.getMapper(AddFriendMapper.class);
        mapper3 = session.getMapper(ChatMapper.class);

    }

    /**
     * 得到一个flag是否开启事务的Session会话
     * @param flag
     * @return
     */
    public static SqlSession getSession(boolean flag) {
        return sqlSessionFactory.openSession(flag);
    }

    /**
     * 用消费者系统取一个session生成一个mapper对象，分配给每一个要用的消费者
     * @param comsumer
     */
    public synchronized static void doSqlWork(Consumer<LoginMapper> comsumer) {  //加入线程锁，防止多个客户端争夺查询
              comsumer.accept(session.getMapper(LoginMapper.class));
    }

    /**
     * 用消费者系统取一个session生成一个mapper对象，分配给每一个要用的消费者
     * @param comsumer
     */
    public synchronized static void doAddFriendWork(Consumer<AddFriendMapper> comsumer) {  //加入线程锁，防止多个客户端争夺查询
             comsumer.accept(session.getMapper(AddFriendMapper.class));
    }
    /**
     * 用消费者系统取一个session生成一个mapper对象，分配给每一个要用的消费者
     * @param comsumer
     */
    public synchronized static void doChatWork(Consumer<ChatMapper> comsumer) {  //加入线程锁，防止多个客户端争夺查询
             comsumer.accept(session.getMapper(ChatMapper.class));
    }
}
