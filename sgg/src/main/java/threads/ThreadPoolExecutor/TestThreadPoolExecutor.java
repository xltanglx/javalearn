package threads.ThreadPoolExecutor;


import java.util.concurrent.*;

/**
 * 自定义线程池，并测试四大拒绝策略
 * <p>DiscardPolicy</p>
 *
 * @author txl
 * @date 2022/8/14 16:15
 */
public class TestThreadPoolExecutor {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        try {
            for (int i = 0; i < 10; i++) {
                threadPoolExecutor.execute(() -> System.out.println(Thread.currentThread().getName() + "办理业务"));
            }
        } finally {
            threadPoolExecutor.shutdown();
        }

        System.out.println(Thread.currentThread().getName());
    }
}
