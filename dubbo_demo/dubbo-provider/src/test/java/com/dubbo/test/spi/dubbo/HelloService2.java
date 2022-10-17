package com.dubbo.test.spi.dubbo;

import org.apache.dubbo.common.URL;

public class HelloService2 implements IHelloService{
    @Override
    public String hello(URL url, String name) {
        return "HelloService2:" + name;
    }
}
