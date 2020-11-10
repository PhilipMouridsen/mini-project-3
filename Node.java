import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Node {

    int port;

    int key;
    String value;

    ServerSocket socket;
    Socket left;
    Socket right;

    ObjectOutputStream out;
    ObjectInputStream in;

    Node(int port, int leftPort) throws UnknownHostException, IOException {
        this.port = port;
        startNode();
    }

    Node(int port) throws UnknownHostException, IOException {
        this.port = port;
        startNode();
    }

    public void startNode() throws UnknownHostException, IOException {

        socket = new ServerSocket(port);

        while (true) {

            System.out.println("Waiting for PUT or GET requests...");
            Socket connection = socket.accept(); // Waits here until a client connects.
            in = new ObjectInputStream(connection.getInputStream());

            try {

                Message incoming = (Message) in.readObject();

                if (incoming.type == Message.MessageType.GET) {
                    out = new ObjectOutputStream(connection.getOutputStream());
                    out.writeObject(new Put(key, value, Message.MessageType.PUT));
                }

                if (incoming.type == Message.MessageType.PUT) {

                    System.out.println(incoming.type);
                    System.out.println(incoming.key);
                    System.out.println(incoming.value);

                    // Manipulate the node at this instance.
                    this.key = incoming.key;
                    this.value = incoming.value;
                }

                connection.close();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        Node node;
        int left;
        int right;

        try {
            int port = Integer.parseInt(args[0]);

            if (args.length > 1) {
                left = Integer.parseInt(args[1]);
                node = new Node(port);
            } else {
                node = new Node(port);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}