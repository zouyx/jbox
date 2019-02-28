package com.tevid.jbox.mq.nsq;

import com.sproutsocial.nsq.Publisher;
import com.sproutsocial.nsq.Subscriber;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * nsq初始化服务
 */
public class NsqService implements InitializingBean, ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ApplicationContext applicationContext;
    @Getter
    private Publisher publisher;

    @Setter
    private String nsqd;
    @Setter
    private String failoverNsqd;
    //使用,或者;隔开
    @Setter
    private String nsqlookupd;

    @Override
    public void afterPropertiesSet() {
        startPublisher();

        startSubscriber();
    }

    /**
     * 开启生产者
     */
    private void startPublisher() {
        logger.info("链接nsqd:%s,备份nsqd:%s",nsqd,failoverNsqd);
        publisher = new Publisher(nsqd,failoverNsqd);
    }

    /**
     * 开启消费者
     */
    private void startSubscriber() {
        String[] nsqlookupds = nsqlookupd.split(",|;");
        if(ArrayUtils.isEmpty(nsqlookupds)){
            logger.info("无nsqlookupd信息不初始化,nsqlookupd:%s", nsqlookupd);
            return;
        }

        Map<String, Callback> beans = applicationContext.getBeansOfType(Callback.class);
        logger.info("Callback: {}", beans);
        Topic nsqTopic = null;
        for(final Callback callback : beans.values()) {
            nsqTopic = callback.getTopic();
            for(int i=0;i<nsqTopic.nThreads;i++){
                Subscriber subscriber = new Subscriber(nsqlookupds);
                subscriber.subscribe( nsqTopic.topic, nsqTopic.channel, callback::messageDataHandler);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
