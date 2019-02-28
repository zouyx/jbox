package com.github.tevid.jbox.mq.nsq;

import org.springframework.stereotype.Service;

@Service
public class TestCallback implements Callback{

    @Override
    public Topic getTopic() {
        return new Topic("test","test_channel_java",2);
    }

    @Override
    public void messageDataHandler(byte[] data) {
        System.out.println("Received:" + new String(data));
    }
}
