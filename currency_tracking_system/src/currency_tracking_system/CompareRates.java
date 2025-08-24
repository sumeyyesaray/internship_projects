package currency_tracking_system;

import java.sql.*;


public class CompareRates {
	public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/dovizdb";
        String user = "root";
        String password = "sifre";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            // Son iki günün verilerini çek (örnek sorgu)
            String sql = "SELECT tarih, kur FROM doviz_kurlari ORDER BY tarih DESC LIMIT 2";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            double todayRate = 0;
            double yesterdayRate = 0;

            if (rs.next()) {
                todayRate = rs.getDouble("kur");
            }
            if (rs.next()) {
                yesterdayRate = rs.getDouble("kur");
            }

            double fark = todayRate - yesterdayRate;
            System.out.println("Bugünkü Kur: " + todayRate);
            System.out.println("Dünkü Kur: " + yesterdayRate);
            System.out.println("Fark: " + fark);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
