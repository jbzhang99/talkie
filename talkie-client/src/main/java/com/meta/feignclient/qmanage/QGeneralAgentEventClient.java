package com.meta.feignclient.qmanage;

import com.meta.*;
import com.meta.feignfallback.qmanage.QGeneralAgentEventFallBack;
import com.meta.model.qmanage.MQGeneralAgentEvent;
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
@FeignClient(name = ServiceNames.TALKIE_CORE,fallbackFactory = QGeneralAgentEventFallBack.class )
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface QGeneralAgentEventClient {


    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'search_q_general_agent_events_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.QGeneralAgentEvent.Q_GENERAL_AGENT_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币总代理操作表", notes = "根据查询条件获Q币总代理操作在前端表格展示")
     Result<MQGeneralAgentEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);

    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'search_q_general_agent_no_page_events_conditions_filters='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.QGeneralAgentEvent.Q_GENERAL_AGENT_EVENT, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币总代理操作表不分页", notes = "根据查询条件获Q币总代理操作在前端表格展示，不分页")
     Result<MQGeneralAgentEvent> searchNoPage(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters);



}
