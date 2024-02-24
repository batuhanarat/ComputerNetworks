package MultithreadedServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class NFTNetServer {

    //Static variables
    public static final int DEFAULT_SERVER_PORT = 4447;

    private int port;
    private  ServerSocket welcomingSocket;

    public NFTNetServer(int port) throws IOException  {
        this.port = port;
        welcomingSocket = new ServerSocket(port);
        System.out.println("Opened up a server socket on " + Inet4Address.getLocalHost());

        while(true) {
            listenForConnection();
        }
    }

    public void listenForConnection()  {

        Socket s;
        try {
            s = welcomingSocket.accept(); //this is blocking call, it opens new socket per connection
            System.out.println("A connection was established with a client on the address of " + s.getRemoteSocketAddress());
            s.setSoTimeout(60000);
            ServerThread serverThread = new ServerThread(s); //per connection, it executes new thread to make server multithreaded
            serverThread.start();
        }
        catch (IOException e) {
            System.err.println(e.getStackTrace());
        }

    }



}
