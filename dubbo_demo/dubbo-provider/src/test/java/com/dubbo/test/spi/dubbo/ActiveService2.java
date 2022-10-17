package com.dubbo.test.spi.dubbo;

import org.apache.dubbo.common.extension.Activate;

@Activate(order = 2, group = "test")
public class ActiveService2 implements IActivateService {
    @Override
    public void active() {
        System.out.println("active2");
    }
}
