package Tomcat.DataBase;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtil {
    private static SqlSessionFactory sqlSessionFactory;
    static{
        String path = "mybatis-config.xml";
        try{
            InputStream in = Resources.getResourceAsStream(path);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSession getSession(boolean flag) {
        return sqlSessionFactory.openSession(flag);
    }

//    public static void doSqlWork(Consumer<UserMapper> consumer) {
//        try (SqlSession session=getSession(true)){
//            UserMapper mapper = session.getMapper(UserMapper.class);
//            consumer.accept(mapper);
//
//        }
//    }
}
