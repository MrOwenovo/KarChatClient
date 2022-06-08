package com.Karchat;

import com.Karchat.util.Constant;
import com.Karchat.util.Controller.Controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class KarChatSpringBootApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(KarChatSpringBootApplication.class, args);
//        SpringApplicationBuilder builder = new SpringApplicationBuilder(KarChatSpringBootApplication.class);
//        builder.headless(false).run(args);
        Constant.context.getBean(Controller.class).start();
    }

}
