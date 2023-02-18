package threads.Volatile;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 测试volatile禁止指令重排
 *
 * @author txl
 * @date 2023/2/11 19:17
 */
public class TestDoNotReorder {
    private static int x, y, a, b;

    @SneakyThrows
    @Test
    public void testSerial() {
        Set<String> resultSet = new HashSet<>();

        for (int i = 0; i < 10000000; i++) {
            x = 0;
            y = 0;
            a = 0;
            b = 0;

            Thread one = new Thread(() -> {
                a = y;
                x = 1;
            });
            Thread second = new Thread(() -> {
                b = x;
                y = 1;
            });

            one.start();
            second.start();
            one.join();
            second.join();
            resultSet.add("a=" + a + " b=" + b);
            System.out.println(resultSet);
        }
    }
}
