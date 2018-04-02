package com.meta.feignfallback.enterprise;

import com.meta.Result;
import com.meta.feignclient.enterprise.EnterpriseClient;
import com.meta.model.enterprise.MEnterprise;
import com.meta.model.totalinfo.MEnterPriseTotalCountInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午11:32
 *
 * @version 1.0
 **/
@Component
public class EnterPriseFallBack implements FallbackFactory<EnterpriseClient> {
    @Override
    public EnterpriseClient create(Throwable cause) {
        return new EnterpriseClient(){
            @Override
            public Result modifyPassword(Long id, String password) {
                return null;
            }

            @Override
            public Result<MEnterprise> create(MEnterprise mEnterprise) {
                return null;
            }

            @Override
            public Result<MEnterprise> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result<MEnterprise> get(Long id) {
                return null;
            }

            @Override
            public Result delete(Long id, String merchantLevel, Long currentUserId) {
                return null;
            }

            @Override
            public Result modifyStatusById(Long id, String status) {
                return null;
            }

            @Override
            public Result<MEnterprise> findByAccountAndParentId(String account, Long parentId) {
                return null;
            }

            @Override
            public Integer countCompayByParentId(Long parentId, Integer isDel) {
                return null;
            }

            @Override
            public Result<MEnterPriseTotalCountInfo> getEnterPriseTotalInfo(Long id, boolean flag) {
                return null;
            }

            @Override
            public Result<MEnterprise> searchNoPage(String filters) {
                return null;
            }

            @Override
            public Result<MEnterprise> searchByTerminal(String filters, String sorts, int size, Long parentId, String GT_createDate, String LT_createDate, int page) {
                return null;
            }



            @Override
            public Long countByParentIdAndMerchantLevel(Long parentId, String merchantLevel) {
                return null;
            }
        };
    }
}
