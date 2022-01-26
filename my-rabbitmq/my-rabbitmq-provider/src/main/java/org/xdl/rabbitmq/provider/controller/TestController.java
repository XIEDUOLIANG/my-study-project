package org.xdl.rabbitmq.provider.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xdl.rabbitmq.provider.config.DirectExchangeConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
    public void sendMessage() {
        Map<String,String> map = new HashMap<>();
        map.put("id", UUID.randomUUID().toString());
        map.put("msg","message");
        rabbitTemplate.convertAndSend("defaultDirectExchange", DirectExchangeConfig.defaultQueueKey,map);
    }
}
