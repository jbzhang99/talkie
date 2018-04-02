package com.meta.feignclient.accountant;

import com.meta.*;
import com.meta.feignfallback.accountant.AccountantEventFallBack;
import com.meta.model.accountant.MAccountantEvent;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * create by lhq
 * create date on  18-3-2上午10:47
 *
 * @version 1.0
 **/
@FeignClient(name = ServiceNames.TALKIE_CORE, fallbackFactory = AccountantEventFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface AccountantEventClient {

    @Cacheable(value = RedisValue.FIND_ACCOUNTANT, key = "'search_accountant_event_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.AccountantEvent.ACCOUNTANT_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取会计操作列表", notes = "根据查询条件获会计操作列表在前端表格展示")
    Result<MAccountantEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);
}
