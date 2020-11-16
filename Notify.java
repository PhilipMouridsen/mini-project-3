import java.net.InetAddress;

public class Notify extends Message {
 
    private static final long serialVersionUID = 7182709924215053334L;

    int newNodePort;
    int newNodesLeftPort;

    int senderPort;
    String senderIP;

    Notify(int senderPort, String senderIP, int newNodePort, int newNodesLeftPort) {
        this.senderPort = senderPort;
        this.senderIP = senderIP;
        this.newNodePort = newNodePort;
        this.newNodesLeftPort = newNodesLeftPort;
        this.type = MessageType.NOTIFY;
    }
}
