package jvm.reference;

import common.ThreadUnits;
import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author txl
 * @date 2022/8/16 0:00
 */
public class TestPhantomReference {
    @Test
    public void testPhantomReference() {
        MyObject myObject = new MyObject("zhangsan", 15);
        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
        PhantomReference<MyObject> phantomReference = new PhantomReference<>(myObject, referenceQueue);

        myObject = null;
        ThreadUnits.gc();

        Reference<? extends MyObject> reference = referenceQueue.poll();
        System.out.println(reference);
    }

    class MyWeakReference extends WeakReference<Object> {
        private String value;

        public MyWeakReference(MyObject myObject, String value, ReferenceQueue<Object> referenceQueue) {
            super(myObject, referenceQueue);
            this.value = value;
        }

        @Override
        public String toString() {
            return "MyEntry{" +
                    "value='" + value + '\'' +
                    '}';
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    @Test
    public void testReferenceQueue() {
        MyObject myObject = new MyObject("lisi", 20);
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        MyWeakReference myWeakReference = new MyWeakReference(myObject, "ddd", queue);

        myObject = null;
        ThreadUnits.gc();

        // 当发生gc时，虚引用指向的对象将被回收，同时虚引用存入引用队列
        // 注意：存入引用队列的为虚引用，而不是虚引用指向的对象
        MyWeakReference clearWeakReference = (MyWeakReference) queue.poll();
        System.out.println(clearWeakReference);
    }
}
