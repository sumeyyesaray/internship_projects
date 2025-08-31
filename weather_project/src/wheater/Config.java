package wheater;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	 private static final Properties P = new Properties();

	    // static blok: sınıf ilk kez yüklendiğinde bir kez çalışır
	    static {
	        try (InputStream in = Config.class.getClassLoader()
	                                          .getResourceAsStream("config.properties")) {
	            if (in == null) {
	                // Hata mesajını erken ver ki debug kolay olsun
	                throw new IllegalStateException(
	                    "config.properties bulunamadı (classpath'e ekleyin: örn. src/resources)");
	            }
	            P.load(in);                 // key=value çiftlerini yükle
	        } catch (IOException e) {
	            // Sarmala ve yükselt: konfigürasyon yoksa uygulama zaten çalışmamalı
	            throw new RuntimeException("Config yüklenemedi", e);
	        }
	    }

	    public static String get(String key) {
	        return P.getProperty(key);      // anahtar ile değer döner
	    }
	}
