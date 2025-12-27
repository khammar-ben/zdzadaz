package repository;

import db.DBConnection;
import model.Admin;
import model.Client;
import model.Responsable;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    // ================= CREATE =================
    public void save(User user) {
        String sql = "INSERT INTO USERS (EMAIL, PASSWORD, ROLE) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getClass().getSimpleName());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    try {
                        user.setId(keys.getInt(1));
                    } catch (Exception ex) {
                        try {
                            String s = keys.getString(1);
                            if (s != null) {
                                String digits = s.replaceAll("\\D", "");
                                if (!digits.isEmpty()) {
                                    user.setId(Integer.parseInt(digits));
                                } else {
                                    System.err.println("⚠️ Could not parse generated key for USER: '" + s + "'");
                                    user.setId(0);
                                }
                            }
                        } catch (Exception ex2) {
                            System.err.println("⚠️ Unable to obtain generated key for USER (fallback failed)");
                            user.setId(0);
                        }
                    }
                }
            }

            System.out.println("✅ User saved (id=" + user.getId() + ")");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= READ BY EMAIL =================
    public User findByEmail(String email) {
        String sql = "SELECT ID, EMAIL, PASSWORD, ROLE FROM USERS WHERE EMAIL = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("ID");
                String em = rs.getString("EMAIL");
                String pwd = rs.getString("PASSWORD");
                String role = rs.getString("ROLE");

                User user = instantiateByRole(role, em, pwd);
                user.setId(id);
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ================= READ BY ID =================
    public User findById(int id) {
        String sql = "SELECT ID, EMAIL, PASSWORD, ROLE FROM USERS WHERE ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int uid = rs.getInt("ID");
                String em = rs.getString("EMAIL");
                String pwd = rs.getString("PASSWORD");
                String role = rs.getString("ROLE");

                User user = instantiateByRole(role, em, pwd);
                user.setId(uid);
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ================= READ ALL =================
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT ID, EMAIL, PASSWORD, ROLE FROM USERS";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int uid = rs.getInt("ID");
                String em = rs.getString("EMAIL");
                String pwd = rs.getString("PASSWORD");
                String role = rs.getString("ROLE");

                User user = instantiateByRole(role, em, pwd);
                user.setId(uid);
                list.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= UPDATE =================
    public boolean update(User user) {
        String sql = "UPDATE USERS SET EMAIL = ?, PASSWORD = ?, ROLE = ? WHERE ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getClass().getSimpleName());
            ps.setInt(4, user.getId());

            int updated = ps.executeUpdate();
            return updated > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ================= DELETE =================
    public boolean deleteById(int id) {
        String sql = "DELETE FROM USERS WHERE ID = ?";

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

    private User instantiateByRole(String role, String em, String pwd) {
        if ("Admin".equalsIgnoreCase(role)) return new Admin(em, pwd);
        if ("Responsable".equalsIgnoreCase(role)) return new Responsable(em, pwd);
        // default to client
        return new Client(em, pwd);
    }
}
