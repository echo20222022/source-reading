package com.cloud.api;

import com.cloud.api.bean.resp.Response;
import com.cloud.api.bean.resp.RobotResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface RobotApi {

    String PREFIX = "/api/robot";

    @GetMapping("/getMessage")
    Response<RobotResponseData> getRobotMessage(@RequestParam("key") String key);

    class DefaultFallback implements RobotApi {

        @Override
        public Response<RobotResponseData> getRobotMessage(String key) {
            Response<RobotResponseData> response = new Response<>();
            response.setCode(200);
            response.setSuccess(false);
            response.setDesc("Internal Exception");
            return response;
        }
    }
}
