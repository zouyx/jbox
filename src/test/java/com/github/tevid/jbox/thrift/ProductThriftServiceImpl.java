package com.github.tevid.jbox.thrift;

import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

@Thrift(serviceName="ProductService")
@Component
public class ProductThriftServiceImpl implements ProductThriftService.Iface{
    @Override
    public int sayInt(int param) throws TException {
        System.out.println("2222");
        return param;
    }

    @Override
    public String sayString(String param) throws TException {
        return param;
    }

    @Override
    public boolean sayBoolean(boolean param) throws TException {
        return param;
    }
}
