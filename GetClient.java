import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class GetClient {

    public static void main(String[] args) throws UnknownHostException {

        try {

            int key = Integer.parseInt(args[0]);
            int toPort = Integer.parseInt(args[1]);
            int fromPort = Integer.parseInt(args[2]);
            Socket socket = new Socket("localhost", toPort);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            // Use fromPort to let the Node get back eventually.
            out.writeObject(new Get(key, fromPort, Message.MessageType.GET));
            socket.close();

            ServerSocket serverSocket = new ServerSocket(fromPort);
            System.out.println("Waiting for PUT...");

            Socket connection = serverSocket.accept();
            ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
            Message incoming = (Message) in.readObject();
            System.out.println("Received key-value pair: " + incoming.key + " : " + incoming.value);
            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
