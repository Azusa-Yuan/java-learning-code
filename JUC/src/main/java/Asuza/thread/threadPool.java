package Asuza.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Azusa-Yuan
 * @description
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */

public class threadPool {
    public static void main(String[] args) {
        // 核心线程池大小
        int corePoolSize = 2;
        // 最大线程池大小
        int maximumPoolSize = 5;
        // 线程池中超过corePoolSize数目的空闲线程最大存活时间  这个当时答错了
        long keepAliveTime = 60;
        // 时间单位
        TimeUnit unit = TimeUnit.SECONDS;
        // 线程池中的任务队列
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);


        //提交任务
        for (int i = 0; i < 100; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread());
                }
            });
        }
        executor.shutdown();
    }
}
