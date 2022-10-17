package com.cloud.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
//@MapperScan(basePackages = "com.cloud.user.dao.read", sqlSessionFactoryRef="readSqlSessionFactoryBean")
//@MapperScan(basePackages = "com.cloud.user.dao.write", sqlSessionFactoryRef="writeSqlSessionFactoryBean")
@MapperScan("com.cloud.user.dao")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class);
    }
}
