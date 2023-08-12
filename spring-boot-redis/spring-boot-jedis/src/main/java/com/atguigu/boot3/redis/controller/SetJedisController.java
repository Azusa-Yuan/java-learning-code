package com.atguigu.boot3.redis.controller;

import com.atguigu.boot3.redis.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Azusa-Yuan
 * @description 保存set类型的数据
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */

@Slf4j
@RestController
public class SetJedisController {
    // ["sda", "asfsa", "sda", "45"]
    @Autowired //如果给redis中保存数据会使用默认的序列化机制，导致redis中保存的对象不可视
    RedisTemplate<Object, Object> redisTemplate;

    @PostMapping("/set/save")
    public String add(@RequestBody Person person){
        redisTemplate.opsForSet().add(person.getName()+"books", person.getBooks());
        log.info(person.toString());
        log.info("添加set成功");
        //调用service新增部门
        return "ok";
    }

    @GetMapping ("/set/get")
    public Object get(String name){
        return redisTemplate.opsForSet().members(name+"books");
    }

    @GetMapping ("/set/getNum")
    public Object getNum(String name){
        return redisTemplate.opsForSet().size(name+"books");
    }
}
