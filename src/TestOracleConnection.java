import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class TestOracleConnection {

    public static void main(String[] args) {

        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "system";
        String password = "123456";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            System.out.println("✅ Connection SUCCESS");

            /* ======================
               INSERT NEW USER
               ====================== */
            String insertSql =
                    "INSERT INTO system.USERS (EMAIL, PASSWORD, ROLE) VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(insertSql);
            ps.setString(1, "ne158wuser@libb.com");
            ps.setString(2, "14656");
            ps.setString(3, "ADMIN");

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ User added successfully");
            }

            ps.close();

            /* ======================
               SELECT ALL USERS
               ====================== */
            Statement stmt = conn.createStatement();
            String selectSql =
                    "SELECT ID, EMAIL, PASSWORD, ROLE FROM system.USERS";

            ResultSet rs = stmt.executeQuery(selectSql);

            System.out.println("📋 Users list:");
            System.out.println("--------------------");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("ID") + " | " +
                                rs.getString("EMAIL") + " | " +
                                rs.getString("PASSWORD") + " | " +
                                rs.getString("ROLE")
                );
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
