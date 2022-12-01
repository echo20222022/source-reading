package com.cloud.elasticjob.task;

import com.alibaba.druid.pool.DruidDataSource;
import com.cloud.elasticjob.MySimpleJob;
import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.api.JobExtraConfiguration;
import org.apache.shardingsphere.elasticjob.http.props.HttpJobProperties;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.OneOffJobBootstrap;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.lite.internal.snapshot.SnapshotService;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.shardingsphere.elasticjob.script.props.ScriptJobProperties;
import org.apache.shardingsphere.elasticjob.tracing.api.TracingConfiguration;
import org.springframework.boot.autoconfigure.http.HttpProperties;

public class JobScheduleApp {

    public static class Lancer1 {
        public static void main(String[] args) {
            new ScheduleJobBootstrap(
                    coordinatorRegistryCenter(),  //配置注册中心
                    new ElasticSimpleJob(),            //初始化作业
                    jobConfiguration())           //初始化作业配置
                    //启动任务
                    .schedule();
        }
    }

    public static class Lancer2 {
        public static void main(String[] args) {
             new ScheduleJobBootstrap(
                    coordinatorRegistryCenter(),  //配置注册中心
                    new ElasticSimpleJob(),            //初始化作业
                    jobConfiguration())           //初始化作业配置
                    //启动任务
                    .schedule();
        }
    }

    public static class Lancer3 {
        public static void main(String[] args) {
            new ScheduleJobBootstrap(
                    coordinatorRegistryCenter(),  //配置注册中心
                    new ElasticSimpleJob(),            //初始化作业
                    jobConfiguration())           //初始化作业配置
                    //启动任务
                    .schedule();
        }
    }

    public static class Lancer4 {
        public static void main(String[] args) {
            CoordinatorRegistryCenter registryCenter = new ZookeeperRegistryCenter(
                    new ZookeeperConfiguration("localhost:2181", "script-job")
            );
            registryCenter.init();
            new ScheduleJobBootstrap(registryCenter,
                    "SCRIPT", JobConfiguration.newBuilder("scriptjob",1)
                    .setProperty(ScriptJobProperties.SCRIPT_KEY, "sh echo hello")
                    .cron("0/5 * * * * ?")
                    .build()).schedule();
        }
    }


    //ERROR
    public static class Lancer5 {

        public static void main(String[] args) {

            CoordinatorRegistryCenter registryCenter = new ZookeeperRegistryCenter(
                    new ZookeeperConfiguration("localhost:2181", "http-job")
            );
            registryCenter.init();
            //配置任务状态导出端口
            //SnapshotService snapshotService = new SnapshotService(registryCenter, 11000);
            //snapshotService.listen();
            new ScheduleJobBootstrap(registryCenter, "HTTP",
                    JobConfiguration.newBuilder("httpjob", 1)
                    .cron("0/5 * * * * ?")
                    .setProperty(HttpJobProperties.URI_KEY, "http://localhost:8084/api/user/addUser")
                    .setProperty(HttpJobProperties.METHOD_KEY, "POST")
                    .setProperty(HttpJobProperties.DATA_KEY, "key=value")
                    .shardingItemParameters("0=Guangzhou")
                     //以日志的形式记录错误
                    //.jobErrorHandlerType("LOG")
                    //.jobErrorHandlerType("THROW")
                    .jobErrorHandlerType("IGNORE")
                    .build()
            ).schedule();
        }
    }




    //数据流
    public static class Lancer6 {
        public static void main(String[] args) {
            CoordinatorRegistryCenter registryCenter = new ZookeeperRegistryCenter(
                    new ZookeeperConfiguration("localhost:2181", "workflow-job")
            );
            registryCenter.init();
            new ScheduleJobBootstrap(
                    registryCenter,  //配置注册中心
                    new ElasticDataFlowJob(),            //初始化作业
                    jobConfiguration())           //初始化作业配置
                    //启动任务
                    .schedule();
        }
    }

    public static class Lancer7 {
        public static void main(String[] args) {
            CoordinatorRegistryCenter registryCenter = new ZookeeperRegistryCenter(
                    new ZookeeperConfiguration("localhost:2181", "oneoff-job")
            );
            registryCenter.init();
            OneOffJobBootstrap jobBootstrap = new OneOffJobBootstrap(registryCenter, new ElasticSimpleJob(),
                    JobConfiguration.newBuilder("oneoff-job", 1).build());
            jobBootstrap.execute();
        }
    }

    //Tracing
    public static class Lancer8 {
        public static void main(String[] args) {
            CoordinatorRegistryCenter registryCenter = new ZookeeperRegistryCenter(
                    new ZookeeperConfiguration("localhost:2181", "tracing-job")
            );
            registryCenter.init();

            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUrl("jdbc:mysql://10.211.55.7:3306/users");
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUsername("root");
            dataSource.setPassword("Gz2021..");

            TracingConfiguration tracingConfiguration = new TracingConfiguration("RDB", dataSource);

            new ScheduleJobBootstrap(registryCenter, "HTTP",
                    JobConfiguration.newBuilder("httpjob", 1)
                            .cron("0/5 * * * * ?")
                            .setProperty(HttpJobProperties.URI_KEY, "http://localhost:8084/api/user/addUser")
                            .setProperty(HttpJobProperties.METHOD_KEY, "POST")
                            .setProperty(HttpJobProperties.DATA_KEY, "key=value")
                            .shardingItemParameters("0=Guangzhou")
                            //AVG_ALLOCATION、ODEVITY、ROUND_ROBIN
                            .jobShardingStrategyType("ODEVITY")
                            //CPU、SINGLE_THREAD
                            .jobExecutorServiceHandlerType("CPU")
                            .addExtraConfigurations(tracingConfiguration)
                            .build()
            ).schedule();
        }
    }


    //配置注册中心
    private static CoordinatorRegistryCenter coordinatorRegistryCenter() {
        CoordinatorRegistryCenter registryCenter = new ZookeeperRegistryCenter(
                new ZookeeperConfiguration("localhost:2181", "elastic-simple-job")
        );
        registryCenter.init();
        return registryCenter;
    }

    //配置作业
    private static JobConfiguration jobConfiguration() {
        //JobConfiguration
        //指定分片数 和 调度频率
        return JobConfiguration.newBuilder("ElasticSimpleJob", 3)
                //.setProperty("script.command.line", "echo sharding execution context is $*")
                .cron("0/5 * * * * ?").build();
    }


}
