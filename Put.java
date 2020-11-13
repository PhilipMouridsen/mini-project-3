import java.io.*;
import java.net.*;

class Put implements Serializable{
    private static final long serialVersionUID = -473740044596583325L;
    private int key;
    private String value;
    //private Socket con; //Socket of a specific node

    public Put(int key, String value) throws IOException {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
