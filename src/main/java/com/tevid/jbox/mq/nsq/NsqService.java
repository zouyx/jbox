package com.tevid.jbox.mq.nsq;

import com.sproutsocial.nsq.Publisher;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

public class NsqService implements InitializingBean, ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ApplicationContext applicationContext;
    @Getter
    private Publisher publisher;

    @Setter
    private String nsqd;
    @Setter
    private String failoverNsqd;

    @Override
    public void afterPropertiesSet() throws Exception {
        startProducer();
        if(publisher==null){
            throw new IllegalStateException("not init any of nsqd");
        }

//        if (nsqlookupd != null) {
//            logger.info("find nsqlookupd address: {}", nsqlookupd);
//            startConsumer();
//            flag = true;
//        }
    }

    private void startProducer() {
        if(StringUtils.isEmpty(nsqd)){
            nsqd="127.0.0.1";
        }
        logger.info("链接nsqd:%s,备份nsqd:%s",nsqd,failoverNsqd);
        publisher = new Publisher(nsqd,failoverNsqd);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
