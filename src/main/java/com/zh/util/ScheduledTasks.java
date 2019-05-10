package com.zh.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/*@Component
@Configurable
@EnableScheduling*/
public class ScheduledTasks{

  /*  @Resource
    RedisTemplate redisTemplate;

    @Scheduled(fixedRate = 1000 * 1800)
    public void reportCurrentTime(){
       String mqtt_token=(String) redisTemplate.boundValueOps("mqtt_token").get();

        if(StringUtils.isEmpty(mqtt_token)){
            System.out.println(WxUrl.TOKEN_URL);
            JSONObject json=JSONObject.parseObject(HttpClientUtil.doGet(WxUrl.TOKEN_URL));
            System.out.println(json);
            mqtt_token = json.getString("access_token");

            redisTemplate.boundValueOps("mqtt_token").set(mqtt_token);
            redisTemplate.boundValueOps("mqtt_token").expire(30, TimeUnit.MINUTES);
        }
        System.out.println("执行了定时任务>>>>>>>>>>>>>>>>>>"+mqtt_token);
        SendMessage.sendtext("oncZz1r0p4uqdU5VWV7aPSUc2fWQ","你好吗？吃饭了吗？",mqtt_token);
        SendMessage.sendtext("oncZz1myHh5DKpo1uW41fNdmyUwc","你好吗？吃饭了吗？",mqtt_token);
    }*/

    //每1分钟执行一次
   // @Scheduled(cron = "0 */1 *  * * * ")
   // public void reportCurrentByCron(){
   //     System.out.println ("Scheduling Tasks Examples By Cron: The time is now " + dateFormat ().format (new Date ()));
  //  }

   // private SimpleDateFormat dateFormat(){
       // return new SimpleDateFormat ("HH:mm:ss");
   // }

}