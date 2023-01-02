package jvm.oom;

import org.junit.Test;

/**
 * 测试堆内存溢出
 * java.lang.OutOfMemoryError: Java heap space
 *
 * @author txl
 * @date 2022/8/29 0:32
 */
public class TestHeapSpaceOver {
    /**
     * JVM参数配置演示
     * -Xms10m -Xmx10m
     */
    @Test
    public void testHeapSpaceOver() {
        byte[] bytes = new byte[30 * 1024 * 1024];
    }
}
