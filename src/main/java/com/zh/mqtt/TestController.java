package com.zh.mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private MqttGateway mqttGateway;
    @Resource
    TestPublic testPublic;

    @RequestMapping("/sendMqtt.do")
    public String sendMqtt(String  sendData) throws MqttException {
        mqttGateway.sendToMqtt(sendData,"hello");
       // testPublic.publishMessage("hello","你好服务器",1);
        return "OK";
    }
}