package com.meta.feignfallback.qmanage;

import com.meta.Result;
import com.meta.feignclient.qmanage.QEnterpriseClient;
import com.meta.model.qmanage.MQEnterprise;
import com.meta.model.qmanage.MQtotal;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午10:08
 *
 * @version 1.0
 **/
@Component
public class QEnterPriseFallBack implements FallbackFactory<QEnterpriseClient> {
    private static final Logger log = LoggerFactory.getLogger(QEnterPriseFallBack.class);
    @Override
    public QEnterpriseClient create(Throwable cause) {
        return new QEnterpriseClient(){
            @Override
            public Result<MQEnterprise> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result<MQEnterprise> create(MQEnterprise mqEnterprise) {
                return null;
            }

            @Override
            public Result modifyBalanceById(Long sourceId, Long targetId, Double value, Integer type, Long currentId) {
                return null;
            }

            @Override
            public MQtotal findQBalance(Long id) {
                return null;
            }

            @Override
            public Result batchAddOrDel(Long id, int value, Long userId) {
                return null;
            }

            @Override
            public Result<MQEnterprise> findByUserId(Long id) {
                return null;
            }

            @Override
            public Result<MQEnterprise> searchNoPage(String filters) {
                return null;
            }
        };
    }
}
