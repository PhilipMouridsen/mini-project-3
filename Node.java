import java.util.*;
import java.net.*;
import java.io.*;

public class Node {
    private int myPort;
    private InetAddress myAddress;
    private ServerSocket nodeServer;
    private Socket left;
    private int key;
    private String value;

    
    public Node(int port, Socket left, int key, String value) throws IOException, ClassNotFoundException {
        this.myPort = port;
        nodeServer = new ServerSocket(this.myPort);
        this.myAddress = nodeServer.getInetAddress();
        this.key = key;
        this.value = value;
        this.left = left;    
    }

    public Node(int port,  int key, String value) throws IOException, ClassNotFoundException {
        this.myPort = port;
        nodeServer = new ServerSocket(this.myPort);
        this.myAddress = nodeServer.getInetAddress();
        this.key = key;
        this.value = value;

        while(true) {
            System.out.println("listening...");
            Socket con = nodeServer.accept();
            ObjectInputStream in = new ObjectInputStream(con.getInputStream());
            Object obj = in.readObject();
            
            if(obj instanceof Put) {
                //check if I am the node if not pass it onwards
                Put put = (Put) obj;
                Socket putCon = put.getConnection();
                if(myAddress.equals(putCon.getInetAddress()) && myPort == putCon.getLocalPort()) {
                    this.key = put.getKey();
                    this.value = put.getValue();
                } else { //send to left
                    ObjectOutputStream out = new ObjectOutputStream(left.getOutputStream());
                    out.writeObject(put);
                    out.flush();
                    out.close();
                }
                
            } else {
                Get get = (Get) obj;
                Socket getCon = get.getCon();
                if(myAddress.equals(getCon.getInetAddress()) && myPort == getCon.getLocalPort()) {

                } else { //send to left
                    ObjectOutputStream out = new ObjectOutputStream(left.getOutputStream());
                    out.writeObject(get);
                    out.flush();
                    out.close();
                }
            }
        }
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        int key = Integer.parseInt(args[1]);
        String value = args[2];
        try {
            Node node = new Node(port, key, value);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

