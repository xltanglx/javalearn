package threads.BolckQueue;

import common.ThreadUnits;
import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 对线程之间按顺序调用，实现A>B>C三个线程启动，要求如下：
 * AA打印5次，BB打印10次，CC打印15次
 * 紧接着
 * AA打印5次，BB打印10次，CC打印15次
 * 。。。。
 * 来十轮
 *
 * @author txl
 * @date 2022/7/28 0:35
 */
public class TestCondition {
    @Test
    public void testCondition() {
        new Thread(new MyThread(1), "AAA").start();
        new Thread(new MyThread(2), "BBB").start();
        new Thread(new MyThread(3), "CCC").start();
        ThreadUnits.mainYield();
    }

    class MyThread implements Runnable {
        private final Lock lock = new ReentrantLock();
        private final Condition condition1 = lock.newCondition();
        private final Condition condition2 = lock.newCondition();
        private final Condition condition3 = lock.newCondition();
        private Condition preCondition;
        private Condition curCondition;
        private Condition nextCondition;
        private int n;

        public MyThread(int flag) {
            if (flag == 1) {
                curCondition = condition1;
                nextCondition = condition2;
                n = 5;
            } else if (flag == 2) {
                curCondition = condition2;
                nextCondition = condition3;
                n = 10;
            } else if (flag == 3) {
                curCondition = condition3;
                nextCondition = condition1;
                n = 15;
            } else {
                throw new RuntimeException("线程名称错误!");
            }
        }

        @Override
        public void run() {
            lock.lock();
            try {
                // 判断
                while (curCondition != condition1) {
                    curCondition.await();
                }
                // 干活
                for (int j = 1; j <= n; j++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + j);
                }
                // 通知
                preCondition = curCondition;
                nextCondition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
