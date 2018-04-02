package com.meta.feignfallback.generalagent;

import com.meta.Result;
import com.meta.feignclient.generalagent.GeneralAgentAccountClient;
import com.meta.model.generalagent.MGeneralAgentAccount;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午11:20
 *
 * @version 1.0
 **/
@Component
public class GeneralAgentAccountFallBack  implements FallbackFactory<GeneralAgentAccountClient>{
    @Override
    public GeneralAgentAccountClient create(Throwable cause) {
        return new GeneralAgentAccountClient(){
            @Override
            public Result<MGeneralAgentAccount> search(String filters, String sorts, int size, int page) {
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
            public Result<MGeneralAgentAccount> create(MGeneralAgentAccount mGeneralAgentAccount) {
                return null;
            }

            @Override
            public Result delete(Long id) {
                return null;
            }
        };
    }
}
