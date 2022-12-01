package com.cloud.user.controller;

import com.cloud.api.UserApi;
import com.cloud.api.bean.resp.Response;
import com.cloud.api.bean.resp.UserResponseData;
import com.cloud.user.bean.User;
import com.cloud.user.dao.UserMapper;
import com.cloud.user.dao.read.ReadUserMapper;
import com.cloud.user.dao.write.WriteUserMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping(UserApi.PREFIX)
public class UserController implements UserApi {

    @Autowired
    private UserMapper userMapper;
    //@Autowired
    private ReadUserMapper readUserMapper;
    //@Autowired
    private WriteUserMapper writeUserMapper;

    @Override
    public Response<UserResponseData> getUserName(Long id) {
        UserResponseData data = null;
        if (id == 1L) {
            data = UserResponseData.builder().id(id).name("echo").age(30).build();
        }
        Response<UserResponseData> response = new Response<>();
        response.setCode(200);
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    @Override
    @Transactional
    public Response<Boolean> addUser() {
        User user = new User();
        user.setName("zhangsan" + UUID.randomUUID().toString().substring(0, 6));
        user.setAge(new Random().nextInt(10));
        user.setSex("male");
        int res = userMapper.insert(user);
        //int s = 10 / 0;
        Response<Boolean> response = new Response<>();
        response.setCode(200);
        response.setSuccess(true);
        response.setData( res > 0);
        return response;
    }

    @Override
    public Response<Map<String, Object>> getUser() {
        Map<String, Object> user = userMapper.selectById(1);
        Response<Map<String, Object>> response = new Response<>();
        response.setCode(200);
        response.setSuccess(true);
        response.setData(user);
        return response;
    }


    @Override
    public Response<Map<String, Object>> getReadUser() {
        Map<String, Object> data = readUserMapper.getUserById(1).get(0);
        Response<Map<String, Object>> response = new Response<>();
        response.setCode(200);
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    @Override
    public Response<Map<String, Object>> getWriteUser() {
        Map<String, Object> data = writeUserMapper.getUserById(1).get(0);
        Response<Map<String, Object>> response = new Response<>();
        response.setCode(200);
        response.setSuccess(true);
        response.setData(data);
        return response;
    }
}
