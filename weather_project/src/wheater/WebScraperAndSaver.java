package wheater;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WebScraperAndSaver {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/weatherdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "root";  
    private static final String DB_PASSWORD = "Sumeyye.03";  
    
    public static String getWeatherDescription(int code) {
        switch(code) {
            case 0: return "Güneşli";
            case 1: return "Kısmen bulutlu";
            case 2: return "Bulutlu";
            case 3: return "Yağmurlu";
            case 61: return "Hafif yağmur";
            case 71: return "Hafif kar";
            default: return "Bilinmiyor";
        }
    }

    public static void main(String[] args) {
        try {
        	// Bugünkü tarihi al
        	LocalDate today = LocalDate.now();
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        	String todayStr = today.format(formatter);

        	// hava durumu URL:
        	String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=39.9334&longitude=32.8597&hourly=temperature_2m,weathercode&timezone=Europe/Istanbul" + 
        	                "&start_date=" + todayStr + "&end_date=" + todayStr;
            
        	URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject(response.toString());
            JSONObject hourly = json.getJSONObject("hourly");
            JSONArray temps = hourly.getJSONArray("temperature_2m");
            JSONArray weathercodes = hourly.getJSONArray("weathercode");
            JSONArray dates = hourly.getJSONArray("time");

            try (Connection dbConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String insertSql = "INSERT INTO weather_data (city, temperature, description, time) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = dbConn.prepareStatement(insertSql);

                String checkSql = "SELECT COUNT(*) FROM weather_data WHERE city = ? AND time = ?";
                PreparedStatement checkStmt = dbConn.prepareStatement(checkSql);

                for (int i = 0; i < temps.length(); i++) {
                    // API'den gelen saatlik sıcaklık verisi
                    double temp = temps.getDouble(i); 
                    int code = weathercodes.getInt(i);
                    String description = getWeatherDescription(code);
                    String date = dates.getString(i); // Saatli zaman bilgisi

                    // Tekrarlanan veriyi engellemek için kontrol
                    checkStmt.setString(1, "Ankara");
                    checkStmt.setString(2, date);
                    ResultSet rs = checkStmt.executeQuery();
                    if (rs.next() && rs.getInt(1) > 0) continue;

                    insertStmt.setString(1, "Ankara");
                    insertStmt.setDouble(2, temp);
                    insertStmt.setString(3, description);
                    insertStmt.setString(4, date);
                    insertStmt.executeUpdate();

                    System.out.println("✔ Kayıt eklendi: Ankara | " + temp + "°C | " + description + " | " + date);
                }
            }
            
          //Python script'ini otomatik çalıştırmak için:
            String pythonExecutablePath = "C:\\Users\\Sümeyye\\AppData\\Local\\Programs\\Python\\Python311\\python.exe";
        	String pythonScriptPath = "C:\\Users\\Sümeyye\\Desktop\\all_workspaces\\veri görselleştirme\\report.py";
            
            System.out.println("✔ Python script çalıştırılıyor...");

            ProcessBuilder pb = new ProcessBuilder(pythonExecutablePath, pythonScriptPath);
            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("✔ Python script başarıyla çalıştı ve grafik oluşturuldu.");
            } else {
                System.out.println("❌ Python script çalışırken bir hata oluştu. Çıkış kodu: " + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}