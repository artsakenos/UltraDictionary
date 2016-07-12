/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.artsakenos.ultradictionary;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrea.Addis
 */
public abstract class UD_CallBack {

    /**
     *
     * @param path The path where to dispatch the call, e.g., /ud_callback
     * @param port The server port
     */
    public UD_CallBack(String path, int port) {
        ///-{ Start a server for the callbacks
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException ex) {
            Logger.getLogger(UD_CallBack.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        server.createContext(path, new UD_CallBack.MyHandler());
        server.setExecutor(null); // Creates a default executor
        server.start();
    }

    private class MyHandler implements HttpHandler {

        @Override
        @SuppressWarnings("ConvertToTryWithResources")
        public void handle(HttpExchange exchanger) throws IOException {
            // System.out.println("Request URI:" + t.getRequestURI());
            // String response = "This is the response";
            String response = onCallBack(exchanger.getRequestURI().getQuery());
            exchanger.sendResponseHeaders(200, response.length());
            OutputStream os = exchanger.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public abstract String onCallBack(String uri);

}
