package threads.Practice;

import common.ThreadUnits;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程分别打印 A，B，C，要求这三个线程一起运行，打印 n 次，输出形如“ABCABCABC…”的字符串。
 *
 * @author txl
 * @date 2022/7/31 0:33
 */
public class Practice001 {
    /**
     * 要循环的次数
     */
    private int n = 0;
    /**
     * 当前状态值，保证线程循环打印
     */
    private int state = 0;
    /**
     * 锁，保证状态值安全同步
     */
    private final ReentrantLock lock = new ReentrantLock();
    /**
     * synchronized同步对象
     */
    private final Object object = new Object();

    @AllArgsConstructor
    @NoArgsConstructor
    class MyThread implements Runnable {
        private String str;
        private Integer flag;

        @Override
        public void run() {
            for (int i = 0; i < n; ) {
                lock.lock();
                if (state % 3 == flag) {
                    state++;
                    System.out.print(str);
                    i++; // 因为要打印n次，所以只有满足条件才能i++
                }
                lock.unlock();
            }
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    class MyThreadV2 implements Runnable {
        private String str;
        private Integer flag;

        @Override
        public void run() {
            for (int i = 0; i < n; i++) {
                synchronized (object) {
                    while (state % 3 != flag) {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print(str);
                    state++;
                    object.notifyAll();
                }
            }
        }
    }

    @Test
    public void testPractice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要循环的次数：");
        n = scanner.nextInt();
        System.out.println("要循环的次数为：" + n + "次");

        // 方法一：ReentrantLock
        new Thread(new MyThread("A", 0), "A").start();
        new Thread(new MyThread("B", 1), "B").start();
        new Thread(new MyThread("C", 2), "C").start();

        // 方法二：wait()、notify()同步机制
        // new Thread(new MyThreadV2("A", 0), "A").start();
        // new Thread(new MyThreadV2("B", 1), "B").start();
        // new Thread(new MyThreadV2("C", 2), "C").start();

        ThreadUnits.mainYield();
    }
}