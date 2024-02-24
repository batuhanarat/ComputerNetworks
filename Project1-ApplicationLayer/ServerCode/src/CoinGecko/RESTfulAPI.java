package CoinGecko;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Abdulrezzak Zekiye
 */

public class RESTfulAPI {


    public JSONArray getNFTbyId(String id) throws IOException{
        URL url = new URL("https://api.coingecko.com/api/v3/nfts/"+id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();
        if (responseCode == 429) {
            throw new IOException("Your api calls should not exceed 50 per minute");
        }

        String inline = "[";
        Scanner scanner = new Scanner(url.openStream());
        while (scanner.hasNext()) {
            inline += scanner.nextLine();
        }
        inline += "]" ;

        JSONArray jsonObject = new JSONArray(inline);
        return jsonObject;

    }

    public JSONArray getListOfNFTs() throws IOException {

        URL url = new URL("https://api.coingecko.com/api/v3/nfts/list");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();
        if (responseCode == 429) {
            throw new IOException("Your api calls should not exceed 50 per minute");
        }
        String inline = "";
        Scanner scanner = new Scanner(url.openStream());
        while (scanner.hasNext()) {
            inline += scanner.nextLine();
        }
        scanner.close();
        System.out.println("----------------------");

        JSONArray jsonObject = new JSONArray(inline);
        return jsonObject;


    }



}
