package Asuza.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Azusa-Yuan
 * @description await() 和 signal()的使用
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */


public class ReentrantLockExample {
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();

    public static void await() {
        lock.lock();
        try {
            System.out.println("被停止住啦");
            condition.await();
            System.out.println("被停止住啦");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void signal(){
        lock.lock();
        try {
            Thread.sleep(2000);
            System.out.println("我先执行了");
            condition.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    // 演示了await()和signal()的使用 对应sychronized的wait()和notify()
    public static void main(String[] args) {
        new Thread(ReentrantLockExample::await).start();
        new Thread(ReentrantLockExample::signal).start();
    }
}
