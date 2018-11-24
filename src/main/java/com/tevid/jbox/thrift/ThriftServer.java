package com.tevid.jbox.thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

public class ThriftServer {

    int port;

    public void start() throws TTransportException {
        System.out.println("服务端开启....");
        //1.创建TProcessor
        TMultiplexedProcessor processor = new TMultiplexedProcessor();
//        processor.registerProcessor("ProductService", new ProductThriftService.Processor<>(new ProductThriftServiceImpl()));
        TProcessorFactory tProcessorFactory = new TProcessorFactory(processor);
        // 2.创建TserverTransport
        TServerTransport serverTransport = new TServerSocket(port);
        //3.创建TProtocol
        TJSONProtocol.Factory factory = new TJSONProtocol.Factory();

        TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(serverTransport);
        tArgs.processorFactory(tProcessorFactory);
        tArgs.protocolFactory(factory);
        //4.创建Tserver,传入需要的参数,server将以上内容集成在一起
        TServer server = new TThreadPoolServer(tArgs);
        //5.启动server
        server.serve();
    }
}
