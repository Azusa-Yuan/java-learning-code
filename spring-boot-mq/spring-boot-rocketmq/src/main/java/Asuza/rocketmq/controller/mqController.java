package Asuza.rocketmq.controller;

import Asuza.rocketmq.utils.BaseEvent;
import Asuza.rocketmq.utils.EventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Azusa-Yuan
 * @description
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */

@RestController
public class mqController {
    
    @Resource
    EventPublisher eventPublisher;

    @GetMapping("/send/{message}")
    public void sendMessage(@PathVariable("message")  String message) {
        eventPublisher.publishAsync("test-mq", new BaseEvent<>("2", new Date(), message));
    }

}
