package com.dubbo.test.spi.java;

public class HelloService1 implements IHelloService {
    @Override
    public String hello(String name) {
        return "HelloService1:" + name;
    }
}
