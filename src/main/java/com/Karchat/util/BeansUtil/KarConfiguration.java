package com.Karchat.util.BeansUtil;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.util.Timer;


/**
 * SpringBoot beans配置类
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScans({
        @ComponentScan(value = "com.Karchat.entity"),
        @ComponentScan(value = "com.Karchat.service"),
        @ComponentScan(value = "com.Karchat.util"),
        @ComponentScan(value = "com.Karchat.util.Controller"),
        @ComponentScan(value = "com.Karchat.util.ComponentUtil.CompositeComponent"),
        @ComponentScan(value = "com.Karchat.view"),
        @ComponentScan(value = "com.Karchat.dao")
        })
@MapperScan("com.Karchat.dao.mapper")
public class KarConfiguration {

    @Bean
    public DataSource dataSource(){
//        return new PooledDataSource("com.mysql.cj.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/user", "root", "lmq1226lmq"
////        "com.mysql.cj.jdbc.Driver",
////                "jdbc:mysql://geoffyli.mysql.rds.aliyuncs.com:3306/user", "k_administrator", "Kk123456"
//                );
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://geoffyli.mysql.rds.aliyuncs.com:3306/user");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("k_administrator");
        dataSource.setPassword("Kk123456");
        return dataSource;
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/user");
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUsername("root");
//        dataSource.setPassword("lmq1226lmq");
//        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean SqlSessionFactoryBean(@Autowired DataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean;
    }

    @Bean
    @Scope("prototype")
    public Timer timer() {
        Timer timer = new Timer();
        return timer;
    }


}
