package repository;

import db.DBConnection;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    // ================= CREATE =================
    public void save(Book book) {

        String sql = """
            INSERT INTO BOOK (TITLE, AUTHOR, DESCRIPTION, QUANTITY, CATEGORY_ID)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getDescription());
            ps.setInt(4, book.getQuantity());
            ps.setInt(5, book.getCategory());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    try {
                        book.setId(keys.getInt(1));
                    } catch (Exception ex) {
                        try {
                            String s = keys.getString(1);
                            if (s != null) {
                                String digits = s.replaceAll("\\D", "");
                                if (!digits.isEmpty()) {
                                    book.setId(Integer.parseInt(digits));
                                } else {
                                    System.err.println("⚠️ Could not parse generated key for BOOK: '" + s + "'");
                                    book.setId(0);
                                }
                            }
                        } catch (Exception ex2) {
                            System.err.println("⚠️ Unable to obtain generated key for BOOK (fallback failed)");
                            book.setId(0);
                        }
                    }
                }
            }

            System.out.println("✅ Book added (id=" + book.getId() + ")");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= READ ALL =================
    public List<Book> findAll() {

        List<Book> books = new ArrayList<>();
        String sql = "SELECT ID, TITLE, AUTHOR, DESCRIPTION, QUANTITY, CATEGORY_ID FROM BOOK";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                books.add(new Book(
                                rs.getInt("ID"),
                        rs.getString("TITLE"),
                        rs.getString("AUTHOR"),
                        rs.getString("DESCRIPTION"),
                        rs.getInt("QUANTITY"),
                        rs.getInt("CATEGORY_ID")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    // ================= READ BY ID =================
    public Book findById(int id) {

        String sql = "SELECT * FROM BOOK WHERE ID = ?";
        Book book = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                book = new Book(
                        rs.getInt("ID"),
                        rs.getString("TITLE"),
                        rs.getString("AUTHOR"),
                        rs.getString("DESCRIPTION"),
                        rs.getInt("QUANTITY"),
                        rs.getInt("CATEGORY_ID")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return book;
    }

    // ================= UPDATE =================
    public void update(Book book) {

        String sql = """
            UPDATE BOOK
            SET TITLE = ?, AUTHOR = ?, DESCRIPTION = ?, QUANTITY = ?, CATEGORY_ID = ?
            WHERE ID = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getDescription());
            ps.setInt(4, book.getQuantity());
            ps.setInt(5, book.getCategory());
            ps.setInt(6, book.getId());

            ps.executeUpdate();
            System.out.println("✏️ Book updated");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= DELETE =================
    public void delete(int id) {

        String sql = "DELETE FROM BOOK WHERE ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("🗑️ Book deleted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
