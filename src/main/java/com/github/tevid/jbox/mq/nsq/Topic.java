package com.github.tevid.jbox.mq.nsq;

public class Topic {
    public final String topic, channel;
    public final int nThreads;

    /**
     * @param nThreads 线程数
     */
    public Topic(String topic, String channel, int nThreads) {
        this.topic = topic;
        this.channel = channel;
        this.nThreads = nThreads;
    }
}
