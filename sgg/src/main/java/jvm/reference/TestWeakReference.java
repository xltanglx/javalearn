package jvm.reference;

import common.ThreadUnits;
import org.junit.Test;

import java.lang.ref.WeakReference;

/**
 * @author txl
 * @date 2022/8/16 0:00
 */
public class TestWeakReference {
    /**
     * 弱引用测试
     */
    @Test
    public void testWeakReference() {
        MyObject myObject = new MyObject("zhangsan", 14);
        WeakReference<MyObject> weakReference = new WeakReference<>(myObject);
        System.out.println("before gc, myReference: " + weakReference.get());

        myObject = null;
        ThreadUnits.gc();
        System.out.println("after gc, myReference: " +weakReference.get());
    }
}
