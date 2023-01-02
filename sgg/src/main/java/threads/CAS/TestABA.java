package threads.CAS;

import common.ThreadUnits;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 模拟ABA问题以及解决ABA问题
 */
public class TestABA {
    /**
     * 模拟ABA问题
     */
    @Test
    public void testSimulationABA() {
        AtomicInteger num = new AtomicInteger(0);
        int except = num.get();

        // AAA线程狸猫换太子
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " coming, expect num = " + except);

            ThreadUnits.sleep(1, TimeUnit.SECONDS);
            System.out.println(Thread.currentThread().getName() + ", compareAndSet: " + num.compareAndSet(except, 2019)
                    + " , cur num = " + num.get());

            System.out.println(Thread.currentThread().getName() + " do some thing");
            ThreadUnits.sleep(1, TimeUnit.SECONDS);

            System.out.println(Thread.currentThread().getName() + ", compareAndSet: " + num.compareAndSet(2019, except)
                    + " , cur num = " + num.get());
        }, "AAA").start();

        System.out.println(Thread.currentThread().getName() + " coming, expect num = " + except);
        ThreadUnits.sleep(3, TimeUnit.SECONDS);

        // main线程抱着假太子
        System.out.println(Thread.currentThread().getName() + ", compareAndSet: " + num.compareAndSet(except, 888)
                + " , cur num = " + num.get());
    }

    /**
     * 解决ABA问题
     * <p>版本的思想</p>
     */
    @Test
    public void testSolveABA() {
        AtomicStampedReference<Integer> numStamp = new AtomicStampedReference<>(0, 0);
        int reference = numStamp.getReference();
        // 为太子贴上标签（不可修改和撕毁）
        int stamp = numStamp.getStamp();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " coming, except num = " + reference + ", cur stamp = " + stamp);
            Integer newRef = 2019;

            ThreadUnits.sleep(1, TimeUnit.SECONDS);
            System.out.println(Thread.currentThread().getName() + ", compareAndSet: " + numStamp.compareAndSet(reference, newRef, stamp, stamp + 1)
                    + " , cur num = " + numStamp.getReference() + ", cur stamp = " + numStamp.getStamp());

            System.out.println(Thread.currentThread().getName() + " do some thing");

            ThreadUnits.sleep(1, TimeUnit.SECONDS);
            System.out.println(Thread.currentThread().getName() + ", compareAndSet: " + numStamp.compareAndSet(newRef, reference, numStamp.getStamp(), numStamp.getStamp() + 1)
                    + " , cur num = " + numStamp.getReference() + ", cur stamp = " + numStamp.getStamp());
        }, "AAA").start();

        System.out.println(Thread.currentThread().getName() + " coming, except num = " + reference + ", cur stamp = " + stamp);

        ThreadUnits.sleep(3, TimeUnit.SECONDS);
        // AAA线程狸猫换太子被识破，因为标签对不上
        System.out.println(Thread.currentThread().getName() + ", compareAndSet: " + numStamp.compareAndSet(reference, 888, stamp, stamp + 1)
                + " , cur num = " + numStamp.getReference() + ", cur stamp = " + numStamp.getStamp());
    }
}
