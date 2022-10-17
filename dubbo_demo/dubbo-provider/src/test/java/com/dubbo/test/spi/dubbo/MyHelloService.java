package com.dubbo.test.spi.dubbo;

import org.apache.dubbo.common.URL;

public class MyHelloService implements IHelloService{

    //IOC
    private IHelloService helloService;
    public void setHelloService(IHelloService helloService) {
        this.helloService = helloService;
    }
    @Override
    public String hello(URL url, String name) {
        System.out.println("my hello service ");
        return helloService.hello(url, name);
    }
}
