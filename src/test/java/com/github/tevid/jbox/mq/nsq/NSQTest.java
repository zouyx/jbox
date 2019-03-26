package com.github.tevid.jbox.mq.nsq;

import com.sproutsocial.nsq.Publisher;
import com.sproutsocial.nsq.Subscriber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.github.tevid.jbox.mq.nsq.CommonCache.CACHE;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class NSQTest {
    @Autowired
    NsqService nsqService;

    @Test
    public void publish() throws InterruptedException {
        Publisher publisher = nsqService.getPublisher();

        for(int i=1;i<=20;i++) {
            String key="Hello nsq"+i;

            byte[] data = key.getBytes();
            try {
                CACHE.put(key,key);
                publisher.publish("test", data);
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("publish " + new String(data));
        }
        Thread.sleep(2000);
    }
}
