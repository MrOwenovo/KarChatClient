package KarChat.Server.DataBase.Entry;

import KarChat.Chat.Helper.GetPicture;
import lombok.Data;

import java.awt.image.BufferedImage;

@Data
public class Icon {
    String iconString;
    BufferedImage icon;
}
