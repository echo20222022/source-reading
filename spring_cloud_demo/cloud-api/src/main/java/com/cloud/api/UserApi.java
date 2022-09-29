package com.cloud.api;

import com.cloud.api.bean.resp.Response;
import com.cloud.api.bean.resp.UserResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

public interface UserApi {

    String PREFIX = "/api/user";

    @GetMapping("/getNameById")
    Response<UserResponseData> getUserName(@RequestParam("id") Long id);

    class DefaultFallback implements UserApi {
        @Override
        public Response<UserResponseData> getUserName(Long id) {
            Response<UserResponseData> response = new Response<>();
            response.setCode(200);
            response.setSuccess(false);
            response.setDesc("Internal Exception");
            return response;
        }
    }
}
