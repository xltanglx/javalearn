package threads.Practice;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 两个线程交替打印 0~100 的奇偶数
 *
 * @author txl
 * @date 2022/7/31 0:33
 */
@NoArgsConstructor
@AllArgsConstructor
public class Practice002 {
    private Object monitor = new Object();
    private int limit;
    private volatile int count;

    Practice002(int initCount, int times) {
        this.count = initCount;
        this.limit = times;
    }

    private void print() {

        synchronized (monitor) {
            while (count < limit) {
                try {
                    System.out.println(String.format("线程[%s]打印数字:%d", Thread.currentThread().getName(), ++count));
                    monitor.notifyAll();
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //防止有子线程被阻塞未被唤醒，导致主线程不退出
            monitor.notifyAll();
        }
    }

    public static void main(String[] args) {
        Practice002 printer = new Practice002(0, 100);
        new Thread(printer::print, "odd").start();
        new Thread(printer::print, "even").start();
    }
}

