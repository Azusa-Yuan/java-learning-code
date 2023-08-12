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
 * @description
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */

/*
typedef struct zskiplistNode {
    //Zset 对象的元素值
    sds ele;
    //元素权重值
    double score;
    //后向指针
    struct zskiplistNode *backward;

    //节点的level数组，保存每层上的前向指针和跨度
    struct zskiplistLevel {
        struct zskiplistNode *forward;
        unsigned long span;
    } level[];
} zskiplistNode;
typedef struct zskiplist {
    struct zskiplistNode *header, *tail;
    unsigned long length;
    int level;
} zskiplist;
 */
@Slf4j
@RestController
public class ZSetJedisController {

    @Autowired //如果给redis中保存数据会使用默认的序列化机制，导致redis中保存的对象不可视
    RedisTemplate<Object, Object> redisTemplate;

    @PostMapping("/zset/save")
    public String add(@RequestBody Person person){
        redisTemplate.opsForZSet().add("personZSet", person.getName(), person.getAge());
        log.info(person.toString());
        log.info("添加Zset成功");
        //调用service新增部门
        return "ok";
    }

    @GetMapping("/zset/get")
    public Object get(){
        return redisTemplate.opsForZSet().range("personZSet", 0, -1);
    }

    @GetMapping ("/zset/getNum")
    public Object getNum(){
        return redisTemplate.opsForZSet().size("personZSet");
    }
}
