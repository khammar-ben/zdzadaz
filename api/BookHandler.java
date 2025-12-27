package api;

import com.sun.net.httpserver.*;
import repository.BookRepository;
import model.Book;
import java.io.*;
import java.util.List;

public class BookHandler implements HttpHandler {

    private BookRepository repo = new BookRepository();

    @Override
    public void handle(HttpExchange ex) throws IOException {
        try {
            List<Book> books = repo.findAll();
            StringBuilder json = new StringBuilder("[");

            for (Book b : books) {
                json.append("{")
                        .append("\"id\":").append(b.getId()).append(",")
                        .append("\"title\":\"").append(b.getTitle()).append("\",")
                        .append("\"author\":\"").append(b.getAuthor()).append("\",")
                        .append("\"quantity\":").append(b.getQuantity())
                        .append("},");
            }

            if (json.charAt(json.length()-1) == ',')
                json.deleteCharAt(json.length()-1);

            json.append("]");

            ex.sendResponseHeaders(200, json.length());
            ex.getResponseBody().write(json.toString().getBytes());
            ex.close();

        } catch (Exception e) {
            ex.sendResponseHeaders(500, 0);
            ex.close();
        }
    }
}
