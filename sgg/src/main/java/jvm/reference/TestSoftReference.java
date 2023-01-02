package jvm.reference;

import common.ThreadUnits;
import org.junit.Test;

import java.lang.ref.SoftReference;

/**
 * @author txl
 * @date 2022/8/15 23:59
 */
public class TestSoftReference {
    /**
     * 软引用测试：内存够用的情况
     */
    @Test
    public void testEnoughSoftReference() {
        MyObject myObject = new MyObject("zhangsan", 14);
        SoftReference<MyObject> softReference = new SoftReference<>(myObject);
        System.out.println("before gc, myReference: " + softReference.get());
        myObject = null;
        ThreadUnits.gc();
        System.out.println("after gc, myReference: " + softReference.get());
    }

    /**
     * 软引用测试：内存不够用的情况
     * <p>JVM参数配置：-Xms5M -Xmx5M -XX:+PrintGCDetails</p>
     */
    @Test
    public void testNoEnoughSoftReference() {
        MyObject myObject = new MyObject("lisi", 18);
        SoftReference<MyObject> softReference = new SoftReference<>(myObject);
        System.out.println("before gc, myReference: " + softReference.get());
        try {
            myObject = null;
            byte[] bytes = new byte[30 * 1024 * 1024];
        }  finally {
            System.out.println("after gc, myReference: " + softReference.get());
        }
    }
}
