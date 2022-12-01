package com.cloud.elasticjob;

import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.OneOffJobBootstrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class EJApp {

    /*@Autowired
    @Qualifier("springBootDataFlowJob")*/
    //@Resource(name = "springBootDataFlowJob")
    //private OneOffJobBootstrap springBootDataFlowJob;

    @GetMapping("/hello")
    public String hello() {
        //springBootDataFlowJob.execute();
        return "ok";
    }

   /* @Resource(name="httpElasticJobBean")
    private OneOffJobBootstrap httpElasticJobBean;*/

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(EJApp.class);
    }


    public static class ScheduleTest {
        public static void main(String[] args) throws Exception {
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println("to work...");
                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();*/

            /*Timer timer = new Timer("TimeJob", false);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Timer Task schedule running..");
                }
            },0, 1000);

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Timer Task scheduleAtFixedRate running..");
                    int i  = 10 / 0;
                }
            }, 0, 2000);*/

            //timer.purge();
            //timer.cancel();

            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    System.out.println("ScheduledExecutorService running..");
                }
            },0, 10, TimeUnit.SECONDS);
        }
    }
}
