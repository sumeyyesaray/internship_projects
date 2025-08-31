package wheater;

public class WeatherData {
    private final String city;         
    private final double temperature;  
    private final String description;  // durum açıklaması

    // Kurucu: tüm alanları zorunlu kılar -> tutarlı nesne
    public WeatherData(String city, double temperature, String description) {
        this.city = city;
        this.temperature = temperature;
        this.description = description;
    }

  //kurucu tüm alanları zorunlu kılar (tutarlı nesne)
    public String getCity() { return city; }
    public double getTemperature() { return temperature; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return city + " | " + temperature + "° | " + description;
    }
}
