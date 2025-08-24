package currency_tracking_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/dovizdb"; // Veritabanı adı dovizdb
        String user = "root"; // Kullanıcı adı
        String password = "sifre"; // Şifre

        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            String sql = "INSERT INTO doviz_kurlari (tarih, kur) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "2025-08-22");
            statement.setDouble(2, 35.5);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Veri başarıyla eklendi.");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
