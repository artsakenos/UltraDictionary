/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.artsakenos.ultradictionary.UD_Test;

import java.io.IOException;
import tk.artsakenos.ultradictionary.UD_CallBack;
import tk.artsakenos.ultradictionary.UD_Client;

/**
 *
 * @author Andrea.Addis
 */
public class UD_ClientCallback {

    private final UD_CallBack udlc = new UD_CallBack("/ud_callback", 8000) {

        @Override
        public String onCallBack(String uriParameters) {
            String message = "The parameters " + uriParameters + " have been passed, take action!";
            System.out.println("DEBUG: " + message);
            return message;
        }
    };

    public void testClient() throws IOException {
        String get = UD_Client.get("pippo_key", "12345678");
        System.out.println("GET = " + get);

        // Set a Key / Value
        UD_Client.set("pippo_key", "pippo_value&param=fake\r\nNewLine?Yes!", "12345678");

        // Read that Entry
        get = UD_Client.get("pippo_key", "12345678");
        System.out.println("GET = " + get);

        // Read the list of entries
        System.out.println(UD_Client.lis("12345678"));

        // Set a callback
        // System.out.println(UltraDictionaryClient.cal("pippo_key", "http://localhost:8000/ud_callback/morepaths/?pippo=hasbeenset", "12345678"));
        // When pippo_key will be set again, that callback will be called.
        UD_Client.set("pippo_key", "", "12345678");
    }

    public static void main(String[] args) throws IOException {
        UD_ClientCallback uct = new UD_ClientCallback();
        uct.testClient();
    }

}
