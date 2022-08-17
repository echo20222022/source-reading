package com.tomcat.embed;

import org.apache.catalina.startup.Tomcat;

public class EmbededTomcatWithDocBase {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();

        tomcat.addWebapp("/servlet-app", "/Users/dongzhang/develop/workspace/echo20222022/open_src/apache-tomcat-9.0.65-src/home/webapps/servlet-app");

        tomcat.init();
        tomcat.start();
        tomcat.getServer().await();
    }
}
