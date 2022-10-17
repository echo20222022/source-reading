package com.dubbo.test.spi.java;

public class HelloService2 implements IHelloService{
    @Override
    public String hello(String name) {
        return "HelloService2:" + name;
    }
}
