import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class GetClient {

    public static void main(String[] args) throws UnknownHostException {

        int key = Integer.parseInt(args[0]);
        int toPort = Integer.parseInt(args[1]);
        int senderPort = Integer.parseInt(args[2]);

        try {

            // Open a connection that can listen for backgoing PUT from any node in the
            // network.
            ServerSocket serverSocket = new ServerSocket(senderPort);

            // Open a connection to the node network.
            Socket socket = new Socket("localhost", toPort);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            // Include port and IP of the GetClient, so the node, which stores the key-value pair knows where to return it.
            out.writeObject(new Get(key, serverSocket.getLocalPort(), serverSocket.getInetAddress(), toPort));
            socket.close();

            // Wait for the connection.
            System.out.println("Waiting for PUT...");
            Socket connection = serverSocket.accept();

            // Read the PUT that was received, and print it.
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
