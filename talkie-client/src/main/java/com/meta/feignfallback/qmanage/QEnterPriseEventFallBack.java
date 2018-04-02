package com.meta.feignfallback.qmanage;

import com.meta.Result;
import com.meta.feignclient.qmanage.QEnterpriseEventClient;
import com.meta.model.qmanage.MQEnterpriseEvent;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午9:52
 *
 * @version 1.0
 **/
@Component
public class QEnterPriseEventFallBack  implements FallbackFactory<QEnterpriseEventClient> {
    private static final Logger log = LoggerFactory.getLogger(QEnterPriseEventFallBack.class);

    @Override
    public QEnterpriseEventClient create(Throwable cause) {
        return new QEnterpriseEventClient(){
            @Override
            public Result<MQEnterpriseEvent> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result<MQEnterpriseEvent> create(MQEnterpriseEvent mqEnterpriseEvent) {
                return null;
            }

            @Override
            public Result<MQEnterpriseEvent> searchNoPage(String filters) {
                return null;
            }
        };
    }
}
