package com.zh.mqtt;

import com.alibaba.fastjson.JSONObject;
import com.zh.util.HttpClientUtil;
import com.zh.util.SendMessage;
import com.zh.util.WxUrl;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 消息接收
 */
@Configuration
@IntegrationComponentScan
public class MqttReceiveConfig {

    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.url}")
    private String hostUrl;

    @Value("${spring.mqtt.client.id}")
    private String clientId;

    @Value("${spring.mqtt.default.topic}")
    private String defaultTopic;

    @Value("${spring.mqtt.completionTimeout}")
    private int completionTimeout ;   //连接超时

    @Resource
    RedisTemplate redisTemplate;
    @Bean
    public MqttConnectOptions getMqttConnectOptions(){
        MqttConnectOptions mqttConnectOptions=new MqttConnectOptions();
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());
        mqttConnectOptions.setServerURIs(new String[]{hostUrl});
        mqttConnectOptions.setKeepAliveInterval(2);
        return mqttConnectOptions;
    }
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    //接收通道
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    //配置client,监听的topic
    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(clientId+"_inbound", mqttClientFactory(),
                        "df/test/monitor01","name2");
        adapter.setCompletionTimeout(completionTimeout);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    //通过通道获取数据
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
                String type = topic.substring(topic.lastIndexOf("/")+1, topic.length());
                if("df/test/monitor01".equalsIgnoreCase(topic)){
                    String mqtt_token=(String) redisTemplate.boundValueOps("mqtt_token").get();
                    if(StringUtils.isEmpty(mqtt_token)){
                        JSONObject json=JSONObject.parseObject(HttpClientUtil.doGet(WxUrl.TOKEN_URL));
                        System.out.println(json);
                        mqtt_token = json.getString("access_token");
                        redisTemplate.boundValueOps("mqtt_token").set(mqtt_token);
                        redisTemplate.boundValueOps("mqtt_token").expire(30, TimeUnit.MINUTES);
                    }

                    String string = message.getPayload().toString();
                    System.out.println("执行了定时任务>>>>>>>>>>>>>>>>>>"+string);
                    SendMessage.sendtext("oncZz1r0p4uqdU5VWV7aPSUc2fWQ",string,mqtt_token);
                    SendMessage.sendtext("oncZz1myHh5DKpo1uW41fNdmyUwc",message.getPayload().toString(),mqtt_token);

                }else if("hello1".equalsIgnoreCase(topic)){
                    System.out.println("hello1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+message.getPayload().toString());
                }else {
                    System.out.println(topic+">>>>>>>>"+message.getPayload().toString());
                }
            }
        };
    }
}
