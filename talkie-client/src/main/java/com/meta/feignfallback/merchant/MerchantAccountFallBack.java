package com.meta.feignfallback.merchant;

import com.meta.Result;
import com.meta.feignclient.merchant.MerchantAccountClient;
import com.meta.model.merchant.MMerchantAccount;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午10:17
 *
 * @version 1.0
 **/
@Component
public class MerchantAccountFallBack implements FallbackFactory<MerchantAccountClient>{
    @Override
    public MerchantAccountClient create(Throwable cause) {
        return new MerchantAccountClient(){
            @Override
            public Result delete(Long id) {
                return null;
            }

            @Override
            public Result<MMerchantAccount> create(MMerchantAccount mMerchantAccount) {
                return null;
            }

            @Override
            public Result modifyStatusById(Long id, String status) {
                return null;
            }

            @Override
            public Result modifyPassword(Long id, String password) {
                return null;
            }

            @Override
            public Result<MMerchantAccount> search(String filters, String sorts, int size, int page) {
                return null;
            }
        };
    }
}
