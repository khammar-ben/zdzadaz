package repository;

import db.DBConnection;
import enums.EmpruntStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;

public class EmpruntRepository {

    public void borrow(Long clientId, Long bookId) throws Exception {

        String sql = """
            INSERT INTO emprunts
            (client_id, book_id, borrow_date, return_date, status)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, clientId);
            ps.setLong(2, bookId);
            ps.setDate(3, new Date(System.currentTimeMillis()));
            ps.setDate(4, null);
            ps.setString(5, EmpruntStatus.EN_COURS.name());

            ps.executeUpdate();
        }
    }
}
