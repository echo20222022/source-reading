package com.dubbo.test.spi.dubbo;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

//指定默认实现
@SPI("helloService1")
public interface IHelloService {

    //根据key - helloService来适配指定的实现
    @Adaptive("helloService")
    String hello(URL url, String name);
}
