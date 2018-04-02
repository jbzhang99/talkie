package com.meta.feignfallback.qmanage;

import com.meta.Result;
import com.meta.feignclient.qmanage.QUserEventClient;
import com.meta.model.qmanage.MQUserEvent;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午9:36
 *
 * @version 1.0
 **/
@Component
public class QUserEventFallBack implements FallbackFactory<QUserEventClient> {
    private static final Logger log = LoggerFactory.getLogger(QUserEventFallBack.class);
    @Override
    public QUserEventClient create(Throwable cause) {
        return new QUserEventClient(){
            @Override
            public Result<MQUserEvent> search(String filters, String sorts, int size, int page) {
                return null;
            }
        };
    }
}
