package cc.databus.hashmap;

import java.util.HashMap;

public class BencMarkhHashMap {

    private static void test(Keys keys, int keyCount) {
        HashMap<Keys.Key, Integer> perf = new HashMap<>();
        for (int i = 0; i < keyCount; i++) {
            perf.put(keys.get(i), i);
        }
        long start = System.nanoTime();
        for (int i = 0; i < keyCount; i++) {
            perf.get(keys.get(i));
        }
        long elapsed = System.nanoTime() - start;
        System.out.println(String.format("%d entries, total read time: %dns, average: %d ns per entry",
                keyCount, elapsed, elapsed/keyCount));
    }

    public static void main(String[] args) {
        Keys keys = new Keys(10000000);
        for (int i = 10; i <= 10000000; i*=10) {
            test(keys, i);
        }
    }
}
