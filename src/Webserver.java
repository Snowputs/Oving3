import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Webserver {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response =
                    "<HTML><BODY>" +
                    "<h1>Hilsen. Du har koblet deg opp til min enkle web-tjener</h1>" +
                            "Header fra klienten er:" +
                            "<ul>";
            for(Object obj : t.getRequestHeaders().values()){
                response += "<li>" + obj + "</li>";
                System.out.println(obj + "");
            }
            response += "</ul></BODY></HTML>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
//            System.out.println(t.getRequestHeaders().values());
            os.write(response.getBytes());
            os.close();
        }
    }

}
