package com.cloud.user.controller;

import com.cloud.api.UserApi;
import com.cloud.api.bean.resp.Response;
import com.cloud.api.bean.resp.UserResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserApi.PREFIX)
public class UserController implements UserApi {


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
}
