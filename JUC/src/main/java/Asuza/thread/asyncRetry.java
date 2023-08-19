package Asuza.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * @author Azusa-Yuan
 * @description 异步任务异常重复执行
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */
public class asyncRetry {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        int maxRetries = 3;

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> runWithRetry(()-> {
            try {
                return task(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, maxRetries), executor);

        future.thenAccept(result -> {
            System.out.println("Result: " + result);
            executor.shutdown();
        });
        System.out.println("我结束了");
    }

    private static <T> T runWithRetry(Supplier<T> task, int maxRetries) {
        int retryCount = 0;
        while (true) {
            try {
                System.out.println(Thread.currentThread());
                return task.get();
            } catch (Exception e) {
                if (retryCount >= maxRetries) {
                    throw new RuntimeException("Exceeded max retry attempts", e);
                }
                System.out.println("Retrying...");
                retryCount++;
            }
        }
    }

    public static String task(int param) throws InterruptedException {
        System.out.println(Thread.currentThread());
        Thread.sleep(2000);
        // 模拟异步任务，这里抛出异常作为示例
        if (Math.random() < 0.5) {
            throw new RuntimeException("Async task failed");
        }
        return "Async task completed";
    }
}
