package threads.Locks;

import common.ThreadUnits;
import org.junit.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量-多线程操作多个资源
 *
 * @author txl
 * @date 2022/8/12 0:25
 */
public class TestSemaphore {
    @Test
    public void testSemaphore() {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "进入停车场");
                    long waitTime = ThreadUnits.randomSleep(5, TimeUnit.SECONDS);
                    System.out.println(Thread.currentThread().getName() + "停留了" + waitTime + "小时，离开停车场");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, i + "号车").start();
        }

        ThreadUnits.mainYield();
    }
}
