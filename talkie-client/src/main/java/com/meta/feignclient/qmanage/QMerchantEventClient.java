package com.meta.feignclient.qmanage;

import com.meta.*;
import com.meta.feignfallback.qmanage.QMerchantEventFallBack;
import com.meta.model.qmanage.MQMerchantEvent;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by lhq on 2017/11/15.
 */
@FeignClient(name = ServiceNames.TALKIE_CORE, fallbackFactory = QMerchantEventFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface QMerchantEventClient {

    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'search_q_merchant_event_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.QMerchantEvent.Q_MERCHANT_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币代理操作表", notes = "根据查询条件获Q币代理操作在前端表格展示")
    Result<MQMerchantEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);

    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'search_q_merchant_event_no_page_conditions_filters='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.QMerchantEvent.Q_MERCHANT_EVENT, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币代理不分页操作表", notes = "根据查询条件获Q币代理操作不分页在前端表格展示")
    Result<MQMerchantEvent> searchNoPage(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters);

}
