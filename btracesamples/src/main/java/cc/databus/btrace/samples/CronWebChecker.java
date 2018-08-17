package cc.databus.btrace.samples;

import java.lang.management.ManagementFactory;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * webcheck <url> [intervalInSeconds]
 *  - default interval is 30 seconds
 */
public class CronWebChecker {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Current process: " +  ManagementFactory.getRuntimeMXBean().getName());

        if (args.length < 1) {
            System.err.println("webcheck <url> [intervalInSeconds]");
            return;
        }

        String url = args[0];
        long interval = 30; // seconds
        if (args.length > 1) {
            interval = Long.parseLong(args[1]);
        }

        try {
            new URL(url);
        }
        catch (MalformedURLException e) {
            System.err.println("Given URL is not valid - " + url);
            return;
        }

        System.out.println("Wait 30 seconds....");
        Thread.sleep(30000);
        System.out.println("Ready go!");

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        scheduledExecutorService.scheduleWithFixedDelay(new CheckTask(url), 0, interval, TimeUnit.SECONDS);
    }

    private static class CheckTask implements Runnable {
        final String url;

        private CheckTask(String url) {
            this.url = url;
        }

        public void run() {
            try {
                System.out.printf("%s %s start check\n", getThreadInfo(), new Date());
                long start = System.currentTimeMillis();
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.connect();
                int code = connection.getResponseCode();
                String message = connection.getResponseMessage();
                Thread.sleep(3000);
                System.out.printf("%s %s finish check, elapsed %dms %d:%s\n",getThreadInfo(), new Date(), System.currentTimeMillis() - start, code, message);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

        private static String getThreadInfo() {
            return Thread.currentThread().getName();
        }
    }
}
