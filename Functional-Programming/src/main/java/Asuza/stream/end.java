package Asuza.stream;

import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static Asuza.stream.Author.getAuthors;

/**
 * @author Azusa-Yuan
 * @description
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */
public class end {

    @Test
    public static void forEach(){
        // 用于遍历流中的元素
        getAuthors().stream()
                .forEach(author -> System.out.println(author.getName()));
    }

    @Test
    public static void count(){
        // 用于统计流中的元素个数
        long count = getAuthors().stream()
                .count();
        System.out.println(count);
    }

    @Test
    public static void max(){
        // 用于获取流中的最大值 后续会补充Optional知识
        Optional<Author> max = getAuthors().stream()
                .max((o1, o2) -> o1.getAge() - o2.getAge());

        System.out.println(max.get());
    }

    @Test
    public static void min(){
        // 用于获取流中的最小值 后续会补充Optional知识
        Optional<Author> min = getAuthors().stream()
                .min((o1, o2) -> o1.getAge() - o2.getAge());

        System.out.println(min.get());
    }

    @Test
    public static void collect(){
        // 把当前流转换成一个集合。
        //        获取一个存放所有作者名字的List集合。
        List<Author> authors = getAuthors();
        List<String> nameList = authors.stream()
                .map(author -> author.getName())
                .collect(Collectors.toList());
        System.out.println(nameList);
    }

    @Test
    public static void collect2(){
        //        获取一个Map集合，map的key为作者名，value为List<Book>
        List<Author> authors = getAuthors();

        Map<String, List<Book>> map = authors.stream()
                .distinct()
                .collect(Collectors.toMap(author -> author.getName(), author -> author.getBooks()));

        System.out.println(map);
    }

    //-------------------------------查找与匹配-----------------------------------

    @Test
    public static void anyMatch(){
        // 用于判断流中是否有满足条件的元素，如果有返回true，否则返回false。
        boolean b = getAuthors().stream()
                .anyMatch(author -> author.getAge() > 20);
        System.out.println(b);
    }

    @Test
    public static void allMatch(){
        // 可以用来判断是否都符合匹配条件，结果为boolean类型。如果都符合结果为true，否则结果为false。
        //        判断是否所有的作家都是成年人
        List<Author> authors = getAuthors();
        boolean flag = authors.stream()
                .allMatch(author -> author.getAge() >= 18);
        System.out.println(flag);
    }

    @Test
    public static void noneMatch(){
        // 可以用来判断是否都不符合匹配条件，结果为boolean类型。如果都不符合结果为true，否则结果为false。
        //        判断是否所有的作家都是成年人
        List<Author> authors = getAuthors();
        boolean flag = authors.stream()
                .noneMatch(author -> author.getAge() >= 18);
        System.out.println(flag);
    }

    @Test
    public static void findAny(){
        // 用于获取流中的任意一个元素，返回Optional类型。
        Optional<Author> any = getAuthors().stream()
                .findAny();
        System.out.println(any.get());
    }

    @Test
    public static void reduce(){
        // 对流中的数据按照你指定的计算方式计算出一个结果。（缩减操作)
        //        计算所有作家的年龄总和
        // reduce的作用是把stream中的元素给组合起来，我们可以传入一个初始值，
        // 它会按照我们的计算方式依次拿流中的元素和初始化值进行计算，计算结果再和后面的元素计算
        // reduce两个参数的重载形式内部的计算方式如下：
        //        T result = identity;
        //        for (T element : this stream)
        //        result = accumulator.apply(result, element)
        //        return result;
        List<Author> authors = getAuthors();
        Integer reduce = authors.stream()
                .map(author -> author.getAge())
                .reduce(0, (a, b) -> a + b);
        System.out.println(reduce);
    }
}
