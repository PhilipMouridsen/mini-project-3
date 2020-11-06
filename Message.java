import java.io.Serializable;

public class Message implements Serializable {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public enum MessageType {
        PUT,
        GET
    }
    
    int key;
    String value;
    MessageType messageType;

    Message(int key, String value, MessageType messageType) {
        this.key = key;
        this.value = value;
        this.messageType = messageType;
    }
}
