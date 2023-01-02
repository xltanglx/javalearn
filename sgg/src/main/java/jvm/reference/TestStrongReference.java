package jvm.reference;

import common.ThreadUnits;
import org.junit.Test;

/**
 * @author txl
 * @date 2022/8/21 22:43
 */
public class TestStrongReference {
    @Test
    public void testStrongReference() {
        MyObject myObjectA = new MyObject("zhangsan", 10);
        MyObject myObjectB = myObjectA;
        System.out.println("before gc, myReferenceA: " + myObjectA);
        System.out.println("before gc, myReferenceB: " + myObjectB);

        myObjectA = null;
        ThreadUnits.gc();
        System.out.println("after gc, myReferenceA: " + myObjectA);
        System.out.println("after gc, myReferenceB: " + myObjectB);
    }
}
