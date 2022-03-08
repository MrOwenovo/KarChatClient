package KarChat.Server.DataBase.Entry;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 发送的请求信息
 */
@Data
@AllArgsConstructor
public class Post {
    String post;  //请求人
    String geter; //被请求人
}
