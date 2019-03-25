package com.github.tevid.jbox.mq.nsq;

import com.sproutsocial.nsq.Publisher;
import com.sproutsocial.nsq.Subscriber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class NSQTest {
    @Autowired
    NsqService nsqService;

    @Test
    public void publish() throws InterruptedException {
//        Publisher publisher = nsqService.getPublisher();
//
//        for(int i=1;i<=20;i++) {
//
//            byte[] data = ("Hello nsq"+i).getBytes();
//            try {
//                publisher.publish("test", data);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            System.out.println("publish " + new String(data));
//            Thread.sleep(2000);
//        }
//    }
//
//    @Test
//    public void subscribe() throws InterruptedException {
//        Subscriber subscriber = new Subscriber("192.168.8.167", "192.168.8.170");
//        subscriber.subscribe("test", "test_channel_java", NSQTest::handleData);
//
//        Thread.sleep(20000);
//    }
//
//    public static void handleData(byte[] data) {
//        System.out.println("Received:" + new String(data));
    }
}
