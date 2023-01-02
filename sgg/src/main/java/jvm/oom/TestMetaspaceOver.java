package jvm.oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.Test;

/**
 * 测试MetaSpace溢出
 * java.lang.OutOfMemoryError: Metaspace
 *
 * @author txl
 * @date 2022/8/29 0:31
 */
public class TestMetaspaceOver {
    /**
     * JVM参数配置演示
     * -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10m
     */
    @Test
    public void testMetaspaceOver() {
        int i = 0;
        try {
            while (true) {
                i++;
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(TestMetaspaceOver.class);
                enhancer.setUseCache(false);
                enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> methodProxy.invokeSuper(o, new Object[1]));
                enhancer.create();
            }
        } catch (Throwable e) {
            System.out.println(i + "次后发生了异常");
            e.printStackTrace();
        }
    }
}
