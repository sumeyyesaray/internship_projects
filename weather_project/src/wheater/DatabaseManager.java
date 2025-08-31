package wheater;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//Database erişim katmanı.

public class DatabaseManager {
	 private static final String URL  = Config.get("db.url");   // JDBC URL
	 private static final String USER = Config.get("db.user");  // kullanıcı
	 private static final String PASS = Config.get("db.pass");  // şifre
	  public static void saveWeather(WeatherData data) {
		  
	        // ? parametreli SQL -> PreparedStatement ile dolduracağız
	        String sql = "INSERT INTO weather_data (city, temperature, description) VALUES (?, ?, ?)";

	        // try-with-resources: blok bitince kaynaklar otomatik kapanır
	        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            // Sırayla '?' yerlerine değerleri bağla (1-indexed)
	            ps.setString(1, data.getCity());
	            ps.setDouble(2, data.getTemperature());
	            ps.setString(3, data.getDescription());

	            // DML komutu olduğu için executeUpdate -> etkilenen satır sayısını döner
	            int rows = ps.executeUpdate();
	            System.out.println("✔ DB -> " + rows + " kayıt eklendi: " + data);

	        } catch (SQLException e) {
	            // Uygulama çökmesin; hatayı görünür logla
	            System.err.println("❌ DB hatası: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	}
	

