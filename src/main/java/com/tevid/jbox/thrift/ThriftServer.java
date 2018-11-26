package com.tevid.jbox.thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ThriftServer {

    int port;

    private ApplicationContext applicationContext;

    private TServer server;

    public ThriftServer(ApplicationContext applicationContext) throws TTransportException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.applicationContext=applicationContext;
        TProcessorFactory tProcessorFactory = new TProcessorFactory(this.registerProcessor());
        // 2.创建TserverTransport
        TServerTransport serverTransport = new TServerSocket(port);
        //3.创建TProtocol
        TJSONProtocol.Factory factory = new TJSONProtocol.Factory();

        TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(serverTransport);
        tArgs.processorFactory(tProcessorFactory);
        tArgs.protocolFactory(factory);
        //4.创建Tserver,传入需要的参数,server将以上内容集成在一起
        this.server=new TThreadPoolServer(tArgs);
    }

    public void start(){
        System.out.println("服务端开启....");
        //5.启动server
        this.server.serve();
    }

    /**
     * 创建TProcessor
     * @return
     */
    private TProcessor registerProcessor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TMultiplexedProcessor processor = new TMultiplexedProcessor();
        String[] beanNames = applicationContext.getBeanNamesForType(Object.class);
        for (String beanName : beanNames) {
            Thrift mapping = applicationContext.findAnnotationOnBean(beanName, Thrift.class);
            if (mapping==null) {
                continue;
            }

            Object bean = applicationContext.getBean(beanName);
            //当前类
            Class clazz=bean.getClass();
            //当前类的父接口
            Class clazzInterface = clazz.getInterfaces()[0];
            //当前类的父接口的父类的所有内部类
            Class innerClazzes[] = clazzInterface.getDeclaringClass().getDeclaredClasses();

            for (Class innerClazz : innerClazzes) {
                if(innerClazz.getName().lastIndexOf("$Processor")==-1){
                    continue;
                }
                Constructor constructor = innerClazz.getDeclaredConstructor(clazzInterface);
                TProcessor tProcessor = (TProcessor) constructor.newInstance(bean);
                processor.registerProcessor(mapping.serviceName(), tProcessor);
            }
        }

        return processor;
    }

    public TServer getServer() {
        return server;
    }
}
