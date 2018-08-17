package cc.databus.btrace.samples;

import javax.management.MBeanServerFactory;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CronUpdateFile {

    private static final String DEFAULT_CONTENT = "THIS IS THE DEFAULT CONTENT\n" +
            "FOR CronUpdateFile\n" +
            "And this will be updated or overwritten.";

    private static final String APPEND_LINE = "Appended Line. ";

    private static final CronUpdateFile INSTANCE = new CronUpdateFile();
    private CronUpdateFile() {}

    private ScheduledExecutorService scheduledExecutorService = null;

    private synchronized void start() throws IOException {
        if (scheduledExecutorService != null) {
            throw new IllegalStateException("Already started!");
        }

        // initialize
        File file = new File(".", this.getClass().getSimpleName());
        if (file.exists()) {
            file.delete();
        }

        String fullPath = file.getCanonicalPath();

        System.out.println("Cron update file: " + fullPath);
        scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.scheduleWithFixedDelay(new CrontTask(fullPath, true),  10,10, TimeUnit.MINUTES);
        scheduledExecutorService.scheduleWithFixedDelay(new CrontTask(fullPath, false), 3, 3, TimeUnit.MINUTES);
    }

    private synchronized void stop() {
        if (scheduledExecutorService == null) {
            throw new IllegalStateException("Not started!");
        }

        try {
            scheduledExecutorService.shutdownNow();
        }
        catch (Exception e) {
        }
        finally {
            scheduledExecutorService = null;
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(ManagementFactory.getRuntimeMXBean().getName());
        INSTANCE.start();
    }


    private static String readFileToString(final File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n" );
            }
            return sb.toString();
        }
    }

    private static void writeStringToFile(final File file, String content, boolean append) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
            writer.write(content);
        }
    }

    private static class CrontTask implements Runnable {

        private final String fileName;
        private final boolean refresh;

        private CrontTask(String fileName, boolean refresh) {
            this.fileName = fileName;
            this.refresh = refresh;
        }

        @Override
        public void run() {
            File file = new File(fileName);
            try {
                if (refresh) {
                    System.out.println(String.format("%s Start refresh file.", new Date()));
                    writeStringToFile(file, DEFAULT_CONTENT, false);
                    System.out.println(String.format("%s Refresh done.", new Date()));
                }
                else {
                    System.out.println(String.format("%s Start append line to file.", new Date()));
                    writeStringToFile(file, String.format("\n%s %s", new Date(), APPEND_LINE), true);
                    System.out.println(String.format("%s Append line done.", new Date()));
                }
            }
            catch (IOException ioe) {
                System.out.println("File update failed refresh=" + refresh);
                ioe.printStackTrace();
            }
        }
    }

}
