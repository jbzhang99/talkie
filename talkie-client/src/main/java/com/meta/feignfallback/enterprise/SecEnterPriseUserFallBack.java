package com.meta.feignfallback.enterprise;

import com.meta.Result;
import com.meta.feignclient.enterprise.SecEnterPriseUserClient;
import com.meta.model.enterprise.MSecEnterPriseUser;
import com.meta.model.user.MUser;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23下午1:47
 *
 * @version 1.0
 **/
@Component
public class SecEnterPriseUserFallBack implements FallbackFactory<SecEnterPriseUserClient> {
    @Override
    public SecEnterPriseUserClient create(Throwable cause) {
        return new SecEnterPriseUserClient(){
            @Override
            public Result<MSecEnterPriseUser> create(MSecEnterPriseUser mSecEnterPriseUser) {
                return null;
            }

            @Override
            public Result deleteById(Long id) {
                return null;
            }

            @Override
            public Result<MUser> findByUserIdAndParentId(Long userId, Long parentId) {
                return null;
            }

            @Override
            public Result<MSecEnterPriseUser> findByUserId(Long userId) {
                return null;
            }
        };
    }
}
