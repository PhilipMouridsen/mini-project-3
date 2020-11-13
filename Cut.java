import java.net.InetAddress;

public class Cut extends Message {
    
    private static final long serialVersionUID = 7695714241469497637L;

    public Cut(int senderPort, InetAddress senderIP) {
        this.senderPort = senderPort;
        this.senderIP = senderIP;
        this.type = MessageType.CUT;
    }

}
