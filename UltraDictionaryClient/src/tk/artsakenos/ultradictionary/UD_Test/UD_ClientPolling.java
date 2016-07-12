/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.artsakenos.ultradictionary.UD_Test;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import tk.artsakenos.ultradictionary.UD_Client;
import tk.artsakenos.ultradictionary.UD_Polling;

/**
 *
 * @author drupalpro
 */
public class UD_ClientPolling {

    private static final String token = "12345678";

    private final UD_Polling udp = new UD_Polling() {

        @Override
        public void onChange(String key, String value) {
            System.out.println("[UD] Variable " + key + " is changed to " + value);
        }

    };

    public void testClient() {
        UD_Client.set("pippo_key", "Initial Value", token);
        udp.start();
        udp.addPollKey("pippo_key");

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException ex) {
            Logger.getLogger(UD_ClientPolling.class.getName()).log(Level.SEVERE, null, ex);
        }
        UD_Client.set("pippo_key", "new Value " + Calendar.getInstance().getTimeInMillis(), token);

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException ex) {
            Logger.getLogger(UD_ClientPolling.class.getName()).log(Level.SEVERE, null, ex);
        }
        udp.stop();
    }

    public static void main(String[] args) {
        UD_ClientPolling udcp = new UD_ClientPolling();
        udcp.testClient();
    }

}
