package com.dubbo.demo.provider.service;

import com.dubbo.demo.api.IHelloService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcException;

@DubboService(group = "demo", version = "1.0")
public class HelloServiceImpl implements IHelloService {
    @Override
    public String sayHello(String name) {
       /* try {
            int i = 10 / 0;
        }catch (Exception ex) {
            throw new RpcException("man ex");
        }*/
        return "Hello," + name;
    }
}
