import java.util.Scanner;

/**
 * Copyright [Yahya Hassanzadeh-Nazarabadi]

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
public class Main
{
    public final static String TLS_SERVER_ADDRESS = "localhost";
    public final static String MESSAGE_TO_SERVER = "68665 Comp416";
    public final static int TLS_SERVER_PORT = 4446;
    public static long startTimeMillis;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which connection do you want to establish ? Write 's' for secure connection, 'i' for insecure connection: ");

        String message;
        if((message =scanner.nextLine()).equals("s")) {
            System.out.println("s is pressed");
            startTimeMillis = System.currentTimeMillis();

            connectToSecureConnection();


        } else if((message.equals("i"))) {
            System.out.println("i is pressed");
            startTimeMillis = System.currentTimeMillis();

            // Your code here
            // ...

            // End time in milliseconds

            connectToInsecureConnection();
        }

        scanner.close();

    }

    private static void connectToSecureConnection() {
         /*
        Creates an SSLConnectToServer object on the specified server address and port
         */
        SSLConnectToServer sslConnectToServer = new SSLConnectToServer(TLS_SERVER_ADDRESS, TLS_SERVER_PORT);

        /*
        Connects to the server
         */
        sslConnectToServer.Connect();

        /*
        Sends a message over SSL socket to the server and prints out the received message from the server
         */
        System.out.println(sslConnectToServer.SendForAnswer(MESSAGE_TO_SERVER));
         /*
        Disconnects from the SSL server
         */
        long endTimeMillis = System.currentTimeMillis();

        // Calculate tsime difference in milliseconds
        long timeDiffMillis = (endTimeMillis - startTimeMillis);
        System.out.println("Time taken in milliseconds: " + timeDiffMillis);
        sslConnectToServer.Disconnect();

    }

    private static void connectToInsecureConnection() {
        ConnectionToServer connectionToServer = new ConnectionToServer(ConnectionToServer.DEFAULT_SERVER_ADDRESS, ConnectionToServer.DEFAULT_SERVER_PORT);
        connectionToServer.Connect();
        System.out.println(connectionToServer.SendForAnswer(MESSAGE_TO_SERVER));
        long endTimeMillis = System.currentTimeMillis();

        // Calculate time difference in milliseconds
        long timeDiffMillis = endTimeMillis - startTimeMillis;
        System.out.println("Time taken in milliseconds: " + timeDiffMillis);
        connectionToServer.Disconnect();

    }
}
