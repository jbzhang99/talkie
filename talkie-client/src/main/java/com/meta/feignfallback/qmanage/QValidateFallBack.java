package com.meta.feignfallback.qmanage;

import com.meta.Result;
import com.meta.feignclient.qmanage.QValidateClient;
import com.meta.model.qmanage.MQValidate;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午9:37
 *
 * @version 1.0
 **/
@Component
public class QValidateFallBack implements FallbackFactory<QValidateClient> {
    private static final Logger log = LoggerFactory.getLogger(QValidateFallBack.class);
    @Override
    public QValidateClient create(Throwable cause) {
        return new QValidateClient(){
            @Override
            public Result<MQValidate> get(Long id) {
                return null;
            }

            @Override
            public Result<MQValidate> modifyPasswordById(Long id, String password) {
                return null;
            }
        };
    }
}
