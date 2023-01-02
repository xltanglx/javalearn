package common;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 线程工具
 */
public class ThreadUnits {
    /**
     * 睡眠
     *
     * @param sleepTime
     */
    public static void sleep(long sleepTime, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 随机睡眠[0~bound)
     *
     * @param bound
     * @param timeUnit
     */
    public static long randomSleep(int bound, TimeUnit timeUnit) {
        long sleepTime = new Random().nextInt(bound);
        sleep(sleepTime, timeUnit);
        return sleepTime;
    }

    /**
     * 阻塞主线程
     */
    public static void mainYield() {
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
    }

    /**
     * 主动GC
     */
    public static void gc() {
        System.gc();
        System.runFinalization();
    }
}
