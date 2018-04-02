package com.meta.feignfallback.qmanage;

import com.meta.Result;
import com.meta.feignclient.qmanage.QMerchantEventClient;
import com.meta.model.qmanage.MQMerchantEvent;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午9:45
 *
 * @version 1.0
 **/
@Component
public class QMerchantEventFallBack implements FallbackFactory<QMerchantEventClient> {
    private static final Logger log = LoggerFactory.getLogger(QMerchantEventFallBack.class);
    @Override
    public QMerchantEventClient create(Throwable cause) {
        return new QMerchantEventClient(){
            @Override
            public Result<MQMerchantEvent> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result<MQMerchantEvent> searchNoPage(String filters) {
                return null;
            }
        };
    }
}
