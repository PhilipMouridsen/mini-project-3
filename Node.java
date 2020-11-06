import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Node {

    int key;
    String value;

    Socket socket;
    Socket left;

    ObjectOutputStream out;
    ObjectInputStream in;

    Node(int port, int leftPort) throws UnknownHostException, IOException {

        while (true) {
            socket = new Socket("localhost", port);
            left = new Socket("localhost", leftPort);

            in = new ObjectInputStream(left.getInputStream());

            try {
                Object incoming = in.readObject();
                // TODO: Determine if PUT or GET request, and do something.
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    Node(int port) throws UnknownHostException, IOException {
        socket = new Socket("localhost", port);
    }

    public static void main(String[] args) {

        try {
            Node node = new Node(12345);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}