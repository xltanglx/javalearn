package threads.Locks;

import common.ThreadUnits;
import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 集齐7颗龙珠召唤神龙
 *
 * @author txl
 * @date 2022/8/12 0:24
 */
public class TestCyclicBarrier {
    @Test
    public void testCyclicBarrier() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, new Runnable() {
            @Override
            public void run() {
                System.out.println("召唤神龙！！！");
            }
        });

        for (int i = 0; i < cyclicBarrier.getParties(); i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    System.out.println("收集到一颗龙珠，当前共有" + (finalI + 1) + "颗龙珠");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
            ThreadUnits.sleep(1, TimeUnit.SECONDS);
        }

        ThreadUnits.mainYield();
    }
}
