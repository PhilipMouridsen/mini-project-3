import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class PutClient {

    public void put(int key, String value, int port) {

    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        
        int key = Integer.parseInt(args[0]);
        String value = args[1];
        int port = Integer.parseInt(args[2]);
        Socket socket = new Socket("localhost", port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        out.writeObject(new Message(1, "A", Message.MessageType.PUT));
    }

}
