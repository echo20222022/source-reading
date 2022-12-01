package com.cloud.user;

import com.cloud.user.bean.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@SpringCloudApplication
//@MapperScan(basePackages = "com.cloud.user.dao.read", sqlSessionFactoryRef="readSqlSessionFactoryBean")
//@MapperScan(basePackages = "com.cloud.user.dao.write", sqlSessionFactoryRef="writeSqlSessionFactoryBean")
@SpringBootApplication
@MapperScan("com.cloud.user.dao")
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class UserApplication {
    //@Autowired
    //private User user;
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class);
    }
}
