package com.company;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpJSONService {

    private final String USER_AGENT = "Mozilla/5.0";
    private JSONParsing engine = new JSONParsing();

    // HTTP GET request
    public Map sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        System.out.println("\n'GET' request sent to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        StringBuffer response = new StringBuffer();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Print result
        // System.out.println(response.toString());

        // Parse JSON result
        JSONParsing engine = new JSONParsing();
        return engine.parseJSON(response.toString());
    }

}
