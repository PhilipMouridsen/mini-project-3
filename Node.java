import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Node {

    String nodeIP;
    int port;

    String leftIP;
    int leftPort;

    int key;
    String value;

    ServerSocket socket;
    Socket left;
    Socket right;

    ObjectOutputStream out;
    ObjectInputStream in;

    Node(String nodeIP, int port, String leftIP, int leftPort) throws UnknownHostException, IOException {
        
        this.nodeIP = nodeIP;
        this.port = port;

        this.leftIP = leftIP;
        this.leftPort = leftPort;

        // Make a ring, by notifying the left node of this new node.
        left = new Socket(leftIP, leftPort);
        out = new ObjectOutputStream(left.getOutputStream());
        out.writeObject(new Notify(port, this.port, this.leftPort));
        left.close();

        startNode();
    }

    Node(String nodeIP, int port) throws UnknownHostException, IOException {
        this.nodeIP = nodeIP;
        this.port = port;
        startNode();
    }

    public void startNode() throws UnknownHostException, IOException {

        socket = new ServerSocket(port);

        // Keep listening for requests, either NOTIFY, GET or PUT.
        while (true) {

            System.out.println(
                    "Node " + port + " left node is connected to " + leftPort + " at " + socket.getInetAddress());

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

                    // Case when connecting the second node.
                    if (leftPort == 0) {
                        leftPort = newNodePort;
                    }

                    // If the leftPort equals the port that the new node connected to on the left,
                    // then change the leftPort to the new nodes port.
                    // This essentially splits the ring and inserts the new node.
                    if (leftPort == newNodesLeftPort) {
                        leftPort = newNodePort;
                    }

                    // Case when connecting the third, fourth etc. Keeps notifying the socket to the
                    // left, until the ring has closed.
                    if (leftPort != 0 && leftPort != newNodePort) {

                        left = new Socket(leftIP, leftPort);
                        out = new ObjectOutputStream(left.getOutputStream());
                        out.writeObject(new Notify(notify.senderPort, notify.newNodePort, notify.newNodesLeftPort));
                        left.close();
                    }

                }

                if (incoming.type == Message.MessageType.GET) {

                    Get get = (Get) incoming;

                    System.out.println(get.type);
                    System.out.println("Key: " + get.key);
                    System.out.println("GetClient: " + get.senderIP + " : " + get.senderPort);

                    // If this is the key from the GET request, write back a put.
                    if (incoming.key == this.key) {

                        // Close what's already going on.
                        connection.close();

                        // Send back to the original GET client.
                        Socket sendback = new Socket(get.senderIP, get.senderPort);
                        out = new ObjectOutputStream(sendback.getOutputStream());
                        out.writeObject(new Put(this.key, this.value));
                        System.out.println("Sent a PUT to " + sendback.getPort());
                        sendback.close();

                    } else {

                        // IF: We are not back at the node which sent the GET, 
                        // forward the GET left.
                        // ELSE: Send a NOT FOUND back for the key.
                        if (this.leftPort != get.firstNodePort) {

                            System.out.println("Forwarding left...");
                            left = new Socket(leftIP, leftPort);
                            out = new ObjectOutputStream(left.getOutputStream());
                            out.writeObject(incoming);
                            left.close();

                        } else {
                            // Send back to the original GET client.
                            Socket sendback = new Socket(get.senderIP, get.senderPort);
                            out = new ObjectOutputStream(sendback.getOutputStream());
                            out.writeObject(new Put(get.key, "NOT FOUND"));
                            System.out.println("Sent a PUT to " + sendback.getPort());
                            sendback.close();
                        }

                    }

                }

                // If a PUT is received, just update this node.
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
        int leftPort;
        String leftIP;

        // Use different constructor overloads.
        try {
            String nodeIP = args[0];
            int port = Integer.parseInt(args[1]);
            if (args.length > 2) {
                leftIP = args[2];
                leftPort = Integer.parseInt(args[3]);
                node = new Node(nodeIP, port, leftIP, leftPort);
            } else {
                node = new Node(nodeIP, port);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
