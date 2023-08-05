package Asuza.stream;


import org.testng.annotations.Test;

import java.util.List;

import static Asuza.stream.Author.getAuthors;

/**
 * @author Azusa-Yuan
 * @description
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */
public class Intermediate {
    public static void filter() {
        // 可以对流中的元素进行条件过滤，符合过滤条件的才能继续留在流中。
        List<Author> authors = getAuthors();
        authors.stream()
                .filter(author -> author.getName().length()>1)
                .forEach(author -> System.out.println(author.getName()));
    }

    @Test
    public static void map() {
        // map 可以把对流中的元素进行计算或转换。 例如 打印所有作家的姓名
        List<Author> authors = getAuthors();
        authors
                .stream()
                .map(author -> author.getName())
                .forEach(name->System.out.println(name));
    }


    @Test
    public static void distinct(){
        // 可以去除流中的重复元素。
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .forEach(author -> System.out.println(author.getName()));
    }

    @Test
    public static void sorted(){
        //        对流中的元素按照年龄进行降序排序，并且要求不能有重复的元素。
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .sorted((o1, o2) -> o2.getAge()-o1.getAge())
                .forEach(author -> System.out.println(author.getAge()));
    }

    @Test
    public static void limit(){
        // 可以设置流的最大长度，超出的部分将被抛弃
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .sorted((o1, o2) -> o2.getAge()-o1.getAge())
                .limit(2)
                .forEach(author -> System.out.println(author.getName()));
    }

    @Test
    public static void skip(){
        //    跳过流中的前n个元素，返回剩下的元素
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .sorted((o1, o2) -> o2.getAge()-o1.getAge())
                .skip(1)
                .forEach(author -> System.out.println(author.getName()));
    }

    @Test
    public static void flatMap(){
        //  map只能把一个对象转换成另一个对象来作为流中的元素。
        //  而flatMap可以把一个对象转换成多个对象作为流中的元素。
        //        打印所有书籍的名字。要求对重复的元素进行去重。
        List<Author> authors = getAuthors();

        authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .forEach(book -> System.out.println(book.getName()));
    }
}
