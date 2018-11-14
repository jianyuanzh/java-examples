package cc.databus.cache.test;

import cc.databus.cache.LRUCache;
import org.junit.Test;

import java.util.Iterator;

public class TestLRUCache {
    @Test
    public void test() {
        LRUCache<String, Integer> lru = new LRUCache<>(3);

        lru.put("test1", 1);
        lru.put("test2", 2);
        lru.put("test3", 3);

        System.out.println("old --------------> new");
        printCache(lru);
        System.out.println("=======================");
        lru.get("test2");
        printCache(lru);
        System.out.println("=======================");
        lru.put("test4", 4);
        printCache(lru);
    }

    private static void printCache(LRUCache cache) {
        for (Object o : cache.entrySet()) {
            System.out.print(o + " ");
        }
        System.out.print("\n");
    }
}
