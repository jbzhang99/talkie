package com.meta.feignfallback.user;

import com.meta.Result;
import com.meta.feignclient.user.UserGroupClient;
import com.meta.model.user.MUserGroup;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午9:29
 *
 * @version 1.0
 **/
@Component
public class UserGroupFallBack  implements FallbackFactory<UserGroupClient> {
    private static final Logger log = LoggerFactory.getLogger(UserGroupFallBack.class);
    @Override
    public UserGroupClient create(Throwable cause) {
        return new UserGroupClient(){
            @Override
            public Result<MUserGroup> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result<MUserGroup> create(MUserGroup mUserGroup) {
                return null;
            }

            @Override
            public Result delete(Long id) {
                return null;
            }

            @Override
            public Result<MUserGroup> batchCreat(MUserGroup mUserGroup) {
                return null;
            }

            @Override
            public Result deleteByUserIdAndGroupId(String userId, String groupId, String type) {
                return null;
            }
        };
    }
}
