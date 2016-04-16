/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.artsakenos.ultradictionary.client;

import java.io.IOException;

/**
 *
 * @author Andrea.Addis
 */
public class UD_ClientTest {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        UD_LocalCallBack udlc = new UD_LocalCallBack("/ud_callback", 8000) {

            @Override
            public String onCallBack(String uriParameters) {
                String message = "The parameters " + uriParameters + " have been passed, take action!";
                System.out.println("DEBUG: " + message);
                return message;
            }
        };

        // Set a Key / Value
        UltraDictionaryClient.set("pippo_key", "pippo_value", "12345678");

        // Read that Entry
        String get = UltraDictionaryClient.get("pippo_key", "12345678");
        System.out.println("GET = " + get);

        // Read the list of entries
        System.out.println(UltraDictionaryClient.lis("12345678"));

        // Set a callback
        System.out.println(UltraDictionaryClient.cal("pippo_key", "http://localhost:8000/ud_callback/morepaths/?pippo=hasbeenset", "12345678"));

        // When pippo_key will be set again, that callback will be called.
        UltraDictionaryClient.set("pippo_key", "pippo_value", "12345678");
    }

}
