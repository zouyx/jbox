package com.tevid.jbox.cache.redis;

import com.tevid.jbox.cache.redis.ShardingRedisClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class ShardingRedisClientTest {

    @Autowired
    ShardingRedisClient shardingRedisClient;

    @Test
    public void testRedis(){
        String abc = shardingRedisClient.set("abc", "123");
        System.out.println(abc);
        assertThat(abc,equalTo("OK"));

    }
}