package com.meta.feignfallback.qmanage;

import com.meta.Result;
import com.meta.feignclient.qmanage.QGeneralAgentEventClient;
import com.meta.model.qmanage.MQGeneralAgentEvent;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午9:51
 *
 * @version 1.0
 **/
@Component
public class QGeneralAgentEventFallBack implements FallbackFactory<QGeneralAgentEventClient> {
    private static final Logger log = LoggerFactory.getLogger(QGeneralAgentEventFallBack.class);

    @Override
    public QGeneralAgentEventClient create(Throwable cause) {
        return new QGeneralAgentEventClient(){
            @Override
            public Result<MQGeneralAgentEvent> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result<MQGeneralAgentEvent> searchNoPage(String filters) {
                return null;
            }
        };
    }
}
