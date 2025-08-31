package wheater;
import java.util.concurrent.*;

public class DailyScheduler {

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                WebScraperAndSaver.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        // İlk çalıştırma: hemen, sonra her 24 saatte bir
        scheduler.scheduleAtFixedRate(task, 0, 24, TimeUnit.HOURS);
    }
}
