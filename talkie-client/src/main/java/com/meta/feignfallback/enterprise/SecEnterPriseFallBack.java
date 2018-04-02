package com.meta.feignfallback.enterprise;

import com.meta.Result;
import com.meta.feignclient.enterprise.SecEnterPriseClient;
import com.meta.model.enterprise.MEnterprise;
import com.meta.model.group.MGroupUser;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23下午1:44
 *
 * @version 1.0
 **/
@Component
public class SecEnterPriseFallBack implements FallbackFactory<SecEnterPriseClient> {
    @Override
    public SecEnterPriseClient create(Throwable cause) {
        return new SecEnterPriseClient(){
            @Override
            public Result<MEnterprise> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result<MEnterprise> create(MEnterprise mEnterprise) {
                return null;
            }

            @Override
            public Result deleteById(Long id) {
                return null;
            }

            @Override
            public Result modifyPasswordById(Long id, String password) {
                return null;
            }

            @Override
            public Result<MEnterprise> findByAccountAndParentId(String account, Long parentId) {
                return null;
            }

            @Override
            public Result modifyStatusById(Long id, String status) {
                return null;
            }

            @Override
            public Integer countCompayByParentId(Long parentId, Integer isDel) {
                return null;
            }

            @Override
            public Result<MGroupUser> findWaitUserGroup(Long userId, Long groupId) {
                return null;
            }

            @Override
            public Result<MGroupUser> findAlreadyUserGroup(Long groupId) {
                return null;
            }

            @Override
            public Result<MEnterprise> searchNoPage(String filters) {
                return null;
            }

            @Override
            public Long countByParentIdAndMerchantLevel(Long parentId, String merchantLevel) {
                return null;
            }
        };
    }
}
