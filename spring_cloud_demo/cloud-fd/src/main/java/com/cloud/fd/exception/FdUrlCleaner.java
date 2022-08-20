package com.cloud.fd.exception;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlCleaner;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class FdUrlCleaner implements UrlCleaner {
    @Override
    public String clean(String s) {
        if (StringUtils.isEmpty(s)) {
            return s;
        }

        if (s.startsWith("/pathValueUrl/")) {
            return "/pathValueUrl/*";
        }
        return s;
    }
}
