package KarChat.Server.DataBase.Entry;

import lombok.Data;

/**
 * 用户实体类
 */
@Data
public class User {
    String username;  //用户名
    String password;  //密码
    int state ; //状态
}
