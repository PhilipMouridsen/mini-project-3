public class Copy extends Message {
    
    private static final long serialVersionUID = 7695714241469997637L;

    public Copy(int key, String value) {
        this.key = key;
        this.value = value;
        this.type = MessageType.COPY;
    }
    
}