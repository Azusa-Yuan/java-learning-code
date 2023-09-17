package Asuza.rocketmq.event;

import Asuza.rocketmq.utils.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author Azusa-Yuan
 * @description
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */

@Slf4j
@Service
@RocketMQMessageListener(topic = "test-mq", consumerGroup = "test-application")
public class listenEvent implements RocketMQListener<BaseEvent<String>> {

    @Override
    public void onMessage(BaseEvent<String> stringBaseEvent) {
        log.info("接收到MQ消息 {}", stringBaseEvent.getData());
        log.info("接收到MQ消息的时间戳 {}", stringBaseEvent.getTimestamp());
    }
}
