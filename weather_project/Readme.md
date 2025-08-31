# 🌦️ Weather Data Scraper & Visualizer

This project uses **Java** to collect weather data from an **API** and through **Web Scraping**, saves it to a **MySQL** database, and then visualizes it with **Python.**

## 🚀 Features

| Feature | Description |
|---------|-------------|
| 📡 Open-Meteo API & Jsoup | Collects weather data from the API and through web scraping. |
| 🗄️ MySQL | Saves data to the database and prevents duplicate entries. |
| 🕒 DailyScheduler | Automatically runs the application every day. |
| 📊 Python (Matplotlib + Pandas) | Analyzes the data and visualizes it with charts. |

## 📂 Project Structure



weather-scraper/
├── src/wheater/
│ ├── WebScraperAndSaver.java # Fetches data from the API and saves it to MySQL
│ ├── WebScraper.java # Scraping with Jsoup
│ ├── Config.java # Reads config.properties
│ ├── DailyScheduler.java # Daily runner
│ └── WeatherData.java # Model class
├── config.properties # DB and city settings
└── report.py # Python visualization script


## ⚙️ Kurulum

### 1️⃣ Veritabanını oluştur
```sql
CREATE DATABASE weatherdb;
USE weatherdb;

CREATE TABLE weather_data (
 id INT AUTO_INCREMENT PRIMARY KEY,
 city VARCHAR(50),
 temperature DOUBLE,
 description VARCHAR(50),
 time DATETIME
);
```

### 2️⃣ Config.properties ayarla

```properties
db.url=jdbc:mysql://localhost:3306/weatherdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
db.user=root
db.pass=YOUR_PASSWORD
cities=ankara, istanbul
```


### 3️⃣ Java tarafını çalıştır

javac -cp ".;lib/" wheater/*.java
java -cp ".;lib/*" wheater.WebScraperAndSaver


### 4️⃣ Python scriptini çalıştır

python report.py


## 📊 Sample Output and Graph

<p align="center">
<img src="Çıktı.png" alt="Örnek Çıktı" width="600"/>
</p>

<p align="center">
<img src="grafik.png" alt="Örnek Grafik" width="600"/>
</p>

## 🛠 Technologies Used

| Technology | Description |
|------------|-------------|
| ☕ Java 17 | Fetches data from the API and saves it to the database. |
| 🌐 Jsoup | HTML parsing for web scraping. |
| 🗄️ MySQL | Database for storing weather data. |
| 🐍 Python | Analyzes data and visualizes it with charts. |
| ⏳ ScheduledExecutorService | Automatically collects data daily. |

## ⏳ Automatic Execution

The DailyScheduler class ensures that the program collects hourly data every day and saves it to MySQL:

```java
scheduler.scheduleAtFixedRate(task, 0, 24, TimeUnit.HOURS);
