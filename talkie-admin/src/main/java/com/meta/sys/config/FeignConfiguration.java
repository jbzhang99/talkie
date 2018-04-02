package com.meta.sys.config;


import com.meta.sys.interceptor.ServiceFeignInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 修改feign配置信息,主要是为了携带token认证
 */
@Configuration
public class FeignConfiguration {
    @Bean
    ServiceFeignInterceptor getClientTokenInterceptor(){
        return new ServiceFeignInterceptor();
    }
}
