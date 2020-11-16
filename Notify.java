import java.net.InetAddress;

/**
 * A Notify object is used by Nodes to notify their left neighbors about their existence so that
 * they are able to close the ring on their left.
 */
public class Notify extends Message {
 
    private static final long serialVersionUID = 7182709924215053334L;

    String newNodeIP;
    int newNodePort;
    int newNodesLeftPort;

    int senderPort;
    String senderIP;

    Notify(String senderIP, int senderPort, String newNodeIP, int newNodePort, int newNodesLeftPort) {
        this.senderIP = senderIP;
        this.senderPort = senderPort;
        this.newNodeIP = newNodeIP;
        this.newNodePort = newNodePort;
        this.newNodesLeftPort = newNodesLeftPort;
        this.type = MessageType.NOTIFY;
    }
}
