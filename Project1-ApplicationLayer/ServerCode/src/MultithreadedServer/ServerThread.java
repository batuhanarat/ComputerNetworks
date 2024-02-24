package MultithreadedServer;

import CoinGecko.RESTfulAPI;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread{

    private BufferedReader is;
    private PrintWriter os;
    private Socket s;

    private String line;

    public ServerThread(Socket s ) {
      this.s = s;
    }

    public void showWithIDResponse(PrintWriter os , JSONArray jsonObject) {
        for (int i = 0; i < jsonObject.length(); i++) {
            JSONObject obj = jsonObject.getJSONObject(i);

            String name = safeGetString(obj, "name");
            String asset_platform_id = safeGetString(obj, "asset_platform_id");
            JSONObject floor_price_json = safeGetJson(obj, "floor_price");
            String usd = safeGetString(floor_price_json, "usd");

            os.println( "Nft Name: " +name + "\t" + "Asset Platform Id: " +asset_platform_id  + "\t" +"Price in USD: " +usd);
        }
        os.println("END_OF_RESPONSE");
        os.flush();
    }

    public void listRequestResponse(PrintWriter os , JSONArray jsonObject) {
        for (int i = 0; i < jsonObject.length(); i++) {
            JSONObject obj = jsonObject.getJSONObject(i);

            String id = safeGetString(obj, "id");
            String contract_address = safeGetString(obj, "contract_address");
            String name = safeGetString(obj, "name");
            String platform_id = safeGetString(obj, "asset_platform_id");
            String symbol = safeGetString(obj, "symbol");

            os.println("Id: "+id + "\t" + "Name: " +name + "\t" +"Contract Address: " +contract_address + "\t" + "Platform Id: "+platform_id + "\t" + "Symbol: " +symbol +"\n");


        }
        os.println("END_OF_RESPONSE");
        os.flush();

    }
    private String safeGetString(JSONObject json, String key) {
        return json.has(key) && !json.isNull(key) ? json.get(key).toString() : "null";
    }
    private JSONObject safeGetJson(JSONObject json, String key) {
        return json.has(key) && !json.isNull(key) ? json.getJSONObject(key) : null;
    }

    public void closeSocket() {
        try {
            System.out.println("Closing the connection");
            if (is != null) {
                is.close();
                System.err.println("Socket Input Stream Closed");
            }
            if (os != null) {
                os.close();
                System.err.println("Socket Out Closed");
            }
            if (s != null) {
                s.close();
                System.err.println("Socket Closed");
            }

        }
        catch (IOException ie) {
            System.err.println("Socket Close Error");
        }
    }
    @Override
    public void run() {


        try {
            is = new BufferedReader(new InputStreamReader(s.getInputStream())); //prepare input for server
            os = new PrintWriter(s.getOutputStream()); //prepare output for server
            line = is.readLine();
            RESTfulAPI api = new RESTfulAPI();

            while (line.compareTo("bye") != 0) {

                if(line.compareTo("help") == 0) {
                    os.println("Use '-list' command to see all the NFTs in the CoinGecko platform. \n" +
                            "Use '-show id' to see specific nft's information." +
                            "You should replace the id by the corresponding id of the nft.\nUse 'bye' command to exit.");
                    os.println("END_OF_RESPONSE");
                    os.flush();

                }
                else if(line.compareTo("-list") == 0) {
                    listRequestResponse(os,api.getListOfNFTs());
                }
                else if(line.startsWith("-show")) {
                    String id =line.substring("-show ".length());

                        try {showWithIDResponse(os,api.getNFTbyId(id)); }
                        catch (IOException e) {
                            os.println("You entered a false id, check again");
                            os.println("END_OF_RESPONSE");
                            os.flush();
                        }

                }
                else{
                    os.println("You entered a wrong command in terms of protocol. Write 'help' to see commands");
                    os.println("END_OF_RESPONSE");
                    os.flush();
                }
                line = is.readLine();
            }
        }
        catch (NullPointerException e) {
            line = this.getName(); //reused String line for getting thread name
            System.err.println("Server Thread. Run.Client " + line + " Closed");
        }
        catch (SocketException e) {
            System.err.println(e.getStackTrace());
        }
        catch (SocketTimeoutException e) {
             System.err.println("Client socket is timed out for being unresponsive 1 minute.");
        }
        catch (IOException e) {
            if(e.getMessage() !=null) {
                System.err.println(e.getMessage());
            }else {
                throw new RuntimeException(e);

            }
        }
        finally {
            os.println("TIME_OUT");
            os.flush();
            closeSocket();

        }

    }

}
