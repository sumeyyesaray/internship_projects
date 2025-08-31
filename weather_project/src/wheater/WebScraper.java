package wheater;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebScraper {

    /** Yardımcı: "27°C" gibi ifadeleri double'a çevirir */
    private static double parseTemperature(String raw) {
        if (raw == null) return Double.NaN;
        String normalized = raw.replaceAll("[^0-9\\-\\.]", "");
        if (normalized.isEmpty() || normalized.equals("-")) return Double.NaN;
        return Double.parseDouble(normalized);
    }

    /** Tek bir şehirden hava verisini çeker */
    public static WeatherData scrape(String cityUrlName) throws Exception {
        String url = "https://www.timeanddate.com/weather/turkey/" + cityUrlName;

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .timeout(10_000)
                .get();

        // Sıcaklık
        Element tempEl = doc.selectFirst("div.h2");
        if (tempEl == null) throw new IllegalStateException("Sıcaklık bulunamadı!");
        double temp = parseTemperature(tempEl.text());

        // Durum açıklaması
        Element descEl = doc.selectFirst("p.small");
        if (descEl == null) throw new IllegalStateException("Durum açıklaması bulunamadı!");
        String desc = descEl.text();

        // Şehir adı (baş harfi büyük)
        String cityName = cityUrlName.substring(0,1).toUpperCase() + cityUrlName.substring(1);

        return new WeatherData(cityName, temp, desc);
    }

    /** Uygulama giriş noktası */
    public static void main(String[] args) {
        // config.properties içindeki "cities" anahtarını al
        String[] cities = Config.get("cities").split(",");

        for (String u : cities) {
            String city = u.trim().toLowerCase(); // URL isimleri küçük harf
            if(city.isEmpty()) continue;

            try {
                WeatherData data = scrape(city);       // HTML -> model
                DatabaseManager.saveWeather(data);     // model -> DB
                Thread.sleep(1000);                    // nazik ol
                System.out.println("✔ " + data);
            } catch(Exception ex){
                System.err.println("❌ Hata (" + city + "): " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        System.out.println("🏁 İş bitti.");
    }
}
