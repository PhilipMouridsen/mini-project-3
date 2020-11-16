import java.net.InetAddress;

public class Get extends Message {
    
    private static final long serialVersionUID = -6332108891895842289L;
    
    int senderPort;
    String senderIP;
    int firstNodePort;

    public Get(int key, int senderPort, String senderIP, int firstNodePort) {
        this.key = key;
        this.senderPort = senderPort;
        this.senderIP = senderIP;
        this.firstNodePort = firstNodePort;
        this.type = MessageType.GET;
    }
    
}