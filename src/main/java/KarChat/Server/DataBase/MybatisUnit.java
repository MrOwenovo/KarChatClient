package KarChat.Server.DataBase;

import KarChat.Server.DataBase.Mapper.AddFriend;
import KarChat.Server.DataBase.Mapper.Chat;
import KarChat.Server.DataBase.Mapper.Login;
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

    private static Login mapper1;

    private static AddFriend mapper2;

    private static Chat mapper3;
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
        mapper1 = session.getMapper(Login.class);
        mapper2 = session.getMapper(AddFriend.class);
        mapper3 = session.getMapper(Chat.class);

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
    public synchronized static void doSqlWork(Consumer<Login> comsumer) {  //加入线程锁，防止多个客户端争夺查询
              comsumer.accept(session.getMapper(Login.class));
    }

    /**
     * 用消费者系统取一个session生成一个mapper对象，分配给每一个要用的消费者
     * @param comsumer
     */
    public synchronized static void doAddFriendWork(Consumer<AddFriend> comsumer) {  //加入线程锁，防止多个客户端争夺查询
             comsumer.accept(session.getMapper(AddFriend.class));
    }
    /**
     * 用消费者系统取一个session生成一个mapper对象，分配给每一个要用的消费者
     * @param comsumer
     */
    public synchronized static void doChatWork(Consumer<Chat> comsumer) {  //加入线程锁，防止多个客户端争夺查询
             comsumer.accept(getSession(true).getMapper(Chat.class));
    }
}
