package threads.BolckQueue;

import common.ThreadUnits;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 测试同步阻塞队列SynchronousQueue
 *
 * @author txl
 * @date 2022/7/24 21:49
 */
public class TestSynchronousQueue {
    @Test
    public void testSynchronousQueue() {
        BlockingQueue<Integer> synchronousQueue = new SynchronousQueue();

            new Thread(() -> {
                try {
                    for (int i = 0; i < 3; i++) {
                        System.out.println(Thread.currentThread().getName() + " coming.");
                        synchronousQueue.put(1);
                        System.out.println(Thread.currentThread().getName() + " ending.");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "AAA").start();

        ThreadUnits.sleep(1, TimeUnit.SECONDS);
        System.out.println(Thread.currentThread().getName() + " coming.");
        while (synchronousQueue.poll() != null) {
            ThreadUnits.sleep(3, TimeUnit.SECONDS);
        }
        System.out.println(Thread.currentThread().getName() + " ending.");
    }
}
