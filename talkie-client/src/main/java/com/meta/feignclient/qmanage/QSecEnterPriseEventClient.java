package com.meta.feignclient.qmanage;

import com.meta.*;
import com.meta.feignfallback.qmanage.QSecEnterPriseEventFallBack;
import com.meta.model.qmanage.MQSecEnterPriseEvent;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * Created by lhq on 2017/11/21.
 */
@FeignClient(name = ServiceNames.TALKIE_CORE, fallbackFactory = QSecEnterPriseEventFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface QSecEnterPriseEventClient {

    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'search_q_sec_eneter_prise_event_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.QSecEnterPriseEvent.Q_SEC_ENTER_PRISE_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币二级管理操作表", notes = "根据查询条件获Q币二级管理操作在前端表格展示")
    Result<MQSecEnterPriseEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);


    @CacheEvict(value = RedisValue.FIND_MERCHANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.QSecEnterPriseEvent.Q_SEC_ENTER_PRISE_EVENTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改Q币二级管理信息", notes = "创建/修改Q币二级管理信息")
    Result<MQSecEnterPriseEvent> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MQSecEnterPriseEvent mqSecEnterPriseEvent);


    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'search_q_sec_eneter_prise_event_get_conditions_id='+#p", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.QSecEnterPriseEvent.Q_SEC_ENTER_PRISE_EVENT, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取Q币用户操作信息", notes = "根据id获取Q币用户操作信息")
    Result<MQSecEnterPriseEvent> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id);


    @CacheEvict(value = RedisValue.FIND_MERCHANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.QSecEnterPriseEvent.Q_SEC_ENTER_PRISE_EVENTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除Q币用户操作信息", notes = "根据id删除Q币用户操作信息")
    Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id);

}
