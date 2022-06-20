package com.Karchat;

import com.Karchat.util.Constant;
import com.Karchat.util.Controller.Controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
public class KarChatSpringBootApplication {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("sun.java2d.noddraw", "true");  //防止输入法输入时白屏，禁用DirectDraw
//        SpringApplication.run(KarChatSpringBootApplication.class, args);
////        SpringApplicationBuilder builder = new SpringApplicationBuilder(KarChatSpringBootApplication.class);
////        builder.headless(false).run(args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(KarChatSpringBootApplication.class);
        builder.headless(false).run(args);
        Constant.context.getBean(Controller.class).start();
    }

}
