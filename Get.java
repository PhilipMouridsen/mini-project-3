import java.net.*;
import java.io.*;

class Get implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Socket con;
    private int key;

    public Get(Socket connection, int key) throws IOException {
        this.con = connection;
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public Socket getCon() {
        return this.con;
    }
}