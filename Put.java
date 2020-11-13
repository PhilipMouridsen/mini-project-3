import java.io.Serializable;

public class Put extends Message {
    
    private static final long serialVersionUID = 7695714241469997637L;

    public Put(int key, String value) {
        this.key = key;
        this.value = value;
        this.type = MessageType.PUT;
    }
    
}