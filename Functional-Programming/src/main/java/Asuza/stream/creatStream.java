package Asuza.stream;

import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Stream;
import static Asuza.stream.Author.getAuthors;


/**
 * @author Azusa-Yuan
 * @description
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */
public class creatStream {
    public static void main(String[] args) {
        //打印所有年龄小于18的作家的名字，并且要注意去重
        List<Author> authors = getAuthors();
        authors.
                stream()//把集合转换成流
                .distinct()//先去除重复的作家
                .filter(author -> author.getAge()<18)//筛选年龄小于18的
                .forEach(author -> System.out.println(author.getName()));//遍历打印名字
    }

    @Test
    private static void creatStream1() {
        // 单列集合： 集合对象.stream()
        List<Author> authors = getAuthors();
        Stream<Author> stream = authors.stream();
        stream.forEach(author -> System.out.println(author.getName()));
    }

    @Test
    private static void creatStream2() {
        // 双列集合： 转换成单列集合后再创建
        Map<String,Integer> map = new HashMap<>();
        map.put("蜡笔小新",19);
        map.put("黑子",17);
        map.put("日向翔阳",16);

        Stream<Map.Entry<String, Integer>> stream = map.entrySet().stream();
        stream.forEach(entry -> System.out.println(entry.getKey()+" "+entry.getValue()));
    }

    @Test
    private static void creatStream3(){
        // 数组：Arrays.stream(数组) 或者使用Stream.of来创建
        Integer[] arr = {1,2,3,4,5};
        Stream<Integer> stream = Arrays.stream(arr);
        Stream<Integer> stream2 = Stream.of(arr);
        stream.forEach(integer -> System.out.println(integer));
        // 转回数组
        arr = stream2.toArray(Integer[]::new);

    }

    @Test
    private static void filter(){
        // filter可以对流中的元素进行条件过滤，符合过滤条件的才能继续留在流中。
        List<Author> authors = getAuthors();
        authors.stream()
                .filter(author -> author.getName().length()>1)
                .forEach(author -> System.out.println(author.getName()));
    }

}
