package threads.Volatile;

import common.ThreadUnits;
import org.junit.Test;

/**
 * 单例模式下，单例成员变量必须使用volatile关键字修饰，防止指令重排序
 */
public class TestSingleton {
    private static class Singleton {
        // 私有单例
        private static volatile Singleton singleton;

        // 私有构造方法
        private Singleton() {
            System.out.println("创建单例 singleton 成功！");
        }

        /**
         * 构建Singleton实例（单实例）
         * <p>双端校验锁机制实现单例</p>
         */
        public static Singleton buildSingleton() {
            if (singleton == null) {
                synchronized (Singleton.class) {
                    if (singleton == null) {
                        singleton = new Singleton();
                    }
                }
            }
            return singleton;
        }
    }

    /**
     * 测试多线程环境单例模式
     */
    @Test
    public void testMultiThread() {
        for (int i = 0; i < 5; i++) {
            new Thread(Singleton::buildSingleton, String.valueOf(i)).start();
        }
        ThreadUnits.mainYield();
    }

    /**
     * 测试单线程环境单例模式
     */
    @Test
    public void testSingleThread() {
        System.out.println(Singleton.buildSingleton() == Singleton.buildSingleton());
        System.out.println(Singleton.buildSingleton() == Singleton.buildSingleton());
        System.out.println(Singleton.buildSingleton() == Singleton.buildSingleton());
        ThreadUnits.mainYield();
    }
}
