package javafx.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=IT_Course_Management;integratedSecurity=true;encrypt=false";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Kết nối
            conn = DriverManager.getConnection(URL);
            System.out.println("✅ Kết nối SQL Server thành công!");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Lỗi: Không tìm thấy driver JDBC!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối SQL Server!");
            e.printStackTrace();
        }
        return conn;
    }
}
