package com.meta.feignclient.generalagent;

import com.meta.*;
import com.meta.feignfallback.generalagent.GeneralAgentEventFallBack;
import com.meta.model.generalagent.MGeneralAgentEvent;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by lhq on 2017/11/14.
 */
@FeignClient(name = ServiceNames.TALKIE_CORE,fallbackFactory = GeneralAgentEventFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface GeneralAgentEventClient {

    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'search_general_agent_event_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.GeneralAgentEvent.GENERAL_AGENT_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取总代操作记录列表", notes = "根据查询条件获总代操作记录列表在前端表格展示")
     Result<MGeneralAgentEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);



}
