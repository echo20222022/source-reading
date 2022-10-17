package com.mybatis.test;

import com.mybatis.test.domain.User;
import com.mybatis.test.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GetSqlSessionTest {

    private static Logger LOGGER = LoggerFactory.getLogger(GetSqlSessionTest.class);

    public static void main(String[] args) throws Exception {

        LOGGER.info("aaaa");

        String config = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(config);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        for (int i = 0;i < 5;i ++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SqlSession sqlSession = sqlSessionFactory.openSession();

                    //通过xml定义mapper
                    //List<Object> list = sqlSession.selectList("com.mybatis.test.mapper.UserMapper.selectUsers");
                    //System.out.println(list);

                    //通过接口定义mapper
                    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
                    System.out.println(userMapper.selectUser(1));
                    //System.out.println(userMapper.selectUser(1));

        /*List<User> users = sqlSession.selectList("com.mybatis.test.mapper.UserMapper2.selectUsers", 1);
        System.out.println(users);*/
                    sqlSession.close();
                }
            }).start();
        }


        //SqlSession sqlSession = sqlSessionFactory.openSession();

        //通过xml定义mapper
        //List<Object> list = sqlSession.selectList("com.mybatis.test.mapper.UserMapper.selectUsers");
        //System.out.println(list);

        //通过接口定义mapper
        //UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //System.out.println(userMapper.selectUser(1));
        //System.out.println(userMapper.selectUser(1));

        /*List<User> users = sqlSession.selectList("com.mybatis.test.mapper.UserMapper2.selectUsers", 1);
        System.out.println(users);*/
        //sqlSession.close();

        TimeUnit.SECONDS.sleep(5);
    }
}
