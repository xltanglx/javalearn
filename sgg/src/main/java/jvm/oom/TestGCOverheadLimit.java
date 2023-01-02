package jvm.oom;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试GC垃圾器回收效率过低导致的OOM
 * java.lang.OutOfMemoryError:GC overhead limit exceeded
 *
 * @author txl
 * @date 2022/8/16 0:54
 */
public class TestGCOverheadLimit {
    /**
     * JVM参数配置演示
     * -Xms10m -Xmx10m -XX:+PrintGCDetails
     */
    @Test
    public void testGCOverheadLimit() {
        int i = 0;
        List<String> list = new ArrayList<>();
        try {
            while (true) {
                list.add(String.valueOf(++i).intern());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }
}
