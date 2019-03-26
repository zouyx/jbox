package com.github.tevid.jbox.mq.nsq;

import org.springframework.stereotype.Service;

import static com.github.tevid.jbox.mq.nsq.CommonCache.CACHE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@Service
public class TestCallback implements Callback{

    @Override
    public Topic getTopic() {
        return new Topic("test","test_channel_java",2);
    }

    @Override
    public void messageDataHandler(byte[] data) {
        assertThat(CACHE.get(new String(data)),notNullValue());
        System.out.println("Received:" + new String(data));
    }
}
