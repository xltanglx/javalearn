package threads.BolckQueue;

import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者-消费者 V2
 * <p>ReentrantLock版本</p>
 *
 * @author txl
 * @date 2022/7/24 22:01
 */
public class TestProducerConsumerV2 {
    class ShareData {
        private final Lock lock = new ReentrantLock();
        private final Condition condition = lock.newCondition();
        private Integer num = 0;

        /**
         * 生产：num+1
         */
        public void produce() {
            try {
                lock.lock();
                while (num != 0) {
                    condition.await();
                }
                num++;
                System.out.println(Thread.currentThread().getName() + " producing");
                condition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        /**
         * 消费：num-1
         *
         * @return
         */
        public void consume() {
            try {
                lock.lock();
                while (num == 0) {
                    condition.await();
                }
                num--;
                System.out.println(Thread.currentThread().getName() + " consuming");
                condition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
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
            producers.execute(shareData::produce);
        }
        // 消费
        for (int i = 0; i < 5; i++) {
            consumers.execute(shareData::consume);
        }
    }
}
