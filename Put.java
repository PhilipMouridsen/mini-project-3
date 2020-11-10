import java.io.*;
import java.net.*;

class Put implements Serializable {
    private static final long serialVersionUID = 1L;
    private int key;
    private String value;
    private Socket con; //Socket of a specific node

    public Put(Socket connection, int key, String value) throws IOException {
        this.key = key;
        this.value = value;
        this.con = connection;
      /*  ObjectOutputStream out = new ObjectOutputStream(con.getOutputStream());
        out.writeObject(this);
        out.flush();
        out.close();    */
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public Socket getConnection() {
        return this.con;
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        int key = Integer.parseInt(args[1]);
        String value = args[2];
    }
}
