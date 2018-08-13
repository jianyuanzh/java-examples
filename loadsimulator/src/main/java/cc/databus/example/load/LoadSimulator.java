package cc.databus.example.load;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadSimulator {

    private static LoadSimulator INSTANCE = new LoadSimulator();


    private ExecutorService executor = null;

    private LoadSimulator() {
    }

    public synchronized void start(double load) {
        if (executor != null) {
            System.err.println("Already started");
            return;
        }

        int cores = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(cores);

        long busyTimeInMs = Math.round(load * 100f);
        long idleTimeInMs = 100 - busyTimeInMs;

        System.out.println("Cores: " + cores + ", busy " + busyTimeInMs + "ms and sleep " + idleTimeInMs + "ms!");

        for (int i = 0; i < cores; i++) {
            executor.execute(new BusyThread(idleTimeInMs, busyTimeInMs));
        }
    }

    public synchronized void stop() {
        if (executor == null) {
            System.err.println("Not running");
            return;
        }

        try {
            executor.shutdownNow();
        }
        finally {
            executor = null;
        }
    }


    public static void main(String[] args) {
        double load = 0.3;
        if (args.length > 0) {
            load = Double.parseDouble(args[0]);
        }


        INSTANCE.start(load);
    }
    private static class BusyThread implements Runnable {

        private final long idleTimeInMs;
        private final long busyTimeInMs;

        private BusyThread(long idleTimeInMs, long busyTimeInMs) {
            this.idleTimeInMs = idleTimeInMs;
            this.busyTimeInMs = busyTimeInMs;
        }

        public void run() {

            while (true) {
                long busyTo = System.currentTimeMillis() + busyTimeInMs;
                while (System.currentTimeMillis() < busyTo) {
                    // dead loop
                }
                try {
                    Thread.sleep(idleTimeInMs);
                }
                catch (InterruptedException e) {
                    System.out.println("Thread break!");
                    break;
                }
            }
        }
    }
}
