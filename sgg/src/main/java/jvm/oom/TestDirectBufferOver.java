package jvm.oom;

import org.junit.Test;
import sun.misc.VM;

import java.nio.ByteBuffer;

/**
 * 测试直接内存溢出
 * java.lang.OutOfMemoryError:Direct buffer memory
 *
 * @author txl
 * @date 2022/8/16 0:58
 */
public class TestDirectBufferOver {
    /**
     * JVM参数配置演示
     * -XX:MaxDirectMemorySize=5m -XX:+PrintGCDetails
     */
    @Test
    public void testGCDirectBuffer() {
        System.out.println("配置的maxDirectMemory：" + (VM.maxDirectMemory() / (double) 1024 / 1024) + "MB");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ByteBuffer bb = ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }
}
