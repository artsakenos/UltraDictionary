/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.artsakenos.ultradictionary;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides an asynchronous check.
 *
 * @author Andrea
 */
public abstract class UD_Polling {

    private final HashMap<String, String> pollKeys = new HashMap<>();
    private boolean running = false;
    private static final String token = "12345678";
    private static final long cycle_delay = 5_000;

    public UD_Polling() {
    }

    public void start() {
        Thread t = new Thread() {
            @Override
            @SuppressWarnings("SleepWhileInLoop")
            public void run() {
                running = true;
                System.out.println("[UD] Asynchronous polling started");
                while (running) {
                    try {
                        sleep(cycle_delay);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(UD_Polling.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    pollKeys.keySet().stream().forEach((key) -> {
                        String get = UD_Client.get(key, token);
                        if (!get.equals(pollKeys.get(key))) {
                            onChange(key, get);
                            pollKeys.put(key, get);
                        }
                    });
                }
            }
        };
        t.start();
    }

    public void stop() {
        running = false;
        System.out.println("[UD] Asynchronous polling stopped");
    }

    public abstract void onChange(String key, String value);

    public void addPollKey(String key) {
        pollKeys.put(key, "");
    }

    public void removePollKey(String key) {
        pollKeys.remove(key);
    }

}
