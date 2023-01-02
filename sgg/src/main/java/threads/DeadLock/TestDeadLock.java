package threads.DeadLock;

import common.ThreadUnits;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 测试死锁
 * <p>jps：查看当前正在运行的java程序</p>
 * <p>jstack：查看当前正在运行的java程序的堆栈</p>
 *
 * @author txl
 * @date 2022/8/14 18:47
 */
public class TestDeadLock {
    class DeadLock implements Runnable {
        private final String lockA;
        private final String lockB;

        public DeadLock(String lockA, String lockB) {
            this.lockA = lockA;
            this.lockB = lockB;
        }

        @Override
        public void run() {
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + "获得锁" + lockA + "，尝试获取锁" + lockB);
                ThreadUnits.sleep(1, TimeUnit.SECONDS);
                synchronized (lockB) {
                    System.out.println(Thread.currentThread().getName() + "获得锁" + lockB + "，尝试获取锁" + lockA);
                }
            }
        }
    }

    @Test
    public void testDeadLock() {
        String lockA = "lockA", lockB = "lockB";
        new Thread(new DeadLock(lockA, lockB), "AAA").start();
        new Thread(new DeadLock(lockB, lockA), "BBB").start();

        ThreadUnits.mainYield();
    }
}
