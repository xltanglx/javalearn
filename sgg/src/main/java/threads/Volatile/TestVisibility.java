package threads.Volatile;

import common.ThreadUnits;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 测试volatile的可见性
 */
public class TestVisibility {
    // private Integer num = 0;
    private volatile Integer num = 0;

    public void numAdd() {
        this.num += 60;
    }

    @Test
    public void testVisibility() {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " coming in, num = " + num);
            ThreadUnits.sleep(3, TimeUnit.SECONDS);
            numAdd();
            System.out.println(Thread.currentThread().getName() + " outing, num = " + num);
        }, "AAA").start();
        System.out.println(Thread.currentThread().getName() + " coming in, num = " + num);

        // main线程首次访问num时，会从主内存中取值，所以要想演示可见性这个case，线程AAA的num增加操作要在main线程访问num之后
        // 如果num未用volatile修饰，线程AAA修改num后，main线程不感知，num仍然等于0（CPU cache中获取num的值）
        // 如果num用volatile修饰，线程AAA修改num后，强制main线程从主内存获取num，num等于60（跳过CPU cache）
        while (num == 0) {

        }
        System.out.println(Thread.currentThread().getName() + " outing, num = " + num);
    }
}
