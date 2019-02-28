package com.github.tevid.jbox.mq.nsq;

public interface Callback {

    /**
     * 获取topic信息
     * @return
     */
    Topic getTopic();

    void messageDataHandler(byte[] data);
}
