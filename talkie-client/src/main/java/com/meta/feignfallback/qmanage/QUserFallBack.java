package com.meta.feignfallback.qmanage;

import com.meta.Result;
import com.meta.feignclient.qmanage.QUserClient;
import com.meta.model.qmanage.MQUser;
import com.meta.model.qmanage.MQtotal;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午9:34
 *
 * @version 1.0
 **/
@Component
public class QUserFallBack implements FallbackFactory<QUserClient> {
    private static final Logger log = LoggerFactory.getLogger(QUserFallBack.class);
    @Override
    public QUserClient create(Throwable cause) {
        return new QUserClient(){
            @Override
            public Result<MQUser> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result modifyBalanceById(Long sourceId, Long targetId, Double value, Integer type) {
                return null;
            }

            @Override
            public Result<MQUser> get(Long id) {
                return null;
            }

            @Override
            public MQtotal findQBalance(Long id) {
                return null;
            }

            @Override
            public Result batchAddOrDel(String id, Double value, Long userId) {
                return null;
            }

            @Override
            public Result<MQUser> findByUserId(Long id) {
                return null;
            }
        };
    }
}
