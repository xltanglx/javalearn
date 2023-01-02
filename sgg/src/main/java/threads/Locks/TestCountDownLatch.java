package threads.Locks;

import common.ThreadUnits;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 火箭发射倒计时
 *
 * @author txl
 * @date 2022/8/12 0:24
 */
public class TestCountDownLatch {
    @Test
    public void testCountDownLatch() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "进入了图书馆");
                int learnTime = new Random().nextInt(5);
                ThreadUnits.sleep(learnTime, TimeUnit.SECONDS);
                System.out.println(Thread.currentThread().getName() + "学习了" + learnTime + "个小时走出图书馆");
                countDownLatch.countDown();
            }, i + "号同学 ").start();
        }

        System.out.println(Thread.currentThread().getName() + "管理员进入了图书馆");
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "管理员最后走出图书馆");
    }
}
