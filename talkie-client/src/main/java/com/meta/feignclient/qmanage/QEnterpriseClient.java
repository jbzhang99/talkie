package com.meta.feignclient.qmanage;

import com.meta.*;
import com.meta.feignfallback.qmanage.QEnterPriseFallBack;
import com.meta.model.qmanage.MQEnterprise;
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
@FeignClient(name = ServiceNames.TALKIE_CORE,fallbackFactory = QEnterPriseFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface QEnterpriseClient {

    @Cacheable(value = RedisValue.FIND_ENTER_PRISE, key = "'search_q_enter_prise_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.QEnterPrise.Q_ENTER_PRISES, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币企业表", notes = "根据查询条件获Q币企业在前端表格展示")
    Result<MQEnterprise> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);

    @CacheEvict(value = RedisValue.FIND_ENTER_PRISE,allEntries = true)
    @RequestMapping(value = ServiceUrls.QEnterPrise.Q_ENTER_PRISES, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改Q币企业信息", notes = "创建/修改Q币企业信息")
    Result<MQEnterprise> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MQEnterprise mqEnterprise);

    @CacheEvict(value = RedisValue.FIND_ENTER_PRISE,allEntries = true)
    @RequestMapping(value = ServiceUrls.QEnterPrise.Q_ENTER_PRISE_MODIFY_BALANCE_BY_ID, method = RequestMethod.POST)
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
            @ApiParam(name = "currentId", value = "操作者ID", defaultValue = "")
            @RequestParam(value = "currentId", required = false) Long currentId);

    @RequestMapping(value = ServiceUrls.QEnterPrise.Q_ENTER_PRISE_TOTAL_Q, method = RequestMethod.GET)
    @ApiOperation(value = "查询可用的Q币 ，及已分配的Q币", notes = "查询可用的Q币 ，及已分配的Q币")
    MQtotal findQBalance(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id);

    @CacheEvict(value = RedisValue.FIND_ENTER_PRISE,allEntries = true)
    @RequestMapping(value = ServiceUrls.QEnterPrise.Q_ENTER_PRISE_BATCH_ADD_OR_DEL, method = RequestMethod.POST)
    @ApiOperation(value = "批量操作(增/减)Q币", notes = "批量操作(增/减)Q币")
    Result batchAddOrDel(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "value", value = "value", defaultValue = "")
            @RequestParam(value = "value", required = false) int value,
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId", required = false) Long userId);


    @Cacheable(value = RedisValue.FIND_ENTER_PRISE, key = "'search_q_enter_prise_find_by_user_id_conditions_id='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.QEnterPrise.Q_ENTER_PRISE_FIND_BY_USER_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据idQ币信息", notes = "根据idQ币信息")
     Result<MQEnterprise> findByUserId(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id);

    @Cacheable(value = RedisValue.FIND_ENTER_PRISE, key = "'search_q_enter_prise_no_page_conditions_filters='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.QEnterPrise.Q_ENTER_PRISE_NO_PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币企业表不分页", notes = "获取Q币企业表不分页")
     Result<MQEnterprise> searchNoPage(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters);

}
