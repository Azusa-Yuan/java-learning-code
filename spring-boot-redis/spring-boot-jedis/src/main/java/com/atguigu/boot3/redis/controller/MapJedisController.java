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
public class MapJedisController {
    // ["sda", "asfsa", "sda", "45"]
    @Autowired //如果给redis中保存数据会使用默认的序列化机制，导致redis中保存的对象不可视
    RedisTemplate<Object, Object> redisTemplate;

    @PostMapping("/hash/save")
    public String add(@RequestBody Person person){
        redisTemplate.opsForHash().put(person.getName()+"hash", person.getBooks(),person.getBooks());
        redisTemplate.opsForHash().put(person.getName()+"hash", person.getName(),person.getName());
        redisTemplate.opsForHash().put(person.getName()+"hash", person.getAge(),person.getAge());
        log.info(person.toString());
        log.info("添加hash成功");
        //调用service新增部门
        return "ok";
    }

    // 数组反序列化会出错  暂时不解决
    @GetMapping ("/hash/get")
    public Object get(String name){
        return redisTemplate.opsForHash().entries(name+"hash").get("name");
    }

}
