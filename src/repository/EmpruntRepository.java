package repository;

import db.DBConnection;
import model.Book;
import model.Emprunt;
import model.User;
import repository.BookRepository;
import repository.UserRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntRepository {

    private final BookRepository bookRepository = new BookRepository();

    // ================= CREATE =================
    public void save(Emprunt emprunt) {

        // Use sequence to generate ID explicitly (reliable with Oracle):
        String sql = """
            INSERT INTO EMPRUNT (USER_ID, BOOK_ID, BORROW_DATE, RETURN_DATE, STATUS)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (emprunt.getUser() != null && emprunt.getUser().getId() != 0) {
                ps.setInt(1, emprunt.getUser().getId());
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }
            if (emprunt.getBook() != null && emprunt.getBook().getId() != 0) {
                ps.setInt(2, emprunt.getBook().getId());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            if (emprunt.getBorrowDate() != null) {
                ps.setDate(3, Date.valueOf(emprunt.getBorrowDate()));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }
            if (emprunt.getReturnDate() != null) {
                ps.setDate(4, Date.valueOf(emprunt.getReturnDate()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }
            ps.setString(5, emprunt.getStatus() != null ? emprunt.getStatus().name() : null);

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    emprunt.setId(keys.getInt(1));
                }
            }

            System.out.println("✅ Emprunt saved (id=" + emprunt.getId() + ")");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= READ ALL =================
    public List<Emprunt> findAll() {

        List<Emprunt> list = new ArrayList<>();
        String sql = "SELECT ID, USER_ID, BOOK_ID, BORROW_DATE, RETURN_DATE, STATUS FROM EMPRUNT";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("ID");
                int userId = rs.getObject("USER_ID") != null ? rs.getInt("USER_ID") : 0;
                int bookId = rs.getObject("BOOK_ID") != null ? rs.getInt("BOOK_ID") : 0;
                Date bor = rs.getDate("BORROW_DATE");
                LocalDate borrowDate = bor != null ? bor.toLocalDate() : null;
                Date ret = rs.getDate("RETURN_DATE");
                LocalDate returnDate = ret != null ? ret.toLocalDate() : null;
                String status = rs.getString("STATUS");

                Book book = bookRepository.findById(bookId);
                User user = userId != 0 ? new repository.UserRepository().findById(userId) : null;

                Emprunt e = new Emprunt(id, user, book);
                e.setBorrowDate(borrowDate);
                e.setReturnDate(returnDate);
                e.setStatus(status != null ? enums.EmpruntStatus.valueOf(status) : null);

                list.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= READ BY ID =================
    public Emprunt findById(int id) {

        String sql = "SELECT * FROM EMPRUNT WHERE ID = ?";
        Emprunt emprunt = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getObject("USER_ID") != null ? rs.getInt("USER_ID") : 0;
                int bookId = rs.getObject("BOOK_ID") != null ? rs.getInt("BOOK_ID") : 0;
                Date bor = rs.getDate("BORROW_DATE");
                LocalDate borrowDate = bor != null ? bor.toLocalDate() : null;
                Date ret = rs.getDate("RETURN_DATE");
                LocalDate returnDate = ret != null ? ret.toLocalDate() : null;
                String status = rs.getString("STATUS");

                Book book = bookRepository.findById(bookId);
                User user = userId != 0 ? new UserRepository().findById(userId) : null;

                emprunt = new Emprunt(id, user, book);
                emprunt.setBorrowDate(borrowDate);
                emprunt.setReturnDate(returnDate);
                emprunt.setStatus(status != null ? enums.EmpruntStatus.valueOf(status) : null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emprunt;
    }

    // ================= UPDATE =================
    public boolean update(Emprunt emprunt) {

        String sql = """
            UPDATE EMPRUNT
            SET USER_ID = ?, BOOK_ID = ?, BORROW_DATE = ?, RETURN_DATE = ?, STATUS = ?
            WHERE ID = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (emprunt.getUser() != null && emprunt.getUser().getId() != 0) {
                ps.setInt(1, emprunt.getUser().getId());
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }
            if (emprunt.getBook() != null && emprunt.getBook().getId() != 0) {
                ps.setInt(2, emprunt.getBook().getId());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            if (emprunt.getBorrowDate() != null) {
                ps.setDate(3, Date.valueOf(emprunt.getBorrowDate()));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }
            if (emprunt.getReturnDate() != null) {
                ps.setDate(4, Date.valueOf(emprunt.getReturnDate()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }
            ps.setString(5, emprunt.getStatus() != null ? emprunt.getStatus().name() : null);
            if (emprunt.getId() != 0) {
                ps.setInt(6, emprunt.getId());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }

            int updated = ps.executeUpdate();
            return updated > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ================= DELETE =================
    public boolean deleteById(int id) {

        String sql = "DELETE FROM EMPRUNT WHERE ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int deleted = ps.executeUpdate();
            return deleted > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
