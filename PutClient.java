import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class PutClient {

    public static void main(String[] args) throws UnknownHostException, IOException {
        
        int key = Integer.parseInt(args[0]);
        String value = args[1];

        String ip = args[2];
        int port = Integer.parseInt(args[3]);
        Socket socket = new Socket(ip, port);
        
        // Write the key-value pair to the node at the specified address and port.
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(new Put(key, value));

        socket.close();
    }

}
