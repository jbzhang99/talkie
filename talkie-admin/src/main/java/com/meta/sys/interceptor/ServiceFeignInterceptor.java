package com.meta.sys.interceptor;

import com.meta.request.RequestUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * 做的比较简单单纯的把token发送过去
 */
public class ServiceFeignInterceptor implements RequestInterceptor {
    private Logger logger = LoggerFactory.getLogger(ServiceFeignInterceptor.class);


    public ServiceFeignInterceptor() {
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {

        if ( RequestUtil.getRequest().getHeader("Authorization") != null){
            requestTemplate.header("language",RequestUtil.getRequest().getHeader("language"));
            requestTemplate.header("Authorization",RequestUtil.getRequest().getHeader("Authorization"));
        }
    }

}