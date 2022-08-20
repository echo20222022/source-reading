package com.cloud.fd.exception;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.adapter.spring.webflux.exception.SentinelBlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FdUrlBlockHandler implements UrlBlockHandler {
    @Override
    public void blocked(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws IOException {

        MyResponse errorResponse = new MyResponse();
        if (e instanceof FlowException) {
            errorResponse.setMsg("被限流了");
            errorResponse.setStatus(1);
        } else if (e instanceof DegradeException) {
            errorResponse.setMsg("服务降级了");
            errorResponse.setStatus(2);
        } else if (e instanceof ParamFlowException) {
            errorResponse.setMsg("被限流了");
            errorResponse.setStatus(3);
        } else if (e instanceof SystemBlockException) {
            errorResponse.setMsg("被系统保护了");
            errorResponse.setStatus(4);
        } else if (e instanceof AuthorityException) {
            errorResponse.setMsg("被授权了");
            errorResponse.setStatus(5);
        }

        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().print(JSON.toJSONString(errorResponse));
    }

    public static class MyResponse{
        private String msg;
        private Integer status;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "MyResponse{" +
                    "msg='" + msg + '\'' +
                    ", status=" + status +
                    '}';
        }
    }
}
