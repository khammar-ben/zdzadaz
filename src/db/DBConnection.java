package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // ⚙️ DB CONFIG
    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "system";
    private static final String PASSWORD = "123456";

    // 🔒 Private constructor (ما يتخلقش object)
    private DBConnection() {}

    // 🔌 Get Connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
