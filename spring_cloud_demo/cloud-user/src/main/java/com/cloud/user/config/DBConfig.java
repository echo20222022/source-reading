package com.cloud.user.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.cloud.user.ds.CloudDynamicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DBConfig {

   /* @Bean
    public DataSource readDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://10.211.55.7:3306/db_read");
        dataSource.setUsername("root");
        dataSource.setPassword("Gz2021..");
        return dataSource;
    }

    @Bean
    public DataSource writeDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://10.211.55.7:3306/db_write");
        dataSource.setUsername("root");
        dataSource.setPassword("Gz2021..");
        return dataSource;
    }

    @Bean
    @Qualifier("readSqlSessionFactoryBean")
    public SqlSessionFactoryBean readSqlSessionFactoryBean() throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(readDataSource());
        sqlSessionFactoryBean.setMapperLocations(new FileSystemResource("/Users/dongzhang/develop/workspace/echo20222022/open_src/spring_cloud_demo/cloud-user/src/main/resources/mappers/read/UserMapper.xml"));
        return sqlSessionFactoryBean;
    }

    @Bean
    @Qualifier("writeSqlSessionFactoryBean")
    public SqlSessionFactoryBean writeSqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(writeDataSource());
        sqlSessionFactoryBean.setMapperLocations(new FileSystemResource("/Users/dongzhang/develop/workspace/echo20222022/open_src/spring_cloud_demo/cloud-user/src/main/resources/mappers/write/UserMapper.xml"));
        return sqlSessionFactoryBean;
    }*/

    //@Bean
    public DataSource readDataSource1() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://10.211.55.7:3306/db_read1");
        dataSource.setUsername("root");
        dataSource.setPassword("Gz2021..");
        return dataSource;
    }

    //@Bean
    public DataSource readDataSource2() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://10.211.55.7:3306/db_read2");
        dataSource.setUsername("root");
        dataSource.setPassword("Gz2021..");
        return dataSource;
    }

    //@Bean
    public DataSource writeDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://10.211.55.7:3306/db_write");
        dataSource.setUsername("root");
        dataSource.setPassword("Gz2021..");
        return dataSource;
    }

    @Bean
    public DataSource dataSource() {
        CloudDynamicDataSource dataSource = new CloudDynamicDataSource();
        dataSource.setWriteDataSource(writeDataSource());
        List<DataSource> readDataSourceList = new ArrayList<>();
        readDataSourceList.add(readDataSource1());
        readDataSourceList.add(readDataSource2());
        dataSource.setReadDataSource(readDataSourceList);
        //轮询策略
        dataSource.setReadDataSourceRollPattern(1);
        return dataSource;
    }

    /*@Bean
    @Qualifier("sqlSessionFactoryBean")
    public SqlSessionFactoryBean writeSqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setMapperLocations(new FileSystemResource("/Users/dongzhang/develop/workspace/echo20222022/open_src/spring_cloud_demo/cloud-user/src/main/resources/mappers/UserMapper.xml"));
        return sqlSessionFactoryBean;
    }*/
}
