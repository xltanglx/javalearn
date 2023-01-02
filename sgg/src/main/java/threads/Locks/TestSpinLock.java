package threads.Locks;

import common.ThreadUnits;
import lombok.Data;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 手写一个自旋锁
 */
public class TestSpinLock {
    @Data
    class ShareData {
        private Integer unsafeNum = 0;
        private Integer safeNum = 0;
        SpinLock spinLock = new SpinLock();

        /**
         * 未加锁
         */
        public void numAdd() {
            unsafeNum++;
        }

        /**
         * 加锁
         */
        public void numAddWithLock() {
            try {
                spinLock.myLock();
                safeNum++;
            } finally {
                spinLock.myUnLock();
            }
        }
    }

    /**
     * 自定义自旋锁
     */
    class SpinLock {
        private final AtomicReference<Thread> atomicReference = new AtomicReference<>();

        /**
         * 加锁
         */
        public void myLock() {
            while (!atomicReference.compareAndSet(null, Thread.currentThread())) {

            }
        }

        /**
         * 解锁
         */
        public void myUnLock() {
            atomicReference.compareAndSet(Thread.currentThread(), null);
        }
    }

    @Test
    public void testSpinLock() {
        // 共享数据
        ShareData shareData = new ShareData();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    shareData.numAdd();
                    shareData.numAddWithLock();
                }
            }, "AAA").start();
        }

        ThreadUnits.mainYield();
        System.out.println("unsafeNum=" + shareData.getUnsafeNum() + ", safeNum=" + shareData.getSafeNum());
    }
}
