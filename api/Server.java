package api;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/auth", new AuthController());
        server.createContext("/books", new BookHandler()    );
        server.createContext("/borrow", new EmpruntHandler());
        server.start();
        System.out.println("API running on http://localhost:8081");
    }
}
