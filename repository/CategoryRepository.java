package repository;

import db.DBConnection;
import model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CategoryRepository {

    public void save(Category c) throws Exception {
        String sql = "INSERT INTO categories VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, c.getId());
            ps.setString(2, c.getName());
            ps.executeUpdate();
        }
    }
}
