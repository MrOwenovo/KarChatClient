package com.Karchat.dao.impl;

import com.Karchat.dao.Chat;
import com.Karchat.service.ViewServer;
import com.Karchat.util.ComponentUtil.CompositeComponent.MenuContent;
import com.Karchat.util.Constant;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

;
import static com.Karchat.util.Constant.addState;
import static com.Karchat.util.Constant.*;


@Service
public class ChatImpl implements Chat {



    @SneakyThrows
    @Override
    public synchronized String SendToDataSource(PrintStream out, BufferedReader buf, String message, String geter) {
        out.println("send");
        out.println(message);
        out.println(geter);
        out.println(Constant.usernameAll);
        return buf.readLine();
    }
}
