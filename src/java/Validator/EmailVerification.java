/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ileven
 */
public class EmailVerification {

    private final String apikey = "2bc7fe3d01835ab0b47144fd1b4694ce";

    public String checkEmail(String email) throws Exception {
        String url = "http://apilayer.net/api/check?access_key=" + apikey + "&email=" + email + "&smtp=1&format=1";
        URL urlobj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) urlobj.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", "Mozilla/17.0");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        List<String> response = new ArrayList<>();
        
        while ((inputLine = in.readLine()) != null) {

            String a = inputLine.replaceAll("\"", "");
            String x = a.replaceAll(",", "");
            response.add(x);

        }
        in.close();
        
        return response.get(7).split(":")[1]+":"+response.get(12).split(":")[1];
//        return response.toString();
    }
    
}
