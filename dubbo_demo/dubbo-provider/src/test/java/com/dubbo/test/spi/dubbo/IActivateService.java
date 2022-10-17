package com.dubbo.test.spi.dubbo;

import org.apache.dubbo.common.extension.SPI;

@SPI
public interface IActivateService {
    void active();
}
