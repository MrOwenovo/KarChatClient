package KarChat.Server.DataBase.Entry;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 历史记录对象，用来记录历史记录
 */
@Data
@AllArgsConstructor
public class History {
    public String type;  //有post和get两种状态
    public String message;  //发送的信息
}
