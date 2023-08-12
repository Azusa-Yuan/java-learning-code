package Asuza.DesignPattern;

/**
 * @author Azusa-Yuan
 * @description
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */
public class Singleton {

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}

class Singleton1 {
    // 懒汉式 会有线程安全问题
    private static Singleton1 instance;

    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        if (instance == null) {
            instance = new Singleton1();
        }
        return instance;
    }
}

class Singleton2 {
    // 饿汉式
    private static Singleton2 instance = new Singleton2();

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        return instance;
    }
}

class Singleton3{
    // 懒汉式 线程安全
    private static volatile Singleton3 instance;

    private Singleton3() {
    }

    public static  Singleton3 getInstance() {
        if (instance == null) {
            synchronized (Singleton3.class) {
                if (instance == null) {
                    instance = new Singleton3();
                }
            }
        }
        return instance;
    }
}