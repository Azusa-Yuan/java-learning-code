package Asuza.elasticsearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author Azusa-Yuan
 * @description
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(indexName = "person")
public class Person {
    @Id
    private String id;
    private String name;
    private int age;
}
