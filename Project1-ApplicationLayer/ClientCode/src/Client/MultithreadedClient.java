package Client;

import java.util.Scanner;

public class MultithreadedClient {

    public static void main(String[] args) {

        ConnectionToServer connection = new ConnectionToServer(ConnectionToServer.DEFAULT_SERVER_ADDRESS,
                ConnectionToServer.DEFAULT_SERVER_PORT);
        connection.connect();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a message for the server (type 'bye' to quit, 'help' for commands): ");
        String message;

        while(!(message =scanner.nextLine()).equals("bye")) {

            connection.sendMessageToServer(message);
            StringBuilder responseBuilder = new StringBuilder();
            String response;

            while(!(response = connection.getMessageFromServer()).equals("END_OF_RESPONSE")) {

                if(response.equals("TIME_OUT")) {
                    System.err.println("Client socket is timed out for being unresponsive 1 minute.");
                    connection.closeConnection();
                    System.exit(0);
                }

                responseBuilder.append(response).append("\n");
                }


                System.out.println(responseBuilder.toString());
                System.out.println("Enter a message for the server (type 'bye' to quit, 'help' for commands): ");



        }
        scanner.close();
        connection.closeConnection();




    }
}
