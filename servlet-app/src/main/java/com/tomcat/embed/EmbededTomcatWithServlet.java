package com.tomcat.embed;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 嵌入式运行Servlet
 * */
public class EmbededTomcatWithServlet {

    public static void main(String[] args) throws Exception {
        
        Tomcat tomcat = new Tomcat();

        HttpServlet httpServlet = new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                System.out.println("do get ....");
                resp.getWriter().write("do get response");
            }

            @Override
            protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                System.out.println("do post ....");
                resp.getWriter().write("do post reponse...");
            }
        };
        //定义web应用
        Context context = tomcat.addContext("/sample", null);
        //注册servlet
        tomcat.addServlet(context, "servlet", httpServlet);
        context.addServletMappingDecoded("/servlet", "servlet");

        tomcat.init();
        tomcat.start();
        tomcat.getServer().await();
    }
}
