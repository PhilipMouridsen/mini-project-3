public class Notify extends Message {
 
    private static final long serialVersionUID = 7182709924215053334L;

    int newNodePort;

    String newNodeIP;
    int newNodesLeftPort;

    String senderIP;
    int senderPort;

    Notify(String senderIP, int senderPort, String newNodeIP, int newNodePort, int newNodesLeftPort) {
        this.senderIP = senderIP;
        this.senderPort = senderPort;
        this.newNodePort = newNodePort;

        this.newNodeIP = newNodeIP;
        this.newNodesLeftPort = newNodesLeftPort;

        this.type = MessageType.NOTIFY;
    }
}
