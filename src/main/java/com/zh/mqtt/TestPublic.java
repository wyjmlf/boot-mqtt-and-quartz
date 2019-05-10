package com.zh.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.stereotype.Component;

/**
 * 测试消息的发送
 */
@Component
public class TestPublic {

    @Autowired
    MqttPahoClientFactory mqttClientFactory;


    public static void main(String args[]) throws MqttException {
        TestPublic testPublic=new TestPublic();
        testPublic.publishMessage("topic","你好服务器",1);
    }



    //	发布消息
    public void publishMessage(String pubTopic,String message,int qos) throws MqttException {
        System.out.println(mqttClientFactory);
        IMqttClient clientInstance = mqttClientFactory.getClientInstance("tcp://127.0.0.1:61613","mqttClientId_inbound");
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        mqttMessage.setPayload(message.getBytes());
        MqttTopic topic = clientInstance.getTopic(pubTopic);
        System.out.println(topic);
        if(topic!=null){
            MqttDeliveryToken publish = topic.publish(mqttMessage);
            if(!publish.isComplete()) {
                System.out.println("消息发布成功");
            }
        }
        /*if(null != mqttClient&& mqttClient.isConnected()) {
            System.out.println("发布消息   "+mqttClient.isConnected());
            System.out.println("id:"+mqttClient.getClientId());
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setQos(qos);
            mqttMessage.setPayload(message.getBytes());

            MqttTopic topic = mqttClient.getTopic(pubTopic);

            if(null != topic) {
                try {
                    MqttDeliveryToken publish = topic.publish(mqttMessage);
                    if(!publish.isComplete()) {
                        System.out.println("消息发布成功");
                    }
                } catch (MqttException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }else {
            reConnect();
        }*/

    }


}
