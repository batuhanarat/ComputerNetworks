package Client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ConnectionToServer {
    //static variables
    protected static final int DEFAULT_SERVER_PORT = 4447;
    protected static final String DEFAULT_SERVER_ADDRESS = "localhost";


    private BufferedReader is;
    private PrintWriter os;
    private int port;
    private String address;

    private Socket socket;

    public ConnectionToServer(String address,int port) {
        this.address = address;
        this.port = port;
    }

    public void connect() {

        try {
            socket = new Socket(address, port);

            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = new PrintWriter(socket.getOutputStream());
            System.out.println("Client connected from 'local '" +socket.getLocalSocketAddress());
            System.out.println("Client connected from 'remote '" +socket.getRemoteSocketAddress());
        }
        catch (IOException e) {
            System.out.println("IOException at server address " + address + " port " + port);
        }
    }

    public void sendMessageToServer(String message) {
            os.println(message);
            os.flush();
    }

    public String getMessageFromServer() {
        String response = new String();

        try {
            response = is.readLine();
            return response;
        }

        catch(Exception e) {
            System.out.println("Exceptiondayım");

            System.out.println(e.toString());

            System.out.println(e.getClass().getName());
            System.out.println("Exception bitti");

            return "END_OF_RESPONSE";
        }

    }
    public void closeConnection() {
        try {
            if(is != null) {is.close();}
            if(os != null) {os.close();}
            if(socket != null) {socket.close();}
            System.out.println("ConnectionToServer.. Connection Closed");
        }
        catch(IOException e)
        {
            System.out.println(e.getStackTrace());
        }


    }
}