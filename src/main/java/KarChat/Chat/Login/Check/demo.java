package KarChat.Chat.Login.Check;

public class demo {
    public static void main(String[] args) {
        sendEmail sendEmail=new sendEmail();
        //设置要发送的邮箱
        sendEmail.setReceiveMailAccount("lmq122677@qq.com");
        try {
            sendEmail.Send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
