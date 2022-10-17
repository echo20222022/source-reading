package com.dubbo.test.spi.dubbo;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SPITest {

    public static void main(String[] args) {
        /*IHelloService helloService = ExtensionLoader.getExtensionLoader(IHelloService.class).getDefaultExtension();
        URL url1 = new URL("","",0);
        System.out.println(helloService.hello(url1,"lisi"));

        System.out.println();
        IHelloService helloService2 = ExtensionLoader.getExtensionLoader(IHelloService.class).getExtension("helloService2");
        URL url2 = new URL("","",0);
        System.out.println(helloService2.hello(url2,"wangwu"));

        System.out.println();
        IHelloService helloService3 = ExtensionLoader.getExtensionLoader(IHelloService.class).getExtension("myHelloService");
        Map<String, String> p3 = new HashMap<>();
        p3.put("helloService","helloService2");
        URL url3 = new URL("","",0, p3);
        System.out.println(helloService3.hello(url3, "zhaoliu"));*/

        //通过active 可以同时激活多个实现类
        Map<String, String> m = new HashMap<>();
        m.put("activate1", "activeService1");
        m.put("activate2", "activeService2");
        URL url6 = new URL("","",0, m);

        List<IActivateService> activateService = ExtensionLoader.getExtensionLoader(IActivateService.class).getActivateExtension(url6, "activate1", "test");
        for (IActivateService a : activateService) {
            System.out.println("---");
            a.active();
        }
    }
}
