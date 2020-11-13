import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class GetClient {
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {

        int key = Integer.parseInt(args[0]);
        int portNode = Integer.parseInt(args[1]);
        Socket socketOut = new Socket("localhost", portNode);
        ObjectOutputStream out = new ObjectOutputStream(socketOut.getOutputStream());

        ServerSocket serverSocket = new ServerSocket(0);    //argument 0 to find an available port
        InetAddress ip = serverSocket.getInetAddress();
        int port = serverSocket.getLocalPort();
        out.writeObject(new Get(ip, port, key));
        out.flush();
        out.close();

        while(true) {
            System.out.println("listening...");
            Socket socketIn = serverSocket.accept();
            ObjectInputStream in = new ObjectInputStream(socketIn.getInputStream());
            Object obj = in.readObject();
            if(obj instanceof Put) {
                Put put = (Put) obj;
                System.out.printf("Key value pair: (%d, %s)", put.getKey(), put.getValue());
            }
        }
    }
}