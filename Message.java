import java.io.Serializable;

public abstract class Message implements Serializable {
    
    private static final long serialVersionUID = -696723032569395953L;

    protected int key;
    protected String value;
    protected MessageType type;

    enum MessageType {
        PUT,
        GET,
        NOTIFY,
        COPY
    }
    
}