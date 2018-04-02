package com.meta.feignfallback.qmanage;

import com.meta.Result;
import com.meta.feignclient.qmanage.QSecEnterPriseClient;
import com.meta.model.qmanage.MQEnterprise;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午9:43
 *
 * @version 1.0
 **/
@Component
public class QSecEnterPriseFallBack implements FallbackFactory<QSecEnterPriseClient> {
    private static final Logger log = LoggerFactory.getLogger(QSecEnterPriseFallBack.class);
    @Override
    public QSecEnterPriseClient create(Throwable cause) {
        return new QSecEnterPriseClient(){
            @Override
            public Result<MQEnterprise> create(MQEnterprise mqEnterprise) {
                return null;
            }

            @Override
            public Result<MQEnterprise> get(Long id) {
                return null;
            }

            @Override
            public Result delete(Long id) {
                return null;
            }

            @Override
            public Result<MQEnterprise> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result<MQEnterprise> findByUserId(Long id) {
                return null;
            }
        };
    }
}
