package com.meta.feignclient.qmanage;

import com.meta.*;
import com.meta.feignfallback.qmanage.QGeneralAgentFallBack;
import com.meta.model.datatotal.MQGeneralagentDataTotal;
import com.meta.model.datatotal.MQMerchantTotal;
import com.meta.model.datatotal.MQYearExpenditureDataTotal;
import com.meta.model.qmanage.MQGeneralAgent;
import com.meta.model.qmanage.MQtotal;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * Created by lhq on 2017/11/15.
 */
@FeignClient(name = ServiceNames.TALKIE_CORE,fallbackFactory = QGeneralAgentFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface QGeneralAgentClient {

    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'search_q_general_agent_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币总代表", notes = "根据查询条件获Q币总代在前端表格展示")
    Result<MQGeneralAgent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);

    @CacheEvict(value = RedisValue.FIND_MERCHANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改Q币企业信息", notes = "创建/修改Q币企业信息")
    Result<MQGeneralAgent> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MQGeneralAgent mqGeneralAgent);

    /**
     *  充值需要其他操作的更新缓存
     */
    @CacheEvict(value = {RedisValue.FIND_MERCHANT,RedisValue.FIND_Q_USER,RedisValue.FIND_ENTER_PRISE} ,allEntries = true)
    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_MODIFY_BALANCE_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID更改余额(回收/划分)", notes = "根据ID更改余额(回收/划分)")
    Result modifyBalanceById(
            @ApiParam(name = "sourceId", value = "来源ID", defaultValue = "")
            @RequestParam(value = "sourceId", required = false) Long sourceId,
            @ApiParam(name = "targetId", value = "目标ID", defaultValue = "")
            @RequestParam(value = "targetId", required = false) Long targetId,
            @ApiParam(name = "value", value = "值", defaultValue = "")
            @RequestParam(value = "value", required = false) Double value,
            @ApiParam(name = "type", value = "类型，划分还是收回", defaultValue = "")
            @RequestParam(value = "type", required = false) Integer type,
            @ApiParam(name = "language", value = "语言", defaultValue = "")
            @RequestParam(value = "language", required = false) String language);

    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'qgeneral_alent_find_q_balance_conditions_id='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_TOTAL_Q, method = RequestMethod.GET)
    @ApiOperation(value = "查询可用的Q币 ，及已分配的Q币", notes = "查询可用的Q币 ，及已分配的Q币")
     MQtotal findQBalance(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id);


    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_UPDATE_BALANCE_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "查询可用的Q币 ，及已分配的Q币", notes = "查询可用的Q币 ，及已分配的Q币")
     Result<MQGeneralAgent> updateQBalanceById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "balance", value = "balance", defaultValue = "")
            @RequestParam(value = "balance", required = false) Double balance);



    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'search_q_general_agent_find_by_id_conditions_id='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_FIND_BY_USER_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据idQ币总代理信息", notes = "根据idQ币总代理信息")
     Result<MQGeneralAgent> findByUserId(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id);


    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_DATA_TOTAL, method = RequestMethod.GET)
    @ApiOperation(value = "根据月份获取统计信息", notes = "根据月份获取统计信息")
     Result<MQGeneralagentDataTotal> totalByYear(
            @ApiParam(name = "year", value = "year", defaultValue = "")
            @RequestParam(value = "year") Integer  year);


    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_YEAR_TOTAL, method = RequestMethod.GET)
    @ApiOperation(value = "获取年度Q币统计信息", notes = "获取年度Q币统计信息")
     Result<MQYearExpenditureDataTotal> expAndEarByYear();



    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'search_q_general_agent_total_by_year_and_month_conditions_year='+#p0+'_and_month='+#p1", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_TOTAL_BY_YEAR_AND_MONTH, method = RequestMethod.GET)
    @ApiOperation(value = "根据年份及月份 统计代理商的划分/回收记录", notes = "根据年份及月份 统计代理商的划分/回收记录")
     Result<MQMerchantTotal> totalByYearAndMonth(
            @ApiParam(name = "year", value = "year", defaultValue = "")
            @RequestParam(value = "year") String year,
            @ApiParam(name = "month", value = "month", defaultValue = "")
            @RequestParam(value = "month") String month);


}
