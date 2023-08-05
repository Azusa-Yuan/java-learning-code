package Asuza.lambda;

import java.util.function.Function;

/**
 * @author Azusa-Yuan
 * @description lambda表达式
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */
public class example {
    public static void main(String[] args) {
        // example1
        //        public Thread(Runnable target) {
        //            init(null, target, "Thread-" + nextThreadNum(), 0);
        //        }
        // 可推导可省略 （参数列表）->{代码} 一个语句可以省略大括号（return要同时省略） 一个参数可以省略括号 类型可推导可省略
        // 匿名内部类必须是接口 且只有一个要重写 上面就是Thread的源码  我们平时写的时候就是创建Runable接口的匿名内部类
        new Thread(()->{System.out.println("函数式编程只关注参数和运算，不关注类名");}).start();
        new Thread(()->System.out.println("函数式编程只关注参数和运算，不关注类名")).start();

        // example1
        int res = typeCover(str->Integer.parseInt(str));
        // 方法引用（Method Reference）是一种更简洁地表示已有方法的方式。它允许你通过方法的名称来引用一个方法，而不是调用它。
        // 方法引用可以使代码更加简洁，易读，并且可以与函数式接口（Functional Interfaces）一起使用。
        res = typeCover(Integer::parseInt);
        System.out.println();

    }
    //public interface Function<T, R> {
    //    Function只有一个方法apply需要重写 apply被我们定义好需要一个String类型参数
    //    /**
    //     * Applies this function to the given argument.
    //     *
    //     * @param t the function argument
    //     * @return the function result
    //     */
    //    R apply(T t);
    public static <R> R typeCover(Function<String, R> function){
        String str = "12345";
        return function.apply(str);
    }
}