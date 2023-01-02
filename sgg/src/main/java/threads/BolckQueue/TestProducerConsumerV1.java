package threads.BolckQueue;

import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 生产者-消费者 V1
 * <p>synchronized版</p>
 *
 * @author txl
 * @date 2022/7/24 22:01
 */
public class TestProducerConsumerV1 {
    class ShareData {
        private Integer num = 0;

        /**
         * 生产：num+1
         */
        public synchronized void produce() throws InterruptedException {
            while (num != 0) {
                this.wait();
            }
            num++;
            System.out.println(Thread.currentThread().getName() + " producing");
            this.notifyAll();
        }

        /**
         * 消费：num-1
         *
         * @return
         */
        public synchronized void consume() throws InterruptedException {
            while (num == 0) {
                this.wait();
            }
            num--;
            System.out.println(Thread.currentThread().getName() + " consuming");
            this.notifyAll();
        }
    }

    @Test
    public void testProducerConsumerV2() {
        ShareData shareData = new ShareData();
        // 5个生产者
        Executor producers = Executors.newFixedThreadPool(5);
        // 5个消费者
        Executor consumers = Executors.newFixedThreadPool(5);

        // 生产
        for (int i = 0; i < 5; i++) {
            producers.execute(() -> {
                try {
                    shareData.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        // 消费
        for (int i = 0; i < 5; i++) {
            consumers.execute(() -> {
                try {
                    shareData.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
