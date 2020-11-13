import java.io.Serializable;
import java.net.InetAddress;

public abstract class Message implements Serializable {
    
    private static final long serialVersionUID = -696723032569395953L;

    protected int senderPort;
    protected InetAddress senderIP;
    protected int key;
    protected String value;
    protected MessageType type;

    enum MessageType {
        PUT,
        GET,
        NOTIFY,
        CUT
    }
    
}