package com.meta.feignfallback.qmanage;

import com.meta.Result;
import com.meta.feignclient.qmanage.QGeneralAgentClient;
import com.meta.model.datatotal.MQGeneralagentDataTotal;
import com.meta.model.datatotal.MQMerchantTotal;
import com.meta.model.datatotal.MQYearExpenditureDataTotal;
import com.meta.model.qmanage.MQGeneralAgent;
import com.meta.model.qmanage.MQtotal;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午9:47
 *
 * @version 1.0
 **/
@Component
public class QGeneralAgentFallBack implements FallbackFactory<QGeneralAgentClient> {
    private static final Logger log = LoggerFactory.getLogger(QGeneralAgentFallBack.class);
    @Override
    public QGeneralAgentClient create(Throwable cause) {
        return new QGeneralAgentClient(){
            @Override
            public Result<MQGeneralAgent> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result<MQGeneralAgent> create(MQGeneralAgent mqGeneralAgent) {
                return null;
            }

            @Override
            public Result modifyBalanceById(Long sourceId, Long targetId, Double value, Integer type, String language) {
                return null;
            }

            @Override
            public MQtotal findQBalance(Long id) {
                return null;
            }

            @Override
            public Result<MQGeneralAgent> updateQBalanceById(Long id, Double balance) {
                return null;
            }

            @Override
            public Result<MQGeneralAgent> findByUserId(Long id) {
                return null;
            }

            @Override
            public Result<MQGeneralagentDataTotal> totalByYear(Integer year) {
                return null;
            }

            @Override
            public Result<MQYearExpenditureDataTotal> expAndEarByYear() {
                return null;
            }

            @Override
            public Result<MQMerchantTotal> totalByYearAndMonth(String year, String month) {
                return null;
            }
        };
    }
}
