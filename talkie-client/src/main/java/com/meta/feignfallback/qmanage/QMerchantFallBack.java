package com.meta.feignfallback.qmanage;

import com.meta.Result;
import com.meta.feignclient.qmanage.QMerchantClient;
import com.meta.model.qmanage.MQMerchant;
import com.meta.model.qmanage.MQtotal;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午9:46
 *
 * @version 1.0
 **/
@Component
public class QMerchantFallBack  implements FallbackFactory<QMerchantClient> {
    private static final Logger log = LoggerFactory.getLogger(QMerchantFallBack.class);
    @Override
    public QMerchantClient create(Throwable cause) {
        return new QMerchantClient(){
            @Override
            public Result<MQMerchant> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result<MQMerchant> create(MQMerchant mqMerchant) {
                return null;
            }

            @Override
            public Result modifyBalanceById(Long sourceId, Long targetId, Double value, Integer type, String language) {
                return null;
            }

            @Override
            public Result batchAddOrDel(String id, Double value, Long userId, String language) {
                return null;
            }

            @Override
            public MQtotal findQBalance(Long id) {
                return null;
            }

            @Override
            public Result<MQMerchant> get(Long id) {
                return null;
            }
        };
    }
}
