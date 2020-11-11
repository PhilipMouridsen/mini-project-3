import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class GetClient {

    public static void main(String[] args) throws UnknownHostException {

        int key = Integer.parseInt(args[0]);
        int toPort = Integer.parseInt(args[1]);
        int fromPort = Integer.parseInt(args[2]);

        try {

            // Open a connection that can listen for backgoing PUT from any node in the
            // network.
            ServerSocket serverSocket = new ServerSocket(fromPort);
            Socket socket = new Socket("localhost", toPort);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            // Use fromPort to let the Node get back eventually.
            out.writeObject(new Get(key, fromPort, Message.MessageType.GET));
            socket.close();

            System.out.println("Waiting for PUT...");
            Socket connection = serverSocket.accept();
            ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
            Message incoming = (Message) in.readObject();
            System.out.println("Received key-value pair: " + incoming.key + " : " + incoming.value);
            serverSocket.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
