package threads.BolckQueue;

import common.ThreadUnits;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * 生产者-消费者 V3
 * <p>BlockQueue版：此种模式没有全局同步控制标签&锁机制（无法保证原子性），同步控制也需要sleep()的帮助，并且只能一个生产者，一个消费者</p>
 *
 * @author txl
 * @date 2022/7/24 22:01
 */
public class TestProducerConsumerV3 {
    private volatile Boolean enable = true;

    class ShareData {
        private BlockingQueue<Integer> blockingQueue;

        public ShareData(BlockingQueue<Integer> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        /**
         * 生产：num+1
         */
        public void produce() throws InterruptedException {
            while (enable) {
                System.out.println(Thread.currentThread().getName() + " producing");
                blockingQueue.offer(1, 1, TimeUnit.SECONDS);
                ThreadUnits.sleep(1, TimeUnit.SECONDS);
            }
            System.out.println("大老板叫停了，生产者停止生产！");
        }

        /**
         * 消费：num-1
         *
         * @return
         */
        public void consume() throws InterruptedException {
            while (blockingQueue.poll(2, TimeUnit.SECONDS) != null) {
                System.out.println(Thread.currentThread().getName() + " consuming ");
            }
            if (!enable) {
                System.out.println("大老板叫停了，消费者停止消费！");
            }
        }
    }

    @Test
    public void testProducerConsumerV3() {
        ShareData shareData = new ShareData(new ArrayBlockingQueue<>(5));
        // 5个生产者
        ThreadPoolExecutor producers = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        // 5个消费者
        ThreadPoolExecutor consumers = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

        // 不停地生产（间隔1s）
        producers.execute(() -> {
            try {
                shareData.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 不停地消费
        consumers.execute(() -> {
            try {
                shareData.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        ThreadUnits.sleep(5, TimeUnit.SECONDS);
        System.out.println("大老板：大家手上的活停一停。");
        enable = false;
        producers.shutdown();
        consumers.shutdown();
        ThreadUnits.mainYield();
    }
}
