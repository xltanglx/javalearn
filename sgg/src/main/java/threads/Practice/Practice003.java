package threads.Practice;

import common.ThreadUnits;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过 N 个线程顺序循环打印从 0 至 100
 *
 * @author txl
 * @date 2022/7/31 0:33
 */
public class Practice003 {

    private int threadCount;
    private final ReentrantLock lock = new ReentrantLock();
    private final int printCount = 100;
    private int num = 0;
    private final Object object = new Object();

    @AllArgsConstructor
    @NoArgsConstructor
    class MyThread implements Runnable {
        private Integer tag;

        @Override
        public void run() {
            while (num < printCount) {
                lock.lock();
                if (num % threadCount == tag && num < printCount) {
                    System.out.println(getThreadName() + ", state: " + num);
                    num++;
                }
                lock.unlock();
            }
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    class MyThreadV2 implements Runnable {
        private Integer tag;

        @SneakyThrows
        @Override
        public void run() {
            while (num < printCount) {
                synchronized (object) {
                    while (num % threadCount != tag) {
                        object.wait();
                    }
                    if (num < printCount) {
                        System.out.println(getThreadName() + ", state: " + num);
                    }
                    num++;
                    object.notifyAll();
                }
            }
        }
    }

    @Test
    public void testPractice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要创建的线程数量：");
        threadCount = scanner.nextInt();
        System.out.println("共创建了" + threadCount + "个线程");

        for (int i = 0; i < threadCount; i++) {
            new Thread(new MyThreadV2(i), String.valueOf((char) ('A' + i))).start();
        }

        ThreadUnits.mainYield();
    }

    /**
     * 获取当前线程名称
     *
     * @return
     */
    private String getThreadName() {
        return "Thread" + Thread.currentThread().getName();
    }
}


