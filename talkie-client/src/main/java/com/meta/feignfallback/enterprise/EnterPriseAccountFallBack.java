package com.meta.feignfallback.enterprise;

import com.meta.Result;
import com.meta.feignclient.enterprise.EnterPriseAccountClient;
import com.meta.model.enterprise.MEnterpriseAccount;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午11:36
 *
 * @version 1.0
 **/
@Component
public class EnterPriseAccountFallBack implements FallbackFactory<EnterPriseAccountClient> {
    @Override
    public EnterPriseAccountClient create(Throwable cause) {
        return new EnterPriseAccountClient(){
            @Override
            public Result<MEnterpriseAccount> create(MEnterpriseAccount mEnterpriseAccount) {
                return null;
            }

            @Override
            public Result<MEnterpriseAccount> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result modifyPassword(Long id, String password) {
                return null;
            }

            @Override
            public Result modifyStatusById(Long id, String status) {
                return null;
            }

            @Override
            public Result delete(Long id) {
                return null;
            }
        };
    }
}
