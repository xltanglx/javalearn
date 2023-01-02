package threads.Locks;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试可重入锁
 * <p>synchronized、ReentrantLock都是可重入锁，可防止死锁</p>
 */
public class TestReentrantLock {
    private Lock lock = new ReentrantLock();

    public synchronized void sendMsgA() {
        System.out.println(Thread.currentThread().getName() + " sendMsgA()");
        sendMsgB();
    }

    public synchronized void sendMsgB() {
        System.out.println(Thread.currentThread().getName() + " sendMsgB()");
    }

    public void sendMsgC() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " sendMsgC()");
            sendMsgD();
        } finally {
            lock.unlock();
        }
    }

    public void sendMsgD() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " sendMsgD()");
        } finally {
            lock.unlock();
        }
    }

    @Test
    public void testSynchronized() {
        new Thread(this::sendMsgA, "ThreadAAA").start();
        new Thread(this::sendMsgC, "ThreadBBB").start();
    }
}
