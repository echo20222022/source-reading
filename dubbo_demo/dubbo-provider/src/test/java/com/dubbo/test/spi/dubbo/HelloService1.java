package com.dubbo.test.spi.dubbo;

import org.apache.dubbo.common.URL;

public class HelloService1 implements IHelloService{
    @Override
    public String hello(URL url, String name) {
        return "HelloService1:" + name;
    }
}
