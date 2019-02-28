package com.tevid.jbox.thrift;

import org.apache.thrift.server.TServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class ThriftServerTest {

    @Autowired
    protected ApplicationContext ctx;

    @Test
    public void init() throws Exception {
        ThriftServer thriftServer=new ThriftServer(ctx);
        TServer server = thriftServer.getServer();
        assertThat(server,notNullValue());

//        thriftServer.start();
    }
}