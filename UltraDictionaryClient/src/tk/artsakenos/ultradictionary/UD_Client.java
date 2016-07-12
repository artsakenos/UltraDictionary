/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.artsakenos.ultradictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrea.Addis
 */
public class UD_Client {

    private static final String url = "http://drupatest.dev/aa_ud/";

    public static String set(String key, String value, String token) {
        try {
            value = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UD_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        String call = url + String.format("?COM=SET&KEY=%s&VAL=%s&TOK=%s", key, value, token);
        try {
            return UD_Client.getTextFromUrl(call);
        } catch (Exception ex) {
            Logger.getLogger(UD_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String get(String key, String token) {
        String call = url + String.format("?COM=GET&KEY=%s&TOK=%s", key, token);
        try {
            return UD_Client.getTextFromUrl(call);
        } catch (Exception ex) {
            Logger.getLogger(UD_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String lis(String token) {
        String call = url + String.format("?COM=LIS&TOK=%s", token);
        try {
            return UD_Client.getTextFromUrl(call);
        } catch (Exception ex) {
            Logger.getLogger(UD_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String cal(String key, String callback, String token) {
        String call = url + String.format("?COM=CAL&KEY=%s&VAL=%s&TOK=%s", key, callback, token);
        try {
            return UD_Client.getTextFromUrl(call);
        } catch (Exception ex) {
            Logger.getLogger(UD_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public static String getTextFromUrl(String url) throws MalformedURLException, IOException {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine).append("\n");
        }

        in.close();
        if (response.length() < 1) {
            return "";
        }
        return response.deleteCharAt(response.length() - 1).toString();
    }

}
