package com.meta.feignfallback.enterprise;

import com.meta.Result;
import com.meta.feignclient.enterprise.EnterpriseEventClient;
import com.meta.model.enterprise.MEnterpriseEvent;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午11:42
 *
 * @version 1.0
 **/
@Component
public class EnterpriseEventFallBack implements FallbackFactory<EnterpriseEventClient> {
    @Override
    public EnterpriseEventClient create(Throwable cause) {
        return new EnterpriseEventClient(){
            @Override
            public Result<MEnterpriseEvent> search(String filters, String sorts, int size, int page) {
                return null;
            }
        };
    }
}
