package com.meta.feignfallback.qmanage;

import com.meta.Result;
import com.meta.feignclient.qmanage.QSecEnterPriseEventClient;
import com.meta.model.qmanage.MQSecEnterPriseEvent;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午9:41
 *
 * @version 1.0
 **/
@Component
public class QSecEnterPriseEventFallBack implements FallbackFactory<QSecEnterPriseEventClient> {
    private static final Logger log = LoggerFactory.getLogger(QSecEnterPriseEventFallBack.class);
    @Override
    public QSecEnterPriseEventClient create(Throwable cause) {
        return new QSecEnterPriseEventClient(){
            @Override
            public Result<MQSecEnterPriseEvent> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result<MQSecEnterPriseEvent> create(MQSecEnterPriseEvent mqSecEnterPriseEvent) {
                return null;
            }

            @Override
            public Result<MQSecEnterPriseEvent> get(Long id) {
                return null;
            }

            @Override
            public Result delete(Long id) {
                return null;
            }
        };
    }
}
