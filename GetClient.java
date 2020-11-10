import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class GetClient {

    public static void main(String[] args) throws UnknownHostException {

        try {

            int key = Integer.parseInt(args[0]);
            int senderPort = Integer.parseInt(args[1]);
            Socket socket = new Socket("localhost", senderPort);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new Get(key, senderPort, Message.MessageType.GET));

            System.out.println("Waiting for PUT...");

            // socket = new Socket("localhost", senderPort);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Message incoming = (Message) in.readObject();
            System.out.println("Received key-value pair: " + incoming.key + " : " + incoming.value);
            socket.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
