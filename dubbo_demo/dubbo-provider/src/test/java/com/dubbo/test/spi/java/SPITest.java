package com.dubbo.test.spi.java;

import java.util.ServiceLoader;

public class SPITest {

    public static void main(String[] args) {
        ServiceLoader<IHelloService> helloServices = ServiceLoader.load(IHelloService.class);
        System.out.println("))))");
        for (IHelloService helloService : helloServices) {
            System.out.println("---");
            System.out.println(helloService.hello("zhangsan"));
        }
    }
}
