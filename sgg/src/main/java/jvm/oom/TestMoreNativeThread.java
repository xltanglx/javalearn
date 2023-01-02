package jvm.oom;

import common.ThreadUnits;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 测试创建过多线程导致的OOM
 *
 * @author txl
 * @date 2022/8/29 0:31
 */
public class TestMoreNativeThread {
    /**
     * Windows操作系统测试不出来，Linux操作系统默认一个进程最大创建1024个线程
     */
    @Test
    public void testMoreNativeThread() {
        for (int i = 0; ; i++) {
            System.out.println(i);
            new Thread(() -> ThreadUnits.sleep(3, TimeUnit.MINUTES)).start();
        }
    }
}
