package com.meta.feignfallback.user;

import com.meta.Result;
import com.meta.feignclient.user.UserEventClient;
import com.meta.model.user.MUserEvent;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午9:08
 *
 * @version 1.0
 **/
@Component
public class UserEventFallBack implements FallbackFactory<UserEventClient> {
    private static final Logger log = LoggerFactory.getLogger(UserEventFallBack.class);
    @Override
    public UserEventClient create(Throwable cause) {
        return new UserEventClient(){
            @Override
            public Result<MUserEvent> search(String filters, String sorts, int size, int page) {
                return null;
            }
        };
    }
}
