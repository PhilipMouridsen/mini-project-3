import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Node {

    int port;
    int leftPort;
    int rightPort;

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

        // Make a ring.
        left = new Socket("localhost", leftPort);
        out = new ObjectOutputStream(left.getOutputStream());
        out.writeObject(new Notify(port, InetAddress.getLocalHost(), this.port, this.leftPort)); // Notify of my Node
                                                                                                 // port.
        left.close();

        startNode();
    }

    Node(int port) throws UnknownHostException, IOException {
        this.port = port;
        startNode();
    }

    public void startNode() throws UnknownHostException, IOException {

        socket = new ServerSocket(port);

        while (true) {

            System.out.println("Node " + port + " left node is connected to " + leftPort);
            // System.out.println("Node " + port + " right node is connected to " +
            // rightPort);

            System.out.println("Waiting for PUT or GET requests...");
            Socket connection = socket.accept(); // Waits here until a client connects.
            in = new ObjectInputStream(connection.getInputStream());

            try {

                Message incoming = (Message) in.readObject();

                // Connect left node with right node.
                if (incoming.type == Message.MessageType.NOTIFY) {

                    Notify notify = (Notify) incoming;
                    int newNodePort = notify.newNodePort;
                    int newNodesLeftPort = notify.newNodesLeftPort;


                    // TODO: Also set the right ports.

                    // Case when connecting the second node.
                    if (leftPort == 0) {
                        leftPort = newNodePort;
                        rightPort = incoming.senderPort;
                    }

                    if (leftPort == newNodesLeftPort) {
                        leftPort = newNodePort;
                    }

                    // Case when connecting the third, fourth etc.
                    if (leftPort != 0 && leftPort != newNodePort) {

                            left = new Socket("localhost", leftPort);
                            out = new ObjectOutputStream(left.getOutputStream());
                            out.writeObject(new Notify(incoming.senderPort, 
                                                        InetAddress.getLocalHost(),
                                                        notify.newNodePort, 
                                                        notify.newNodesLeftPort));
                            left.close();
                    }

                }

                if (incoming.type == Message.MessageType.GET) {

                    System.out.println(incoming.type);
                    System.out.println("Key: " + incoming.key);
                    System.out.println("GetClient: " + incoming.senderIP + " : " + incoming.senderPort);

                    // If this is the key from the GET request, write back a put.
                    if (incoming.key == this.key) {

                        // Close what's already going on.
                        connection.close();

                        Socket sendback = new Socket(incoming.senderIP, incoming.senderPort);
                        // Send back to the original GET client.
                        out = new ObjectOutputStream(sendback.getOutputStream());
                        out.writeObject(new Put(this.key, this.value));
                        System.out.println("Sent a PUT to " + sendback.getPort());
                        sendback.close();

                    } else {

                        try {
                            System.out.println("Forwarding left...");
                            left = new Socket("localhost", leftPort);
                            out = new ObjectOutputStream(left.getOutputStream());
                            out.writeObject(incoming);
                            left.close();
                        } catch (ConnectException e) {
                            System.out.println("Forwarding right...");
                            right = new Socket("localhost", rightPort);
                            out = new ObjectOutputStream(right.getOutputStream());
                            out.writeObject(incoming);
                            right.close();
                        }
                            // System.out.println("Forwarding right...");
                            // right = new Socket("localhost", rightPort);
                            // out = new ObjectOutputStream(right.getOutputStream());
                            // out.writeObject(incoming);
                            // right.close();

                    }

                }

                // If a PUT is received, just update the node.
                // Also send to neighbor?
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