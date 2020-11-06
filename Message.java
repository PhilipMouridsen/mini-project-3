import java.io.Serializable;

public class Message implements Serializable {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    enum MessageType {
        PUT,
        GET
    }
    
    int key;
    String value;
    MessageType messageType;
}
