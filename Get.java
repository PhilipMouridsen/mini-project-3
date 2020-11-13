import java.net.*;
import java.io.*;

class Get implements Serializable {
    private static final long serialVersionUID = 1L;
    private int key;
    private int port;
    private InetAddress ip;

    public Get(InetAddress ip, int port, int key) throws IOException {
        this.key = key;
        this.ip = ip;
        this.port = port;
    }

    public int getKey() {
        return this.key;
    }

    public Socket getCon() {
        return this.con;
    }
}