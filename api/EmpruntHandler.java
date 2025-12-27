package api;

import com.sun.net.httpserver.*;
import service.EmpruntService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EmpruntHandler implements HttpHandler {

    private EmpruntService service = new EmpruntService();

    @Override
    public void handle(HttpExchange ex) throws IOException {

        System.out.println(">>> /borrow called");

        try {
            String body = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("BODY = " + body);

            String[] params = body.split("&");
            Long userId = Long.parseLong(params[0].split("=")[1]);
            Long bookId = Long.parseLong(params[1].split("=")[1]);

            System.out.println("userId=" + userId + " bookId=" + bookId);

            service.borrow(userId, bookId);

            respond(ex, "OK");

        } catch (Exception e) {
            System.out.println("🔥 BORROW ERROR:");
            e.printStackTrace();   // 👈 خاصها تبان دابا
            respond(ex, "ERROR");
        }
    }

    private void respond(HttpExchange ex, String msg) throws IOException {
        ex.sendResponseHeaders(200, msg.length());
        ex.getResponseBody().write(msg.getBytes());
        ex.close();
    }
}
