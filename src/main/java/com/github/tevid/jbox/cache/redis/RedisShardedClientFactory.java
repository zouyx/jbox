package com.github.tevid.jbox.cache.redis;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class RedisShardedClientFactory implements FactoryBean<ShardingRedisClient>, InitializingBean, DisposableBean {

    private ShardedJedisPool shardedJedisPool;

    private String server;

    public void setServer(String server) {
        this.server = server;
    }

    private int maxTotal = 1000;
    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    private int maxWait = 1000;
    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    private String password;
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<JedisShardInfo> shards = new ArrayList<>();
        String[] servers = server.split(";|,");
        for (String str : servers) {
            String[] ap = str.split(":");
            JedisShardInfo si = new JedisShardInfo(ap[0], Integer.valueOf(ap[1]));

            if(!StringUtils.isEmpty(password)){
                si.setPassword(password);
            }
            shards.add(si);
        }

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(100);
        config.setMinIdle(10);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        shardedJedisPool = new ShardedJedisPool(config, shards);
    }

    @Override
    public void destroy() {
        shardedJedisPool.destroy();
    }

    public ShardingRedisClient getJedis(){
        ShardingRedisClient service = (ShardingRedisClient)creatProxyInstance();
        return service;
    }

    @Override
    public ShardingRedisClient getObject() throws Exception {
        return getJedis();
    }

    @Override
    public Class<?> getObjectType() {
        return ShardingRedisClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public  Object creatProxyInstance() {
        return Proxy.newProxyInstance(ShardingRedisClient.class.getClassLoader(), new Class[]{ShardingRedisClient.class} , new ProxyFactory(shardedJedisPool));
    }

    class ProxyFactory  implements InvocationHandler {

        private ShardedJedisPool shardedJedisPool;

        public ProxyFactory(ShardedJedisPool shardedJedisPool){
            this.shardedJedisPool = shardedJedisPool;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            if ("toString".equals(method.getName())){
                return this.toString();
            }

            Object result = null;

            ShardedJedis shardedJedis = shardedJedisPool.getResource();
            try{

                result = method.invoke(shardedJedis, args);
            }catch(Exception e){
                throw e ;
            }finally {
                shardedJedis.close();
            }
            return result;
        }
    }

}