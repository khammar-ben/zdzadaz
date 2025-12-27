package api;

import com.sun.net.httpserver.*;
import service.AuthService;
import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AuthController implements HttpHandler {

    private AuthService auth = new AuthService();

    public void handle(HttpExchange ex) throws IOException {
        String body = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        String[] p = body.split("&");
        String email = p[0].split("=")[1];
        String password = p[1].split("=")[1];

        try {
            if (ex.getRequestURI().getPath().endsWith("/register")) {
                auth.registerClient(new Client(System.currentTimeMillis(), email, password));
                respond(ex, "OK");
            } else {
                User u = auth.login(email, password);
                respond(ex, u == null ? "FAIL" : "OK");
            }
        } catch (Exception e) {
            respond(ex, "ERROR");
        }
    }

    private void respond(HttpExchange ex, String msg) throws IOException {
        ex.sendResponseHeaders(200, msg.length());
        ex.getResponseBody().write(msg.getBytes());
        ex.close();
    }
}
