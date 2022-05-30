package KarChat.Server.DataBase.Entry;

import lombok.Data;

/**
 * 聊天信息
 */
@Data
public class Message {
    String user1;  //用户1
    String user2;  //用户2
    String message;  //发送的信息
}
