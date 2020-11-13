import java.net.InetAddress;

public class Notify extends Message {
 
    /**
     *
     */
    private static final long serialVersionUID = 7182709924215053334L;

    Notify(int senderPort, InetAddress senderIP) {
        this.senderPort = senderPort;
        this.senderIP = senderIP;
        this.type = MessageType.NOTIFY;
    }
}
