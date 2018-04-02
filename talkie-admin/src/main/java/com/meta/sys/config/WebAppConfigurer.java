package com.meta.sys.config;

import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.sys.interceptor.InterceptorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * ybh 
 */
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {
    /**
     * 自定义拦截器
     * @return
     */
    @Bean
    InterceptorHandler getInterceptorHandler(){
        return new InterceptorHandler();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        ArrayList<String> commonPathPatterns = getExcludeCommonPathPatterns();
        registry.addInterceptor(getInterceptorHandler()).addPathPatterns("/**").excludePathPatterns(commonPathPatterns.toArray(new String[]{}));
            super.addInterceptors(registry);
    }

    /**
     * 放行路径
     * @return
     */
    private ArrayList<String> getExcludeCommonPathPatterns() {
        ArrayList<String> list = new ArrayList<>();
        String[] urls = {
                 VersionNumbers.SERVICE_VERSIONS_ADMIN+ "/"+ServiceUrls.FileData.AUDIO_DOWN,
                VersionNumbers.SERVICE_VERSIONS_ADMIN+ "/"+ServiceUrls.User.LOGIN_BY_ACCOUNT
        };
        Collections.addAll(list, urls);
        return list;
    }

}
