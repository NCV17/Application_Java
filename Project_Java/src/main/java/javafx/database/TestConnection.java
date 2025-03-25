package javafx.database;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("✅ Kết nối SQL Server thành công!");
        } else {
            System.out.println("❌ Lỗi kết nối SQL Server!");
        }
    }
}
