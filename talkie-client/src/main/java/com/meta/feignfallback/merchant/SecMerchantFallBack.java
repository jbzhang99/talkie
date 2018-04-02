package com.meta.feignfallback.merchant;

import com.meta.Result;
import com.meta.feignclient.merchant.SecMerchantClient;
import com.meta.model.merchant.MMerchant;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午10:22
 *
 * @version 1.0
 **/
@Component
public class SecMerchantFallBack  implements FallbackFactory<SecMerchantClient>{
    private static final Logger log = LoggerFactory.getLogger(SecMerchantFallBack.class);
    @Override
    public SecMerchantClient create(Throwable cause) {
        return new SecMerchantClient(){
            @Override
            public Result<MMerchant> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result<MMerchant> create(MMerchant mMerchant) {
                return null;
            }

            @Override
            public Result deleteById(Long id, String merchantLevel, Long currentUserId, String account, String name) {
                return null;
            }

            @Override
            public Result<MMerchant> get(Long id) {
                return null;
            }

            @Override
            public Result modifyPasswordById(Long id, String password) {
                return null;
            }

            @Override
            public Result<MMerchant> findByAccountAndParentId(String account, Long parentId) {
                return null;
            }

            @Override
            public Result modifyStatusById(Long id, String status) {
                return null;
            }

            @Override
            public Result delete(Long id, String merchantLevel, Long currentUserId) {
                return null;
            }

            @Override
            public Integer countCompayByParentId(Long parentId, Integer isDel) {
                return null;
            }
        };
    }
}
