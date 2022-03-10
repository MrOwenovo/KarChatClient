package KarChat.Server.DataBase.Entry;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Friends {
    int index;
    String friends;
    String chatLocation;
}
