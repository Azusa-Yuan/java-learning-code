package Asuza.thread;

import java.util.concurrent.*;

/**
 * @author Azusa-Yuan
 * @description
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */
class MyCallable implements Callable<Integer>{

    //final修饰的变量只能被赋值一次,赋值后值不再改变
    private final int max_num;
    public MyCallable(int max){
        this.max_num = max;
    }

    public Integer call() throws Exception {
//        Thread.sleep(2000);
        return countSum(max_num);
    }
    public int countSum(int max_num){
        int sum = 0;
        for (int i = 0; i < max_num; i++) {
            sum += i;
        }
        return sum;
    }
}

public class futureClass {
    public static void main(String[] args) throws Exception{
        // 使用线程池运行callable
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(new MyCallable(10));
        System.out.println("Result " + future.get());
        executor.shutdown();

        // 如果要使用thread 要套一层futureTask
        FutureTask<Integer> futureTask = new FutureTask<>(new MyCallable(20));
        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println("Result " + futureTask.get());

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("i = " + i);
            }
            return null;
        });

        Thread.sleep(2000);
    }
}
