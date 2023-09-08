package Asuza.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * Created by 周杰伦 on 2018/6/13.
 */

public class PhantomReferenceTest {

    public static int M = 1024*1024;

    public static void printlnMemory(String tag){
        Runtime runtime = Runtime.getRuntime();
        int M = PhantomReferenceTest.M;
        System.out.println("\n"+tag+":");
        System.out.println(runtime.freeMemory()/M+"M(free)/" + runtime.totalMemory()/M+"M(total)");
    }

    public static void main(String[] args) throws InterruptedException {

        PhantomReferenceTest.printlnMemory("1.原可用内存和总内存");
        byte[] object = new byte[10*PhantomReferenceTest.M];
        PhantomReferenceTest.printlnMemory("2.实例化10M的数组后");

        //建立虚引用, 与强引用关联起来
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(object,referenceQueue);

        PhantomReferenceTest.printlnMemory("3.建立虚引用后");
        System.out.println("phantomReference : "+phantomReference);
        System.out.println("phantomReference.get() : "+phantomReference.get());
        System.out.println("referenceQueue.poll() : "+referenceQueue.poll());

        //断开byte[10*PhantomReferenceTest.M]的强引用
        object = null;
        PhantomReferenceTest.printlnMemory("4.执行object = null;强引用断开后");

        System.gc();
        PhantomReferenceTest.printlnMemory("5.GC后");
        System.out.println("phantomReference : "+phantomReference);
        System.out.println("phantomReference.get() : "+phantomReference.get());
        // 对象被垃圾回收器回收时，能够收到一个系统通知。这个通知将在垃圾回收器完成对该对象回收之后，将虚引用对象放入与之关联的引用队列中。
        System.out.println("referenceQueue.poll() : "+referenceQueue.poll());

        //断开虚引用
        phantomReference = null;
        System.gc();
        PhantomReferenceTest.printlnMemory("6.断开虚引用后GC");
        System.out.println("phantomReference : "+phantomReference);
        System.out.println("referenceQueue.poll() : "+referenceQueue.poll());
    }
}