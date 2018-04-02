package com.meta.feignfallback.merchant;

import com.meta.Result;
import com.meta.feignclient.merchant.MerchantClient;
import com.meta.model.merchant.MMerchant;
import com.meta.model.totalinfo.MMerchantTotalCountInfo;
import com.meta.model.totalinfo.MTotalCountInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * create by lhq
 * create date on  18-2-23上午10:11
 *
 * @version 1.0
 **/
@Component
public class MerchantFallBack implements FallbackFactory<MerchantClient> {
    @Override
    public MerchantClient create(Throwable cause) {
        return new MerchantClient(){
            @Override
            public Result<MMerchant> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result<MMerchant> searchNoPage(String filters) {
                return null;
            }

            @Override
            public Result<MMerchant> create(MMerchant mMerchant) {
                return null;
            }

            @Override
            public Result delete(Long id, String merchantLevel, Long currentUserId) {
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
            public Result<Map<String, Integer>> countCompayByParentId(Long parentId, Integer isDel) {
                return null;
            }

            @Override
            public Result<MMerchant> searchByAccount(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result deleteById(Long id) {
                return null;
            }

            @Override
            public Result<MTotalCountInfo> getGeneralTotalInfo(Long id) {
                return null;
            }

            @Override
            public Result<MMerchantTotalCountInfo> getMerchantTotalInfo(Long id, boolean flag) {
                return null;
            }

            @Override
            public Result<MMerchant> get(Long id) {
                return null;
            }

            @Override
            public Long countParnetIdAndMerchantLevel(Long parentId, String merchantLevel) {
                return null;
            }
        };
    }
}
