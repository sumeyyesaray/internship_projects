import mysql.connector
import pandas as pd
import matplotlib.pyplot as plt

# DB bağlantısı
conn = mysql.connector.connect(
    host="localhost",
    user="root",
    password="Sumeyye.03",
    database="weatherdb"
)

# SQL sorgusu
query = "SELECT time, temperature, description FROM weather_data WHERE city='Ankara'"
df = pd.read_sql(query, conn)

# datetime tipine çevir
df['time'] = pd.to_datetime(df['time'])
conn.close()

# description için renk sözlüğü
color_map = {
    "Güneşli": "gold",
    "Kısmen bulutlu": "lightgray",
    "Bulutlu": "gray",
    "Yağmurlu": "blue",
    "Hafif yağmur": "lightblue",
    "Hafif kar": "white",
    "Bilinmiyor": "black"
}

# her noktanın rengi
colors = df['description'].map(color_map)

plt.figure(figsize=(12,6))

# Renkli noktaları çizdir ve kenar renklerini de aynı yap
plt.scatter(df['time'], df['temperature'], c=colors, s=100, edgecolors=colors)

# Sıcaklık çizgisini ekle
plt.plot(df['time'], df['temperature'], color='blue', label='Sıcaklık')

plt.title('Ankara Saatlik Sıcaklık ve Hava Durumu')
plt.xlabel('Zaman')
plt.ylabel('Sıcaklık (°C)')
plt.xticks(rotation=45)
plt.grid(True)

# Legend'ı oluştur
legend_elements = [
    plt.Line2D([0], [0], marker='o', color='w', label=k, markerfacecolor=v, markersize=10)
    for k, v in color_map.items()
]

plt.legend(handles=legend_elements, title='Hava Durumu')
plt.tight_layout()
plt.show()