package currency_tracking_system;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class Main {
	

    public static void main(String[] args) {
        try {
            // 1. API adresi
            String apiUrl = "https://www.tcmb.gov.tr/kurlar/today.xml";

            // 2. Bağlantıyı aç
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 3. Cevabı oku
            BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
            );

            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            connection.disconnect();

            // 4. JSON verisini düz metin olarak al
            String json = response.toString();

            // 5. "TRY" kurunu bul ve yazdır (çok basit regex-like string işlemi)
            String target = "\"TRY\":";
            int index = json.indexOf(target);
            if (index != -1) {
                int start = index + target.length();
                int end = json.indexOf(",", start); // bir sonraki virgül
                if (end == -1) {
                    end = json.indexOf("}", start); // son eleman olabilir
                }
                String tryValue = json.substring(start, end).trim();
                System.out.println("USD to TRY: " + tryValue);
            } else {
                System.out.println("TRY kuru bulunamadı.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

