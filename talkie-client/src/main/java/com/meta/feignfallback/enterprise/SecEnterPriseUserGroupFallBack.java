package com.meta.feignfallback.enterprise;

import com.meta.Result;
import com.meta.feignclient.enterprise.SecEnterPriseUserGroupClient;
import com.meta.model.enterprise.MSecEnterPriseUserGroup;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23下午1:48
 *
 * @version 1.0
 **/
@Component
public class SecEnterPriseUserGroupFallBack implements FallbackFactory<SecEnterPriseUserGroupClient> {
    @Override
    public SecEnterPriseUserGroupClient create(Throwable cause) {
        return  new SecEnterPriseUserGroupClient(){
            @Override
            public Result<MSecEnterPriseUserGroup> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result<MSecEnterPriseUserGroup> create(MSecEnterPriseUserGroup mSecEnterPriseUserGroup) {
                return null;
            }

            @Override
            public Result delete(Long id) {
                return null;
            }

            @Override
            public Result<MSecEnterPriseUserGroup> batchCreat(MSecEnterPriseUserGroup mSecEnterPriseUserGroup) {
                return null;
            }

            @Override
            public Result deleteByUserIdAndGroupId(String userId, String groupId, String type) {
                return null;
            }
        };
    }
}
