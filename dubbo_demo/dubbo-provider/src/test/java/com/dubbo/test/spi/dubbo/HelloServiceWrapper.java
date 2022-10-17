package com.dubbo.test.spi.dubbo;

import org.apache.dubbo.common.URL;

public class HelloServiceWrapper implements IHelloService{

    //AOP
    private IHelloService helloService;
    public HelloServiceWrapper(IHelloService helloService) {
        this.helloService = helloService;
    }
    @Override
    public String hello(URL url, String name) {
        System.out.println("before...");
        String result = helloService.hello(url, name);
        System.out.println("after...");
        return result;
    }
}
