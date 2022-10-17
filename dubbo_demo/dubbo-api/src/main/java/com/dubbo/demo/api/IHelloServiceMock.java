package com.dubbo.demo.api;

public class IHelloServiceMock implements IHelloService{
    @Override
    public String sayHello(String name) {
        return "hello, mock";
    }
}
