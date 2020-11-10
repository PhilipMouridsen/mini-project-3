import java.io.Serializable;

public class Get extends Message {
    
    /**
     *
     */
    private static final long serialVersionUID = -6332108891895842289L;

    public Get(int key, MessageType type) {
        this.key = key;
        this.type = type;
    }
    
}