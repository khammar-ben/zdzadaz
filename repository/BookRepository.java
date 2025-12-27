package repository;

import db.DBConnection;
import model.Book;
import java.sql.*;
import java.util.*;

public class BookRepository {

    public void save(Book b) throws Exception {
        String sql = "INSERT INTO books VALUES (?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, b.getId());
            ps.setString(2, b.getTitle());
            ps.setString(3, b.getAuthor());
            ps.setInt(4, b.getQuantity());
            ps.executeUpdate();
        }
    }

    public List<Book> findAll() throws Exception {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("quantity")
                ));
            }
        }
        return list;
    }
}
