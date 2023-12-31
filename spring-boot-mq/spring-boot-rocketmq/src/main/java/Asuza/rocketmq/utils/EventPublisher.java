package Asuza.rocketmq.utils;

import com.alibaba.fastjson.JSON;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 事件发布，消息推送。你可以在这里扩展各类的消息推送方式，如；异步消息、延迟消息、顺序消息、事务消息。官网：
 * @create 2023-07-29 09:51
 */

@Slf4j
@Service
public class EventPublisher {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 普通消息
     *
     * @param topic   主题
     * @param message 消息
     */
    public void publish(String topic, BaseEvent<?> message) {
        try {
            String mqMessage = JSON.toJSONString(message);
            log.info("发送MQ消息 topic:{} message:{}", topic, mqMessage);
            rocketMQTemplate.convertAndSend(topic, mqMessage);
        } catch (Exception e) {
            log.error("发送MQ消息失败 topic:{} message:{}", topic, JSON.toJSONString(message), e);
            // 大部分MQ发送失败后，会需要任务补偿
        }
    }

    /**
     * 延迟消息
     *
     * @param topic          主题
     * @param message        消息
     * @param delayTimeLevel 延迟时长
     */
    public void publishDelivery(String topic, BaseEvent<?> message, int delayTimeLevel) {
        try {
            String mqMessage = JSON.toJSONString(message);
            log.info("发送MQ延迟消息 topic:{} message:{}", topic, mqMessage);
            rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(message).build(), 1000, delayTimeLevel);
        } catch (Exception e) {
            log.error("发送MQ延迟消息失败 topic:{} message:{}", topic, JSON.toJSONString(message), e);
            // 大部分MQ发送失败后，会需要任务补偿
        }
    }

    /**
     * 异步消息
     *
     * @param topic          主题
     * @param message        消息
     */
    public void publishAsync(String topic, BaseEvent<?> message) {
        String mqMessage = JSON.toJSONString(message);
        rocketMQTemplate.asyncSend(topic, mqMessage, new SendCallback() {
            @Override
            public void onSuccess(SendResult var1) {
                log.info("async onSucess SendResult={}", var1);
            }

            @Override
            public void onException(Throwable var1) {
                // public void error(String msg, Throwable t);
                log.error("async onException Throwable=", var1);
            }
        });
    }

}
