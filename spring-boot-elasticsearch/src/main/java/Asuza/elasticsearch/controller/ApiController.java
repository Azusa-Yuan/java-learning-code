package Asuza.elasticsearch.controller;

import Asuza.elasticsearch.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.*;

/**
 * @author Azusa-Yuan
 * @description
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */
@RestController
public class ApiController{

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;


    @PostMapping("/person")
    public String save(@RequestBody Person person) {
        Person savedEntity = elasticsearchOperations.save(person);
        return savedEntity.getId();
    }

    @GetMapping("/person/{id}")
    public Person findById(@PathVariable("id")  Long id) {
        return elasticsearchOperations.get(id.toString(), Person.class);
    }

//    @GetMapping("/indic")
//    public Person findIndic() {
//        return elasticsearchOperations.ind
//    }
}
