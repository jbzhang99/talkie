package com.meta.feignfallback.user;

import com.meta.Result;
import com.meta.feignclient.user.UserFriendClient;
import com.meta.model.user.MUserFriend;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午9:12
 *
 * @version 1.0
 **/
@Component
public class UserFriendFallBack implements FallbackFactory<UserFriendClient> {
    private static final Logger log = LoggerFactory.getLogger(UserFriendFallBack.class);
    @Override
    public UserFriendClient create(Throwable cause) {
        return new UserFriendClient(){
            @Override
            public Result<MUserFriend> batchAddOrDelFriend(MUserFriend mUserFriend) {
                return null;
            }
        };
    }
}
