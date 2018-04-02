package com.meta.feignfallback.generalagent;

import com.meta.Result;
import com.meta.feignclient.generalagent.GeneralAgentEventClient;
import com.meta.model.generalagent.MGeneralAgentEvent;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午11:25
 *
 * @version 1.0
 **/
@Component
public class GeneralAgentEventFallBack implements FallbackFactory<GeneralAgentEventClient> {
    @Override
    public GeneralAgentEventClient create(Throwable cause) {
        return new GeneralAgentEventClient(){
            @Override
            public Result<MGeneralAgentEvent> search(String filters, String sorts, int size, int page) {
                return null;
            }
        };
    }
}
