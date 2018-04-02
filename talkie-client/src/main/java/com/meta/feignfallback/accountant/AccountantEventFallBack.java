package com.meta.feignfallback.accountant;

import com.meta.Result;
import com.meta.feignclient.accountant.AccountantEventClient;
import com.meta.model.accountant.MAccountantEvent;
import feign.hystrix.FallbackFactory;

/**
 * create by lhq
 * create date on  18-3-2上午10:50
 *
 * @version 1.0
 **/
public class AccountantEventFallBack implements FallbackFactory<AccountantEventClient> {
    @Override
    public AccountantEventClient create(Throwable cause) {
        return new AccountantEventClient(){
            @Override
            public Result<MAccountantEvent> search(String filters, String sorts, int size, int page) {
                return null;
            }
        };
    }
}
