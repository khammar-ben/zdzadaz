package repository;

import db.DBConnection;
import model.*;
import java.sql.*;

public class UserRepository {

    public void save(User user, String role) throws Exception {
        String sql = "INSERT INTO users VALUES (?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, user.getId());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, role);
            ps.executeUpdate();
        }
    }

    public User findByEmailAndPassword(String email, String password) throws Exception {
        String sql = "SELECT * FROM users WHERE email=? AND password=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                if ("ADMIN".equals(role))
                    return new Admin(rs.getLong("id"), email, password);
                return new Client(rs.getLong("id"), email, password);
            }
        }
        return null;
    }
}
