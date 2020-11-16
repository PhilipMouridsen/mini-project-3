import java.net.InetAddress;

public class MakeRight extends Message {

    private static final long serialVersionUID = 1L;

    public MakeRight(InetAddress ip, int port) {
        this.senderPort = port;
        this.senderIP = ip;
        this.type = MessageType.MAKERIGHT;
    }
}
