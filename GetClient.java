import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class GetClient {

    public static void main(String[] args) throws UnknownHostException, IOException {

        int key = Integer.parseInt(args[0]);
        int port = Integer.parseInt(args[1]);
        Socket socket = new Socket("localhost", port);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(new Get(key, Message.MessageType.GET));

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        try {
            Message incoming = (Message) in.readObject();
            System.out.println("Received key-value pair: " + incoming.key + " : " + incoming.value);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
    }

}
