import java.net.InetAddress;

public class Get extends Message {
    
    private static final long serialVersionUID = -6332108891895842289L;

    public Get(int key, int senderPort, InetAddress senderIP, MessageType type) {
        this.key = key;
        this.senderPort = senderPort;
        this.senderIP = senderIP;
        this.type = type;
    }
    
}