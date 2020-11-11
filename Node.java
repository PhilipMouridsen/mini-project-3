import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Node {

    int port;
    int leftPort;

    int key;
    String value;

    ServerSocket socket;
    Socket left;
    Socket right;

    ObjectOutputStream out;
    ObjectInputStream in;

    Node(int port, int leftPort) throws UnknownHostException, IOException {
        this.port = port;
        this.leftPort = leftPort;
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

                    System.out.println(incoming.type);
                    System.out.println("Key: " + incoming.key);
                    System.out.println("senderPort: " + incoming.senderPort);

                    // If this is the key from the GET request, write back a put.
                    if (incoming.key == this.key) {
                        
                        // Close what's already going on.
                        connection.close();

                        Socket sendback = new Socket("localhost", incoming.senderPort);
                        // Send back to the original GET client.
                        out = new ObjectOutputStream(sendback.getOutputStream());
                        out.writeObject(new Put(this.key, this.value, Message.MessageType.PUT));
                        System.out.println("Sent a PUT to " + sendback.getPort());
                        sendback.close();

                    } else {
                        // Else send the Get to the left.
                        System.out.println("Forwarding left...");
                        left = new Socket("localhost", leftPort);
                        out = new ObjectOutputStream(left.getOutputStream());
                        out.writeObject(incoming);
                        left.close();
                    }

                }

                // If a PUT is received, just update the node.
                if (incoming.type == Message.MessageType.PUT) {

                    System.out.println(incoming.type);
                    System.out.println("Key: " + incoming.key);
                    System.out.println("Value: " + incoming.value);

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
                node = new Node(port, left);
            } else {
                node = new Node(port);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}