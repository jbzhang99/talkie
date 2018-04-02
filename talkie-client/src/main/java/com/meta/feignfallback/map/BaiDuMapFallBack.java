package com.meta.feignfallback.map;

import com.meta.Result;
import com.meta.feignclient.map.BaiDuMapClient;
import com.meta.model.map.MBaiDuMap;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午11:07
 *
 * @version 1.0
 **/
@Component
public class BaiDuMapFallBack implements FallbackFactory<BaiDuMapClient> {
    @Override
    public BaiDuMapClient create(Throwable cause) {
        return new BaiDuMapClient(){
            @Override
            public Result<MBaiDuMap> getById(String files, String startDate, String endDate) {
                return null;
            }
        };
    }
}
