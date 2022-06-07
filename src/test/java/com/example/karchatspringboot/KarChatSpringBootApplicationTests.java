package com.example.karchatspringboot;

import com.Karchat.service.ChatService;
import com.Karchat.util.BeansUtil.KarConfiguration;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = KarConfiguration.class)
@SpringBootTest
class KarChatSpringBootApplicationTests {

//    @Resource
//    ChatService chatService;

    @Test
    void contextLoads() {

    }

}
