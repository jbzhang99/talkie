package com.meta.feignfallback.accountant;

import com.meta.Result;
import com.meta.feignclient.accountant.AccountantManageClient;
import com.meta.model.accountant.MAccountant;
import feign.hystrix.FallbackFactory;

/**
 * create by lhq
 * create date on  18-3-1下午4:12
 *
 * @version 1.0
 **/
public class AccountantManageFallBack implements FallbackFactory<AccountantManageClient> {
    @Override
    public AccountantManageClient create(Throwable cause) {
        return new AccountantManageClient(){
            @Override
            public Result<MAccountant> search(String filters, String sorts, int size, int page) {
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
            public Result deleteById(Long id) {
                return null;
            }

            @Override
            public Result<MAccountant> create(MAccountant user) {
                return null;
            }
        };
    }
}
