import java.net.InetAddress;

public class MakeLeft extends Message {

    private static final long serialVersionUID = 1L;

    public MakeLeft(InetAddress senderIP, int senderPort, InetAddress neighborIP, int neighborPort) {
        this.senderPort = senderPort;
        this.senderIP = senderIP;
        this.neighborPort = neighborPort;
        this.neighborIP = neighborIP;
        this.type = MessageType.MAKELEFT;
    }
}
