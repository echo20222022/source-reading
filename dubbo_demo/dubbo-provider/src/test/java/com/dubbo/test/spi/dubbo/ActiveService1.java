package com.dubbo.test.spi.dubbo;

import org.apache.dubbo.common.extension.Activate;

@Activate(order = 1, group = "test")
public class ActiveService1 implements IActivateService {
    @Override
    public void active() {
        System.out.println("Active1");
    }
}
