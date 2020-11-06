import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Node {

    int port;

    int key;
    String value;

    Socket socket;
    Socket left;

    Node(int port, int leftPort) throws UnknownHostException, IOException {
        socket = new Socket("localhost", port);
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