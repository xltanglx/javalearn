package jvm.reference;

import common.ThreadUnits;
import org.junit.Test;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * @author txl
 * @date 2022/8/16 0:06
 */
public class TestWeakHashMap {
    @Test
    public void myTest() {
        testHashMap();
        System.out.println("======= testWeakHashMap =======");
        testWeakHashMap();
    }

    @Test
    public void testHashMap() {
        HashMap<String, MyObject> hashMap = new HashMap<>();
        String key = new String("hashMap");
        MyObject myObject = new MyObject("zhangsan", 14);

        hashMap.put(key, myObject);
        System.out.println("before gc, key:" + key + ", map:" + hashMap);

        key = null;
        System.out.println("before gc, key:" + key + ", map:" + hashMap);

        ThreadUnits.gc();
        System.out.println("after gc, map:" + hashMap + ", size:" + hashMap.size());
    }

    @Test
    public void testWeakHashMap() {
        WeakHashMap<String, Object> weakHashMap = new WeakHashMap<>();
        // 这里必须通过new的方式构建对象，因为new String()存在堆内存，堆内存里面放着一个引用，该引用指向字符串常量池"weakHashMap"，堆内存的对象回收遵循可达性分析算法；
        // 而直接用"weakHashMap"则存在字符串常量池，字符串常量池的数据Full GC并不一定会回收
        String key = new String("weakHashMap");
        Object object = new Object();

        weakHashMap.put(key, object);
        System.out.println("before gc, key:" + key + ", map:" + weakHashMap);

        key = null;
        System.out.println("before gc, key:" + key + ", map:" + weakHashMap);

        ThreadUnits.gc();
        System.out.println("after gc, map:" + weakHashMap + ", size:" + weakHashMap.size());
    }
}
