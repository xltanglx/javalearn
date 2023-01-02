package threads.Locks;

import common.ThreadUnits;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 测试读写锁：常用于缓存
 * <p>写写互斥</p>
 * <p>读写互斥</p>
 * <p>读读不互斥</p>
 *
 * @author txl
 * @date 2022/7/24 16:22
 */
public class TestReadWriteLock {
    static class ShareCache<String, T> {
        private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        private volatile Map<String, T> cache = new HashMap<>();

        /**
         * 读缓存
         *
         * @param key
         * @return
         */
        public T readCache(String key) {
            try {
                readWriteLock.readLock().lock();
                System.out.println(Thread.currentThread().getName() + "正在读取缓存" + cache.get(key));
                ThreadUnits.sleep(1, TimeUnit.SECONDS);
                return cache.get(key);
            } finally {
                System.out.println(Thread.currentThread().getName() + "读取缓存" + cache.get(key) + "完成");
                readWriteLock.readLock().unlock();
            }
        }

        /**
         * 写缓存
         *
         * @param key
         * @param obj
         */
        public void writeCache(String key, T obj) {
            try {
                readWriteLock.writeLock().lock();
                System.out.println(Thread.currentThread().getName() + "正在写入缓存" + key);
                ThreadUnits.sleep(1, TimeUnit.SECONDS);
                cache.put(key, obj);
            } finally {
                System.out.println(Thread.currentThread().getName() + "写入缓存" + key + "完成");
                readWriteLock.writeLock().unlock();
            }
        }

        /**
         * 清空缓存
         */
        public void clearCache() {
            cache = new HashMap<>();
        }
    }

    @Test
    public void testReadWriteLock() {
        ShareCache<String, String> shareCache = new ShareCache<>();

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                shareCache.writeCache(String.valueOf(finalI), String.valueOf(finalI));
            }, "AAA" + finalI).start();
        }

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                shareCache.readCache(String.valueOf(finalI));
            }, "BBB" + finalI).start();
        }

        ThreadUnits.mainYield();
    }
}
