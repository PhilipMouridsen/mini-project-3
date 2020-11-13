import java.net.InetAddress;

public class Notify extends Message {
 
    /**
     *
     */
    private static final long serialVersionUID = 7182709924215053334L;

    int previousPort;

    Notify(int senderPort, InetAddress senderIP, int previousPort) {
        this.senderPort = senderPort;
        this.senderIP = senderIP;
        this.previousPort = previousPort;
        this.type = MessageType.NOTIFY;
    }
}
